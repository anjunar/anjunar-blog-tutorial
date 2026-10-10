package com.anjunar.blog

import com.anjunar.json.mapper.{EntityLoader, JsonMapper}
import com.anjunar.json.mapper.intermediate.JsonParser
import com.anjunar.json.mapper.provider.EntityProvider
import com.anjunar.scala.universe.TypeResolver
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.validation.Validation
import org.scalatest.funsuite.AnyFunSuite

import java.util
import scala.util.Using

class ReferenceAuthor extends EntityProvider {
  @JsonbProperty var id: util.UUID = util.UUID.randomUUID()
  @JsonbProperty var version: Long = 0L
  @JsonbProperty var name: String = "An author"
}
