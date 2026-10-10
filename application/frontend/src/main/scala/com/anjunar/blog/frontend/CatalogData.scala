package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.{ListProperty, Property}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.util.{Failure, Success, Try}

final case class CatalogData(authors: AuthorTable, tags: BlogTagTable)
