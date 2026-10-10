package com.anjunar.blog

import jakarta.annotation.security.PermitAll

import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.{ApplicationScoped, RequestScoped}
import jakarta.inject.Inject
import jakarta.ws.rs.{GET, Path, Produces}
import jakarta.ws.rs.container.{ContainerRequestContext, ContainerResponseContext, ContainerResponseFilter}
import jakarta.ws.rs.ext.Provider

import java.util
import java.util.concurrent.ConcurrentHashMap
import scala.compiletime.uninitialized

@PermitAll
@Path("/_test/scopes")
@RequestScoped
class ScopeProbeResource {
  @Inject
  var request: RequestProbe = uninitialized

  @Inject
  var application: ApplicationProbe = uninitialized

  @GET
  @Produces(Array("text/plain"))
  def read(): String = s"${request.id}|${application.id}"
}
