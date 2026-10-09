package com.anjunar.blog

import jakarta.annotation.security.PermitAll

import com.anjunar.json.mapper.provider.DTO
import com.anjunar.json.mapper.schema.Link
import jakarta.inject.Inject
import jakarta.enterprise.context.RequestScoped
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.persistence.EntityManager
import jakarta.security.enterprise.{AuthenticationStatus, SecurityContext}
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters
import jakarta.security.enterprise.credential.UsernamePasswordCredential
import jakarta.servlet.http.{HttpServletRequest, HttpServletResponse}
import jakarta.ws.rs.{Consumes, GET, NotAuthorizedException, POST, Path, Produces, WebApplicationException}
import jakarta.ws.rs.core.{Context, MediaType, Response}

import java.util
import scala.compiletime.uninitialized
import scala.annotation.meta.field

final class SessionState(
    @(JsonbProperty @field) val csrfToken: String,
    @(JsonbProperty @field) val account: Account,
    @(JsonbProperty @field)("$links") val links: util.List[Link] = util.List.of()
) extends DTO
