package com.anjunar.blog.frontend

import ui.core.state.Property

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.util.{Failure, Success, Try}

enum SaveNotice {
  case Idle, Saved, NewerEdits, Invalid, Conflict, SignedOut, Forbidden, Unconfirmed, BindingFailed

  def isError: Boolean = this match {
    case Idle | Saved | NewerEdits => false
    case _                         => true
  }
}
