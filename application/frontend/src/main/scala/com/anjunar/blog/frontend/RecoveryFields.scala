package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.Property
import ui.json.JsonMapper

import scala.concurrent.ExecutionContext
import scala.scalajs.js
import scala.util.{Failure, Success}

final class RecoveryFields {
  val email: Property[String] = Property("")
  val password: Property[String] = Property("")
}
