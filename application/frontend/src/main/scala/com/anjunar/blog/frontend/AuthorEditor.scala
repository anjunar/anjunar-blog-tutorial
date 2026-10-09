package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.component.AbstractComponent
import ui.core.dsl.AttributeDsl
import ui.core.dsl.AttributeDsl.*
import ui.core.dsl.ClassDsl.classes
import ui.core.dsl.DslLayer.{child, render}
import ui.core.dsl.EventDsl.{on, onClick}
import ui.core.i18n.i18n
import ui.core.layout.Button.{button, buttonType, disabled, disabled_=}
import ui.core.layout.Condition.when
import ui.core.layout.Div.div
import ui.core.layout.Heading.heading
import ui.core.layout.Label.label
import ui.core.layout.Paragraph.paragraph
import ui.core.layout.TextComponent.text
import ui.core.render.Cursor
import ui.core.statement.Foreach.foreach
import ui.forms.ErrorResponse
import ui.forms.Form.{form, editable, editable_=}
import ui.forms.Input.input
import ui.router.RouterLink.routerLink

import scala.concurrent.ExecutionContext
import scala.scalajs.js

final class AuthorEditor(initial: AuthorData, service: CatalogService, saved: AuthorData => Unit)
    (using ExecutionContext) extends AbstractComponent {
  val tagName = "section"
  private val account = initial.data
  private val update = initial.links.find(_.rel == "update").getOrElse(throw new HttpFailure(403))
  private val actions = new MetadataSave[AuthorData](body => service.saveAuthor(update, body), saved)

  override def compose(cursor: Cursor): Unit = {
    addDisposable(() => actions.dispose())
    render(this, cursor) {
      classes = "catalog-editor"
      LanguageNavigation.protect(actions.busy, account.displayName, account.version) {
        actions.busy.get || account.displayName.isDirty
      }
      form(account) { mounted ?=>
        ariaLabel = i18n"Author details"
        editable = actions.busy.map(!_)
        mounted.addDisposable(actions.fields.observe(values =>
          mounted.setErrorResponses(values.map(value => ErrorResponse(value.message, value.path)))))
        on("submit") { event =>
          event.preventDefault()
          mounted.clearErrors()
          if (mounted.validateBindings().isEmpty && mounted.validate().isEmpty) actions(account.writeBody())
        }
        label { AttributeDsl.setAttribute("for", s"author-name-${account.id.get}"); text(i18n"Public name") {} }
        val control = input("displayName") {
          id = s"author-name-${account.id.get}"
          AttributeDsl.setAttribute("aria-describedby", s"author-errors-${account.id.get}")
        }
        paragraph { id = s"author-errors-${account.id.get}"; classes = "field-error"; text(control.errors.map((values: js.Array[String]) => values.mkString(", "))) {} }
        when(actions.error.map(_.nonEmpty)) {
          paragraph {
            role = "alert"
            text(actions.error.map(value => value match {
              case Some(400) => i18n"Review the public name."
              case Some(409) => i18n"This author changed. Reload before saving again."
              case _ => i18n"The save could not be confirmed. Reload to check the author."
            })) {}
          }
        }
        button(i18n"Save author") { buttonType("submit"); disabled = actions.busy.flatMap(busy => actions.blocked.map(blocked => busy || blocked)) }
        when(actions.blocked) {
          button(i18n"Reload authors and tags") {
            buttonType("button")
            onClick(_ => if (cursor.isBrowser) dom.window.location.reload())
          }
        }
      }
    }
  }
}

