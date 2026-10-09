package com.anjunar.blog

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}
import java.util.Locale
import java.util.concurrent.Semaphore
import javax.imageio.ImageIO
import javax.imageio.stream.MemoryCacheImageInputStream
import scala.util.control.NonFatal

final case class DecodedImage(contentType: String, width: Int, height: Int, bytes: Array[Byte])

