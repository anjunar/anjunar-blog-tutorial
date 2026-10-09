package com.anjunar.blog.frontend

import ui.json.JsonProperty
import scala.annotation.meta.field

final class FieldError(var path: Seq[String] = Seq.empty, var message: String = "")

