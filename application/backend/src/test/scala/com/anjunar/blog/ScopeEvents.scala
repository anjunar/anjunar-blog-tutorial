package com.anjunar.blog

import jakarta.annotation.security.PermitAll

import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.{ApplicationScoped, RequestScoped}
import jakarta.inject.Inject
import jakarta.ws.rs.{GET, Path, Produces}
import jakarta.ws.rs.container.{ContainerRequestContext, ContainerResponseContext, ContainerResponseFilter}
import jakarta.ws.rs.ext.Provider

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import scala.compiletime.uninitialized

object ScopeEvents {
  val destroyedRequests = ConcurrentHashMap.newKeySet[String]()
  val destroyedApplications = ConcurrentHashMap.newKeySet[String]()

  def clear(): Unit = {
    destroyedRequests.clear()
    destroyedApplications.clear()
  }
}
