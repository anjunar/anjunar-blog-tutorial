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

final case class AuthorSearch(
  @(JsonbProperty @field) @(RestPredicate @field)(classOf[AuthorSearch.Available])
  available: Boolean = true,
  @(JsonbProperty @field) @(RestSort @field)(classOf[AuthorSearch.ByName])
  sort: String = "name",
  override val index: Int = 0,
  override val limit: Int = 20
) extends AbstractSearch

object AuthorSearch {
  @ApplicationScoped
  class Available extends PredicateProvider[Boolean, Account] {
    override def build(context: Context[Boolean, Account]): Unit = {
      require(context.value, "Only available authors can be selected")
      context.predicates.add(context.builder.equal(context.root.get(Account.schema.role), "ADMIN"))
      context.predicates.add(context.builder.equal(context.root.get(Account.schema.locked), false))
    }
  }

  @ApplicationScoped
  class ByName extends SortProvider[AuthorSearch, Account] {
    override def sort(context: Context[AuthorSearch, Account]): util.List[Order] =
      util.List.of(
        context.builder.asc(context.builder.lower(context.root.get(Account.schema.displayName))),
        context.builder.asc(context.root.get(Account.schema.id))
      )
  }
}
