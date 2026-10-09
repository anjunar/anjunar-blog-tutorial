package com.anjunar.blog

import com.anjunar.json.mapper.intermediate.JsonParser
import com.anjunar.json.mapper.intermediate.model.{JsonObject, JsonString}
import jakarta.mail.internet.InternetAddress
import jakarta.ws.rs.{BadRequestException, Consumes, WebApplicationException}
import jakarta.ws.rs.core.{MediaType, MultivaluedMap, Response}
import jakarta.ws.rs.ext.{MessageBodyReader, Provider}

import java.io.InputStream
import java.lang.annotation.Annotation
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets.UTF_8
import scala.jdk.CollectionConverters.*
import scala.util.control.NonFatal

final case class EmailRequest(email: String)
