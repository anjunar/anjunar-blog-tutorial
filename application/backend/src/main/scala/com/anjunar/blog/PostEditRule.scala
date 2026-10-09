package com.anjunar.blog

import com.anjunar.json.mapper.schema.VisibilityRule
import com.anjunar.scala.universe.introspector.AbstractProperty
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

import scala.compiletime.uninitialized

@RequestScoped
class PostEditRule extends VisibilityRule[BlogPost] {
  @Inject var access: PostAccess = uninitialized
  override def isVisible(post: BlogPost, property: AbstractProperty): Boolean = access.canRead(post)
  override def isWriteable(post: BlogPost, property: AbstractProperty): Boolean = access.canEdit(post)
}
