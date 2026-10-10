package com.anjunar.blog

import com.anjunar.hibernateddl.hibernate.annotation.SchemaId
import com.anjunar.json.mapper.provider.EntityProvider
import com.anjunar.json.mapper.schema.{EntitySchema, SchemaProvider, VisibilityRule}
import com.anjunar.json.mapper.schema.property.SingularProperty
import com.anjunar.scala.universe.introspector.AbstractProperty
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.persistence.{Access, AccessType, Column, Entity, GeneratedValue, GenerationType, Id, NamedAttributeNode, NamedEntityGraph, Table, UniqueConstraint, Version}
import jakarta.validation.constraints.{NotBlank, Pattern, Size}

import java.util
import scala.compiletime.uninitialized

@RequestScoped
class AuthorEditRule extends VisibilityRule[Account] {
  @Inject var caller: CallerAccess = uninitialized
  override def isVisible(account: Account, property: AbstractProperty): Boolean = true
  override def isWriteable(account: Account, property: AbstractProperty): Boolean = caller.administrator
}
