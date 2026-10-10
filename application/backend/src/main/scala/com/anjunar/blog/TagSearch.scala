package com.anjunar.blog

import com.anjunar.blog.hibernate.search.{AbstractSearch, Context, PredicateProvider, SortProvider}
import com.anjunar.blog.hibernate.search.annotations.{RestPredicate, RestSort}
import com.anjunar.json.mapper.schema.Link
import jakarta.enterprise.context.ApplicationScoped
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.persistence.criteria.Order
import jakarta.ws.rs.{BadRequestException, DefaultValue, QueryParam}
import jakarta.ws.rs.core.UriBuilder

import java.util
import scala.annotation.meta.field
import scala.jdk.CollectionConverters.*

final case class TagSearch(
  @(JsonbProperty @field) @(RestSort @field)(classOf[TagSearch.ByName])
  sort: String = "name",
  override val index: Int = 0,
  override val limit: Int = 20
) extends AbstractSearch

object TagSearch {
  @ApplicationScoped
  class ByName extends SortProvider[TagSearch, BlogTag] {
    override def sort(context: Context[TagSearch, BlogTag]): util.List[Order] =
      util.List.of(
        context.builder.asc(context.builder.lower(context.root.get(BlogTag.schema.name))),
        context.builder.asc(context.root.get(BlogTag.schema.id))
      )
  }
}
