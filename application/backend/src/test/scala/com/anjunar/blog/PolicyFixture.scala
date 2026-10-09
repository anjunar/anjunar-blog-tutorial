package com.anjunar.blog

import jakarta.annotation.security.{DenyAll, PermitAll, RolesAllowed}
import org.scalatest.funsuite.AnyFunSuite

@RolesAllowed(Array("ADMIN"))
class PolicyFixture {
  def inherited(): Unit = ()
  @PermitAll def publicMethod(): Unit = ()
  @DenyAll def closed(): Unit = ()
  @RolesAllowed(Array("READER")) def reader(): Unit = ()
  @PermitAll @DenyAll def conflicting(): Unit = ()
}
