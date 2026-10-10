package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.Property
import ui.json.JsonMapper

import scala.concurrent.ExecutionContext
import scala.scalajs.js
import scala.util.{Failure, Success}

final class RecoveryActions(endpoint: String, initialToken: Option[String], accounts: AccountService)(using
  ExecutionContext
) {
  val fields = new RecoveryFields()
  val busy: Property[Boolean] = Property(false)
  val done: Property[Boolean] = Property(false)
  val error: Property[Int] = Property(0)
  private var token = initialToken
  private var disposed = false

  def submit(): Unit =
    if (!busy.get && !done.get && !disposed) {
      busy.set(true)
      error.set(0)
      val command = token match {
        case Some(value) => JsonMapper.serialize(new TokenPasswordCommand(value, fields.password.get))
        case None        => JsonMapper.serialize(new EmailCommand(fields.email.get))
      }
      fields.password.set("")
      accounts.session().flatMap(state =>
        HttpJson.post[RecoveryResult](s"/service/auth/$endpoint", command, state.csrfToken)
      )
        .onComplete { result =>
          if (!disposed) {
            result match {
              case Success(_)                    => done.set(true); token = None
              case Failure(failure: HttpFailure) => error.set(failure.status)
              case Failure(_)                    => error.set(503)
            }
            busy.set(false)
          }
        }
    }

  def requestAgain(): Unit = if (!busy.get && !disposed) { done.set(false); error.set(0) }

  def dispose(): Unit = {
    disposed = true
    token = None
    fields.password.set("")
  }
}
