package com.anjunar.blog

import jakarta.annotation.security.PermitAll

import com.anjunar.json.mapper.provider.DTO
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.servlet.http.HttpServletRequest
import jakarta.ws.rs.{Consumes, POST, Path, Produces}
import jakarta.ws.rs.core.{Context, MediaType, Response}

import scala.annotation.meta.field
import scala.compiletime.uninitialized

final class RecoveryResult(@(JsonbProperty @field) val outcome: String) extends DTO
