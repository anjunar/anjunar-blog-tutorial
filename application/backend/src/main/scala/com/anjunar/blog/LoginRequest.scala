package com.anjunar.blog

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.core.{MediaType, MultivaluedMap}
import jakarta.ws.rs.ext.{MessageBodyReader, Provider}

import java.io.InputStream
import java.lang.annotation.Annotation
import java.lang.reflect.Type

final case class LoginRequest(email: String, password: String)
