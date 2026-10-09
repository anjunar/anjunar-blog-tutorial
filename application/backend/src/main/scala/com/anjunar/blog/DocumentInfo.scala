package com.anjunar.blog

import org.commonmark.node.{Code, FencedCodeBlock, HtmlBlock, HtmlInline, Image, IndentedCodeBlock, Link, Node, Text}
import org.commonmark.parser.Parser

import java.net.URI
import java.util.{Locale, UUID}
import scala.collection.mutable
import scala.util.control.NonFatal

final case class DocumentInfo(images: Set[UUID], hasContent: Boolean)

