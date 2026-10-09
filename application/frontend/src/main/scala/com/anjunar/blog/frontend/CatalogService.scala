package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.{ListProperty, Property}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.util.{Failure, Success, Try}

final class CatalogService(accounts: AccountService)(using ExecutionContext) {
  def load(signal: Option[dom.AbortSignal]): Future[Option[CatalogData]] =
    accounts.session(signal).flatMap { session =>
      (session.links.find(_.rel == "authors"), session.links.find(_.rel == "tags")) match {
        case (Some(authors), Some(tags)) =>
          authorPage(authors, signal).zip(tagPage(tags, signal))
            .map((authors, tags) => Some(CatalogData(authors, tags)))
        case _ => Future.successful(None)
      }
    }

  def authorPage(link: ApiLink, signal: Option[dom.AbortSignal]): Future[AuthorTable] =
    HttpJson.get[AuthorTable](link.path("GET"), signal).map { table =>
      require(table.size >= 0 && table.rows.forall(row => row.data != null), "Invalid author table")
      table
    }

  def tagPage(link: ApiLink, signal: Option[dom.AbortSignal]): Future[BlogTagTable] =
    HttpJson.get[BlogTagTable](link.path("GET"), signal).map { table =>
      require(table.size >= 0 && table.rows.forall(row => row.data != null), "Invalid tag table")
      table
    }

  def saveAuthor(link: ApiLink, body: js.Dynamic): Future[AuthorData] = {
    val path = link.path("PATCH")
    accounts.session().flatMap(state => HttpJson.write[AuthorData](path, "PATCH", body, state.csrfToken))
  }

  def saveTag(link: ApiLink, body: js.Dynamic): Future[BlogTagData] = {
    val method = if (link.rel == "create") "POST" else {
      require(link.rel == "update", "Unexpected tag operation")
      "PATCH"
    }
    val path = link.path(method)
    accounts.session().flatMap(state => HttpJson.write[BlogTagData](path, method, body, state.csrfToken))
  }
}

