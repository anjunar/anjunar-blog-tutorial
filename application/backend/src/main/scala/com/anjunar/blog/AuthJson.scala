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

object AuthJson {
  def read(stream: InputStream, fields: Map[String, Int]): Map[String, String] = {
    val bytes = stream.readNBytes(4097)
    if (bytes.length > 4096) throw new WebApplicationException(Response.status(413).build())
    try {
      val json = JsonParser.parse(new String(bytes, UTF_8)) match {
        case value: JsonObject => value
        case _ => throw new BadRequestException()
      }
      if (json.value.keySet().asScala.toSet != fields.keySet) throw new BadRequestException()
      fields.map { (name, max) =>
        val value = json.value.get(name) match {
          case value: JsonString if value.value.length <= max => value.value
          case _ => throw new BadRequestException()
        }
        name -> value
      }
    } catch { case NonFatal(_) => throw new BadRequestException("Invalid authentication request") }
  }

  def email(value: String): String = {
    val email = Account.canonicalEmail(value)
    try {
      require(email.nonEmpty && email.length <= 254 && email.forall(c => c > ' ' && c < 127))
      val address = new InternetAddress(email, true)
      address.validate()
      require(address.getAddress == email && address.getPersonal == null && email.contains("@"))
      email
    } catch { case NonFatal(_) => throw new BadRequestException("Enter a valid email address") }
  }
}
