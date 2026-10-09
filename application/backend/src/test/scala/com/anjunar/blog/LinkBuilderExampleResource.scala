package com.anjunar.blog

import com.anjunar.json.mapper.PreparedChange
import jakarta.annotation.security.{DenyAll, PermitAll}
import jakarta.ws.rs.{GET, PATCH, POST, Path, PathParam}
import org.scalatest.funsuite.AnyFunSuite

import java.util.UUID

@Path("/_test/link-builder/{postId}")
@PermitAll
class LinkBuilderExampleResource {
  @GET @Path("/de")
  def read(@PathParam("postId") post: BlogPost): Unit = throw new AssertionError("A link must not call the endpoint")

  @PATCH @Path("/{id}")
  def update(@PathParam("postId") post: BlogPost,
      @PathParam("id") change: PreparedChange[BlogPostTranslation]): Unit =
    throw new AssertionError("A link must not apply a change")

  @POST @Path("/de")
  def create(@PathParam("postId") post: BlogPost, change: PreparedChange[BlogPostTranslation]): Unit =
    throw new AssertionError("A link must not create an entity")

  @GET @Path("/hidden") @DenyAll
  def hidden(@PathParam("postId") post: BlogPost): Unit = ()
}

