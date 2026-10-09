package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.state.Property
import ui.json.JsonMapper

import scala.concurrent.ExecutionContext
import scala.scalajs.js
import scala.util.{Failure, Success}

object AccountLink {
  // A fragment-only navigation may reuse the document. Handle it before the router.
  def listen(): () => Unit = {
    val reopen: js.Function1[dom.Event, Unit] = event =>
      if (Set("/en/confirm", "/en/reset-password", "/de/confirm", "/de/reset-password").contains(dom.window.location.pathname) &&
          dom.window.location.hash.startsWith("#token=")) {
        event.stopImmediatePropagation()
        dom.window.location.reload()
      }
    dom.window.addEventListener("popstate", reopen, true)
    dom.window.addEventListener("hashchange", reopen, true)
    () => {
      dom.window.removeEventListener("popstate", reopen, true)
      dom.window.removeEventListener("hashchange", reopen, true)
    }
  }

  // Fragments are not sent in HTTP requests. Remove the secret from the visible URL too.
  def takeToken(): Option[String] = {
    val fragment = dom.window.location.hash.stripPrefix("#token=")
    val token = Option(fragment).filter(_.matches("[A-Za-z0-9_-]{43}"))
    if (dom.window.location.hash.nonEmpty)
      dom.window.history.replaceState(null, "", dom.window.location.pathname)
    token
  }
}
