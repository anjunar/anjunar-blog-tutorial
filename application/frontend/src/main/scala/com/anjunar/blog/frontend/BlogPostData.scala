package com.anjunar.blog.frontend

import ui.core.state.{ListProperty, Property}
import ui.forms.validators.{NotBlank, Pattern, Size}
import ui.json.{JsonId, JsonIgnore, JsonMapper, JsonProperty}

import scala.annotation.meta.field
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*

final class BlogPostData(
  var data: BlogPost = null,
  @(JsonProperty @field)("$links") var links: Seq[ApiLink] = Seq.empty
)
