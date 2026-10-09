package com.anjunar.blog

import com.anjunar.hibernateddl.hibernate.annotation.SchemaId
import com.anjunar.json.mapper.provider.EntityProvider
import com.anjunar.json.mapper.schema.{EntitySchema, SchemaProvider, VisibilityRule}
import com.anjunar.json.mapper.schema.property.{SetProperty, SingularProperty}
import com.anjunar.scala.universe.introspector.AbstractProperty
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.Typed
import jakarta.inject.Inject
import jakarta.json.bind.annotation.{JsonbProperty, JsonbTransient}
import jakarta.persistence.{Access, AccessType, Column, Entity, FetchType, ForeignKey, GeneratedValue, GenerationType, Id, JoinColumn, JoinTable, ManyToMany, ManyToOne, NamedAttributeNode, NamedEntityGraph, Table, Transient, UniqueConstraint, Version}
import jakarta.validation.constraints.{AssertTrue, NotBlank, NotNull, Pattern, Size}

import jakarta.persistence.{PrePersist, PreUpdate}
import java.time.Instant
import java.util
import java.util.UUID
import scala.compiletime.uninitialized

@RequestScoped
class TranslationReadRule extends VisibilityRule[BlogPostTranslation] {
  @Inject var caller: CallerAccess = uninitialized
  override def isVisible(value: BlogPostTranslation, property: AbstractProperty): Boolean =
    caller.administrator || (value.published && value.post != null && value.post.status == BlogPostStatus.PUBLISHED)
  override def isWriteable(value: BlogPostTranslation, property: AbstractProperty): Boolean = false
}
