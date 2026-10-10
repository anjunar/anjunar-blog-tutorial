package com.anjunar.blog

import com.anjunar.json.mapper.{ErrorRequest, ErrorRequestException}
import jakarta.persistence.OptimisticLockException
import jakarta.validation.{ConstraintViolationException as ValidationException}
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.{Context, Response, UriInfo}
import jakarta.ws.rs.ext.{ExceptionMapper, Provider}
import org.hibernate.StaleStateException
import org.hibernate.exception.{ConstraintViolationException as SqlConstraintException}
import org.slf4j.LoggerFactory

import java.util
import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*

// RESTEasy has built-in handling for these types; register explicit application mappers.
@Provider
class WebProblemExceptionMapper extends ExceptionMapper[WebApplicationException] {
  @Context var uriInfo: UriInfo = uninitialized
  override def toResponse(error: WebApplicationException): Response =
    ProblemResponses.render(error, Option(uriInfo).map(_.getRequestUri.getPath).getOrElse(""))
}
