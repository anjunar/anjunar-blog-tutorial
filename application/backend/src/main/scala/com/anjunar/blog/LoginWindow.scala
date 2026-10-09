package com.anjunar.blog

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

import java.time.Instant
import java.util.concurrent.Semaphore
import scala.collection.mutable

private final case class LoginWindow(start: Long, count: Int)
