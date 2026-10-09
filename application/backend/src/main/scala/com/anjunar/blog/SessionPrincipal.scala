package com.anjunar.blog

import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.security.enterprise.CallerPrincipal
import jakarta.servlet.http.HttpServletRequest
import jakarta.ws.rs.{NotAuthorizedException, WebApplicationException}
import jakarta.ws.rs.core.Response

import java.nio.charset.StandardCharsets.UTF_8
import java.security.{MessageDigest, SecureRandom}
import java.time.Instant
import java.util.{Base64, UUID}
import scala.compiletime.uninitialized

final case class SessionPrincipal(accountId: UUID, authenticationVersion: Long, issuedAt: Instant)
    extends CallerPrincipal(accountId.toString) {
  override def getName: String = accountId.toString

  def active(account: Account, now: Instant): Boolean =
    account != null && !account.locked && account.authenticationVersion == authenticationVersion &&
      !now.isBefore(issuedAt) && now.isBefore(issuedAt.plusSeconds(SecurityConfig.absoluteSeconds))
}

