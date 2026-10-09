package com.anjunar.blog

import jakarta.annotation.security.{DenyAll, PermitAll, RolesAllowed}
import org.scalatest.funsuite.AnyFunSuite
class UnannotatedFixture { def closed(): Unit = () }

