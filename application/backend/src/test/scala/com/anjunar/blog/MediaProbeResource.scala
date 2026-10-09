package com.anjunar.blog

import jakarta.annotation.Priority
import jakarta.annotation.security.RolesAllowed
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.{POST, Path, QueryParam}
import jakarta.ws.rs.core.{Context, HttpHeaders, UriInfo}
import jakarta.ws.rs.ext.{Provider, WriterInterceptor, WriterInterceptorContext}

import java.io.IOException
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger
import scala.compiletime.uninitialized

@Path("/_test/media")
@RolesAllowed(Array("ADMIN"))
@RequestScoped
class MediaProbeResource {
  @Inject var lifecycle: MediaLifecycle = uninitialized
  @Inject var identity: SessionIdentity = uninitialized

  @POST @Path("/cleanup")
  def cleanup(@QueryParam("limit") limit: Int): String = {
    identity.checkCsrf()
    lifecycle.cleanup(Instant.now().minusSeconds(86400), Some(identity.requireAccount().id), limit).toString
  }
}

