package com.anjunar.blog

import com.anjunar.json.mapper.PreparedChange
import jakarta.annotation.security.{DenyAll, PermitAll}
import jakarta.ws.rs.{GET, PATCH, POST, Path, PathParam}
import org.scalatest.funsuite.AnyFunSuite

import java.util
class LinkBuilderSpec extends AnyFunSuite {
  private def post(): BlogPost = {
    val value = new BlogPost()
    value.id = util.UUID.randomUUID()
    value
  }

  test("an entity argument supplies its path ID without calling the resource") {
    val parent = post()
    val link = LinkBuilder.create[LinkBuilderExampleResource](_.read(parent)).withRel("self").build()
    assert(link.rel == "self" && link.method == "GET")
    assert(link.url == s"/service/_test/link-builder/${parent.id}/de")
  }

  test("a PreparedChange placeholder uses an explicit entity ID and the annotated PATCH verb") {
    val parent = post()
    val translation = util.UUID.randomUUID()
    val link = LinkBuilder.create[LinkBuilderExampleResource](_.update(parent, null))
      .withVariable("id", translation).build()
    assert(link.rel == "update" && link.method == "PATCH")
    assert(link.url == s"/service/_test/link-builder/${parent.id}/$translation")
  }

  test("create links need the parent but no saved translation or request body") {
    val parent = post()
    val link = LinkBuilder.create[LinkBuilderExampleResource](_.create(parent, null)).build()
    assert(link.rel == "create" && link.method == "POST")
    assert(link.url == s"/service/_test/link-builder/${parent.id}/de")
  }

  test("a method-level denial overrides the class policy") {
    assert(LinkBuilder.create[LinkBuilderExampleResource](_.hidden(post())).build() == null)
  }

  test("missing path variables fail rather than producing an incomplete action URL") {
    intercept[IllegalArgumentException] {
      LinkBuilder.create[LinkBuilderExampleResource](_.update(post(), null)).build()
    }
  }

  test("path values are escaped as values rather than becoming route syntax") {
    val builder = LinkBuilder.create[LinkBuilderExampleResource](_.update(post(), null))
      .withVariable("id", "a/b ?#%")
    assert(builder.build().url.endsWith("/a%2Fb%20%3F%23%25"))
  }
}
