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

class ReferenceProbe {
  @JsonbProperty var author: ReferenceAuthor = null
}
