package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.{ListProperty, Property}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.util.{Failure, Success, Try}

final class MetadataSave[T](send: js.Dynamic => Future[T], accepted: T => Unit)(using ExecutionContext) {
  val busy: Property[Boolean] = Property(false)
  val blocked: Property[Boolean] = Property(false)
  val error: Property[Option[Int]] = Property(None)
  val fields: Property[Seq[FieldError]] = Property(Seq.empty)
  private var disposed = false

  def apply(body: js.Dynamic): Unit = {
    if (disposed || busy.get || blocked.get) return
    busy.set(true); error.set(None); fields.set(Seq.empty)
    Future.fromTry(Try(send(body))).flatten.onComplete {
      case Success(value) if !disposed => busy.set(false); accepted(value)
      case Failure(failure) if !disposed =>
        val http = failure match { case value: HttpFailure => Some(value); case _ => None }
        val errors = http.flatMap(_.problem).toSeq.flatMap(value => Option(value.errors).getOrElse(Seq.empty))
        fields.set(errors)
        val status = http.map(_.status).getOrElse(0)
        error.set(Some(status))
        blocked.set(status != 400 && !(status == 409 && errors.nonEmpty))
        busy.set(false)
      case _ => ()
    }
  }

  def dispose(): Unit = { disposed = true }
}
