package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.forms.validators.{NotBlank, Pattern, Size}
import ui.json.{JsonId, JsonMapper, JsonProperty}

import scala.annotation.meta.field
import scala.scalajs.js

final class BlogTag {
  @JsonId val id: Property[String] = Property("")
  val version: Property[Long] = Property(-1L)
  @(NotBlank @field)() @(Size @field)(min = 2, max = 80)
  @(Pattern @field)("^[a-z0-9]+(?:-[a-z0-9]+)*$")
  val slug: Property[String] = Property("")
  @(NotBlank @field)() @(Size @field)(min = 2, max = 80)
  val name: Property[String] = Property("")

  def writeBody(): js.Dynamic = {
    val body = JsonMapper.serialize(this)
    if (id.get.isEmpty) js.special.delete(body, "id")
    else body.updateDynamic("version")(version.get.toDouble)
    body
  }
}
