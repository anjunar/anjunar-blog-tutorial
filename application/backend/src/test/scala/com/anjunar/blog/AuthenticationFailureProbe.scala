package com.anjunar.blog

import jakarta.annotation.security.PermitAll

import com.arjuna.ats.jta.{UserTransaction as NarayanaUserTransaction}
import io.undertow.servlet.handlers.ServletRequestContext
import jakarta.annotation.Priority
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.inject.spi.{CDI, PassivationCapable}
import jakarta.inject.Inject
import jakarta.security.enterprise.{SecurityContext as JakartaSecurityContext}
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanismHandler
import jakarta.security.enterprise.identitystore.IdentityStoreHandler
import jakarta.servlet.http.HttpServletRequest
import jakarta.ws.rs.{GET, Path, Produces}
import jakarta.ws.rs.core.{Context, MediaType, SecurityContext as RestSecurityContext}
import jakarta.ws.rs.ext.{Provider, WriterInterceptor, WriterInterceptorContext}

import java.io.IOException
import java.security.Principal
import scala.compiletime.uninitialized

// Test-only: force failures after credentials have passed through Soteria.
@Provider
@Priority(6000)
@RequestScoped
class AuthenticationFailureProbe extends WriterInterceptor {
  private var applied = false
  @Context var request: HttpServletRequest = uninitialized

  override def aroundWriteTo(context: WriterInterceptorContext): Unit = {
    val failure = if (applied) null else request.getHeader("X-Test-Auth-Failure")
    applied = true
    failure match {
      case "writer" => throw new IOException("Intentional authentication response failure")
      case "commit" => NarayanaUserTransaction.userTransaction().setRollbackOnly()
      case _ => ()
    }
    context.proceed()
  }
}
