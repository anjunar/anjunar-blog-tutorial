package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.forms.validators.Size
import ui.json.{JsonId, JsonIgnore, JsonMapper, JsonProperty}
import scala.scalajs.js
import scala.annotation.meta.field

final class LoginCredentials {
  val email: Property[String] = Property("")
  val password: Property[String] = Property("")
}
