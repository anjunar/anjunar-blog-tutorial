package com.anjunar.blog

import com.anjunar.json.mapper.provider.DTO
import com.anjunar.json.mapper.schema.EntitySchema
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.persistence.{EntityGraph as JpaEntityGraph}

import java.util
import scala.annotation.meta.field
import scala.jdk.CollectionConverters.*

class SchemaProperty(
    @(JsonbProperty @field) val name: String,
    @(JsonbProperty @field)("type") val typeName: String
) extends DTO

