package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.forms.validators.{NotBlank, Size}
import ui.json.{JsonId, JsonIgnore, JsonMapper, JsonProperty}

import scala.annotation.meta.field
import scala.scalajs.js

final class TranslationData(var data: BlogPostTranslation = null,
    @(JsonProperty @field)("$links") var links: Seq[ApiLink] = Seq.empty)
