package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.forms.validators.{NotBlank, Pattern, Size}
import ui.json.{JsonId, JsonMapper, JsonProperty}

import scala.annotation.meta.field
import scala.scalajs.js

final class AuthorData(
  var data: Account = null,
  @(JsonProperty @field)("$links") var links: Seq[ApiLink] = Seq.empty
)
