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

@Provider
@Consumes(Array(MediaType.APPLICATION_JSON))
class TokenPasswordRequestReader extends MessageBodyReader[TokenPasswordRequest] {
  override def isReadable(
    clazz: Class[?],
    genericType: Type,
    annotations: Array[Annotation],
    mediaType: MediaType
  ): Boolean = clazz == classOf[TokenPasswordRequest]

  override def readFrom(
    clazz: Class[TokenPasswordRequest],
    genericType: Type,
    annotations: Array[Annotation],
    mediaType: MediaType,
    headers: MultivaluedMap[String, String],
    stream: InputStream
  ): TokenPasswordRequest = {
    val values = AuthJson.read(stream, Map("token" -> 43, "password" -> 128))
    if (!AccountToken.wellFormed(values("token")) || !PasswordHash.acceptable(values("password")))
      throw new BadRequestException("Use a valid link and a password with 15 to 128 characters")
    TokenPasswordRequest(values("token"), values("password"))
  }
}
