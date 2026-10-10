package com.anjunar.blog.frontend

import ui.core.state.{ListProperty, Property}
import ui.forms.validators.{NotBlank, Pattern, Size}
import ui.json.{JsonId, JsonIgnore, JsonMapper, JsonProperty}

import scala.annotation.meta.field
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*

final case class PostSnapshot(
  slug: String,
  title: String,
  content: String,
  summary: String,
  authorId: Option[String] = None,
  tagIds: Set[String] = Set.empty,
  coverAlt: String = null,
  coverImageId: Option[String] = None,
  contentFormat: String = "PLAIN_TEXT"
) {
  def values: Seq[String] = Seq(slug, title, content, summary, coverAlt, contentFormat)
  def value(name: String): Option[Any] = name match {
    case "slug"          => Some(slug)
    case "title"         => Some(title)
    case "content"       => Some(content)
    case "contentFormat" => Some(contentFormat)
    case "summary"       => Some(summary)
    case "coverAlt"      => Some(coverAlt)
    case "coverImage"    => Some(coverImageId)
    case "author"        => Some(authorId)
    case "tags"          => Some(tagIds)
    case _               => None
  }
}
