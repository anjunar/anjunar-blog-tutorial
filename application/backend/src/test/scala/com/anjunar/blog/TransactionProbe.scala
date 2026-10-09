package com.anjunar.blog

import jakarta.annotation.security.PermitAll

import com.arjuna.ats.jta.{UserTransaction as NarayanaUserTransaction}
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Status
import jakarta.ws.rs.{GET, POST, Path, PathParam, Produces}
import jakarta.ws.rs.core.{Response, StreamingOutput}

import java.lang
import java.io.IOException
import java.nio.charset.StandardCharsets.UTF_8
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import scala.compiletime.uninitialized

object TransactionProbe {
  // Set once by the database suite; the generated identifier contains only letters and digits.
  var table: String = ""
  val committed = ConcurrentHashMap.newKeySet[UUID]()
}
