package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.json.{JsonMapper, JsonSchema}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import scala.util.Try

final class HttpFailure(val status: Int, val problem: Option[ProblemDetails] = None)
    extends RuntimeException(s"HTTP $status")
