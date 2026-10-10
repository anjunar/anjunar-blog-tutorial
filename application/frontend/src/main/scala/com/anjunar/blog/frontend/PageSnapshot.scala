package com.anjunar.blog.frontend

import com.anjunar.blog.frontend.HttpJson.Response
import org.scalajs.dom
import ui.core.state.Property
import ui.json.{JsonMapper, JsonSchema}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.scalajs.js.URIUtils.{decodeURIComponent, encodeURIComponent}
import scala.util.control.NonFatal

// This describes one public HTTP response, not another copy of the entity model.
final class PageSnapshot(
  var format: Int = 1,
  var url: String = "",
  var request: String = "",
  var status: Int = 0,
  var contentType: String = "",
  var body: String = ""
)
