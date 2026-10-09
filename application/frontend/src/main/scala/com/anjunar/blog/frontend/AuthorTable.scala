package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.forms.validators.{NotBlank, Pattern, Size}
import ui.json.{JsonId, JsonMapper, JsonProperty}

import scala.annotation.meta.field
import scala.scalajs.js

final class AuthorTable(var rows: Seq[AuthorData] = Seq.empty, var size: Long = -1L,
    @(JsonProperty @field)("$links") var links: Seq[ApiLink] = Seq.empty)
