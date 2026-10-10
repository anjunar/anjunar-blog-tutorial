package com.anjunar.blog.frontend

import org.scalajs.dom
import ui.core.component.AbstractComponent
import ui.core.dsl.AttributeDsl.*
import ui.core.dsl.ClassDsl.classes
import ui.core.dsl.DslLayer.render
import ui.core.dsl.EventDsl.on
import ui.core.i18n.i18n
import ui.core.layout.Button.{button, buttonType}
import ui.core.layout.Condition.when
import ui.core.layout.Div.div
import ui.core.layout.Label.label
import ui.core.layout.Paragraph.paragraph
import ui.core.layout.TextComponent.text
import ui.core.render.{Cursor, DomNodes, HostNode}
import ui.core.state.Property
import ui.forms.Form.form
import ui.forms.Input.{input, inputType, inputType_=}
import ui.forms.SelectInput.selectInput
import ui.forms.SelectOption
import ui.forms.validators.Size
import ui.router.Router
import ui.router.RouterLink.routerLink

import scala.annotation.meta.field
import scala.scalajs.js

// Search controls are route state, separate from the editable post model.
final class PostSearchFields(search: PostSearch) {
  @(Size @field)(max = 100)
  val query: Property[String] = Property(search.query)
  val status: Property[String] = Property(search.status)
  val sort: Property[String] = Property(search.sort)
  val limit: Property[String] = Property(search.limit.toString)

  def submitted(editorial: Boolean): PostSearch = {
    val values = Map("q" -> query.get, "status" -> status.get, "sort" -> sort.get, "limit" -> limit.get)
    // A new search always starts at offset zero.
    PostSearch.parse(values.get, editorial)
  }
}
