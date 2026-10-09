package com.anjunar.blog

import com.anjunar.json.mapper.ErrorRequest
import com.anjunar.json.mapper.intermediate.model.{JsonArray, JsonNode, JsonNumber, JsonObject, JsonString}
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

import java.util
import scala.jdk.CollectionConverters.*

final class ApiProblem(val status: Int, val detail: String, val kind: String = "about:blank",
    val errors: Seq[ErrorRequest] = Seq.empty)
    extends WebApplicationException(Response.status(status).build())

