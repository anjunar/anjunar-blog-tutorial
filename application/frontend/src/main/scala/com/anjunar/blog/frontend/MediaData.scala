package com.anjunar.blog.frontend

import ui.core.state.Property
import ui.json.{JsonId, JsonIgnore, JsonProperty}

import scala.annotation.meta.field

final class MediaData(
  var data: Media = null,
  @(JsonProperty @field)("$links") var links: Seq[ApiLink] = Seq.empty
)
