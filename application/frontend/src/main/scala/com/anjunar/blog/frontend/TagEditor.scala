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

final class TagEditor(initial: BlogTagData, service: CatalogService, saved: BlogTagData => Unit)
    (using ExecutionContext) extends AbstractComponent {
  val tagName = "section"
  private val tag = initial.data
  private val creating = tag.id.get.isEmpty
  private val operation = initial.links.find(_.rel == (if (creating) "create" else "update"))
    .getOrElse(throw new HttpFailure(403))
  private val suffix = if (creating) "new" else tag.id.get
  private val actions = new MetadataSave[BlogTagData](body => service.saveTag(operation, body), value => {
    saved(value)
    if (creating) {
      tag.name.set(""); tag.name.setDefault("")
      tag.slug.set(""); tag.slug.setDefault("")
    }
  })

  override def compose(cursor: Cursor): Unit = {
    addDisposable(() => actions.dispose())
    render(this, cursor) {
      classes = "catalog-editor"
      LanguageNavigation.protect(actions.busy, tag.name, tag.slug, tag.version) {
        actions.busy.get || tag.name.isDirty || tag.slug.isDirty
      }
      heading(3) {
        if (creating) text(i18n"New tag") {} else text(tag.name) {}
      }
      form(tag) { mounted ?=>
        if (creating) ariaLabel = i18n"New tag" else ariaLabel = tag.name
        editable = actions.busy.map(!_)
        mounted.addDisposable(actions.fields.observe(values =>
          mounted.setErrorResponses(values.map(value => ErrorResponse(value.message, value.path)))))
        on("submit") { event =>
          event.preventDefault()
          mounted.clearErrors()
          if (mounted.validateBindings().isEmpty && mounted.validate().isEmpty) actions(tag.writeBody())
        }
        label { AttributeDsl.setAttribute("for", s"tag-name-$suffix"); text(if (creating) i18n"New tag name" else i18n"Tag name") {} }
        val nameControl = input("name") {
          id = s"tag-name-$suffix"
          AttributeDsl.setAttribute("aria-describedby", s"tag-name-errors-$suffix")
        }
        paragraph { id = s"tag-name-errors-$suffix"; classes = "field-error"; text(nameControl.errors.map((values: js.Array[String]) => values.mkString(", "))) {} }
        label { AttributeDsl.setAttribute("for", s"tag-slug-$suffix"); text(if (creating) i18n"New tag slug" else i18n"Tag slug") {} }
        val slugControl = input("slug") {
          id = s"tag-slug-$suffix"
          spellCheck = false
          AttributeDsl.setAttribute("aria-describedby", s"tag-slug-errors-$suffix")
        }
        paragraph { id = s"tag-slug-errors-$suffix"; classes = "field-error"; text(slugControl.errors.map((values: js.Array[String]) => values.mkString(", "))) {} }
        when(actions.error.map(_.nonEmpty)) {
          paragraph {
            role = "alert"
            text(actions.error.map(value => value match {
              case Some(400) => i18n"Review the tag fields."
              case Some(409) => i18n"The tag conflicts with saved data. Review the fields or reload."
              case _ => i18n"The save could not be confirmed. Reload to check the tag."
            })) {}
          }
        }
        button(if (creating) i18n"Create tag" else i18n"Save tag") {
          buttonType("submit")
          disabled = actions.busy.flatMap(busy => actions.blocked.map(blocked => busy || blocked))
        }
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
