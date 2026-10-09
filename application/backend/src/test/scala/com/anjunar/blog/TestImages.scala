package com.anjunar.blog

import org.scalatest.funsuite.AnyFunSuite

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.nio.charset.StandardCharsets.UTF_8
import javax.imageio.ImageIO

object TestImages {
  def bytes(format: String = "png", width: Int = 4, height: Int = 3): Array[Byte] = {
    val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    image.setRGB(0, 0, 0x336699)
    val output = new ByteArrayOutputStream()
    assert(ImageIO.write(image, format, output))
    output.toByteArray
  }
}

