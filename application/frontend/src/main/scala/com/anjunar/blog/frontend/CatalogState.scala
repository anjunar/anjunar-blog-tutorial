package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.{ListProperty, Property}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.util.{Failure, Success, Try}

final class CatalogState(initial: CatalogData, service: CatalogService)(using ExecutionContext) {
  val authors: ListProperty[AuthorData] = ListProperty(js.Array(initial.authors.rows*))
  val tags: ListProperty[BlogTagData] = ListProperty(js.Array(initial.tags.rows*))
  val nextAuthors: Property[Option[ApiLink]] = Property(initial.authors.links.find(_.rel == "next"))
  val nextTags: Property[Option[ApiLink]] = Property(initial.tags.links.find(_.rel == "next"))
  val busy: Property[Boolean] = Property(false)
  val failed: Property[Boolean] = Property(false)
  val saved: Property[Boolean] = Property(false)
  val createTag: Option[ApiLink] = initial.tags.links.find(_.rel == "create")
  private val abort = new dom.AbortController()
  private var disposed = false

  def moreAuthors(): Unit = nextAuthors.get.foreach { link =>
    if (!busy.get) {
      busy.set(true); failed.set(false)
      service.authorPage(link, Some(abort.signal)).onComplete {
        case Success(page) if !disposed =>
          page.rows.filterNot(row => authors.exists(_.data.id.get == row.data.id.get)).foreach(authors += _)
          nextAuthors.set(page.links.find(_.rel == "next")); busy.set(false)
        case Failure(_) if !disposed => failed.set(true); busy.set(false)
        case _                       => ()
      }
    }
  }

  def moreTags(): Unit = nextTags.get.foreach { link =>
    if (!busy.get) {
      busy.set(true); failed.set(false)
      service.tagPage(link, Some(abort.signal)).onComplete {
        case Success(page) if !disposed =>
          page.rows.filterNot(row => tags.exists(_.data.id.get == row.data.id.get)).foreach(tags += _)
          nextTags.set(page.links.find(_.rel == "next")); busy.set(false)
        case Failure(_) if !disposed => failed.set(true); busy.set(false)
        case _                       => ()
      }
    }
  }

  def recordAuthor(value: AuthorData): Unit = {
    authors.indexWhere(_.data.id.get == value.data.id.get) match {
      case -1    => authors += value
      case index => authors.update(index, value)
    }
    saved.set(true)
  }

  def recordTag(value: BlogTagData): Unit = {
    tags.indexWhere(_.data.id.get == value.data.id.get) match {
      case -1    => tags += value
      case index => tags.update(index, value)
    }
    saved.set(true)
  }

  def dispose(): Unit = { disposed = true; abort.abort() }
}
