package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.forms.validators.Size
import ui.json.{JsonId, JsonIgnore, JsonMapper, JsonProperty}
import scala.scalajs.js
import scala.annotation.meta.field

final class SessionState(
  var csrfToken: String = "",
  var account: Option[Account] = None,
  @(JsonProperty @field)("$links") var links: Seq[ApiLink] = Seq.empty
)
