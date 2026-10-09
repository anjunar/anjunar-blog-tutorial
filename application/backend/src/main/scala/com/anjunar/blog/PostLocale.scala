package com.anjunar.blog

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.ws.rs.BadRequestException

import scala.compiletime.uninitialized

object PostLocale {
  def parse(raw: String): String = Option(raw).filter(_.nonEmpty).getOrElse("en") match {
    case locale @ ("en" | "de") => locale
    case _ => throw new BadRequestException("locale must be en or de")
  }
}
