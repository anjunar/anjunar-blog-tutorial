package com.anjunar.blog

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.JoinType
import jakarta.ws.rs.ServiceUnavailableException

import java.time.Instant
import java.util.UUID
import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*

final case class PublishedPage(id: UUID, slug: String, locale: String, title: String,
    summary: Option[String], published: Instant, changed: Option[Instant]) {
  def path: String = s"/$locale/posts/$slug"
  def updated: Instant = changed.filter(_.isAfter(published)).getOrElse(published)
}
