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

final class RelationshipPage(initial: CatalogData, service: CatalogService)(using ExecutionContext)
    extends AbstractComponent {
  val tagName = "section"
  private val catalog = new CatalogState(initial, service)

  override def compose(cursor: Cursor): Unit = {
    addDisposable(() => catalog.dispose())
    render(this, cursor) {
      classes = "relationship-page"
      heading(1) { text(i18n"Authors and tags") {} }
      paragraph { text(i18n"Set public author names and maintain the tags shared by your posts.") {} }
      when(catalog.saved) { paragraph { role = "status"; text(i18n"Changes saved.") {} } }
      when(catalog.failed) { paragraph { role = "alert"; text(i18n"More entries could not be loaded. Try again.") {} } }
      heading(2) { text(i18n"Authors") {} }
      paragraph {
        text(i18n"Only active administrators can be selected as authors. Existing posts keep their attribution.") {}
      }
      foreach(catalog.authors) { row => child(new AuthorEditor(row, service, catalog.recordAuthor)) {} }
      when(catalog.nextAuthors.map(_.nonEmpty)) {
        button(i18n"Load more authors") {
          buttonType("button"); disabled = catalog.busy; onClick(_ => catalog.moreAuthors())
        }
      }
      heading(2) { text(i18n"Tags") {} }
      catalog.createTag.foreach { link =>
        child(new TagEditor(new BlogTagData(new BlogTag(), Seq(link)), service, catalog.recordTag)) {}
      }
      foreach(catalog.tags) { row => child(new TagEditor(row, service, catalog.recordTag)) {} }
      when(catalog.nextTags.map(_.nonEmpty)) {
        button(i18n"Load more tags") { buttonType("button"); disabled = catalog.busy; onClick(_ => catalog.moreTags()) }
      }
      paragraph { routerLink("/editorial") { text(i18n"Back to editorial") {} } }
    }
  }
}
