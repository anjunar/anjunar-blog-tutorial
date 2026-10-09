package com.anjunar.blog

import jakarta.enterprise.context.control.RequestContextController
import jakarta.enterprise.inject.se.SeContainerInitializer
import jakarta.persistence.EntityManager

import java.lang
import java.util.UUID
import scala.util.Using

object AdminBootstrap {
  def create(email: String, password: String)(using manager: EntityManager): UUID = {
    // A database lock also covers two operators starting this command concurrently.
    manager.createNativeQuery("select 1 from pg_advisory_xact_lock(725019331)", classOf[lang.Integer])
      .getSingleResult
    val admins = manager.createQuery("select count(a) from Account a where a.role = 'ADMIN'", classOf[lang.Long])
      .getSingleResult.longValue()
    require(admins == 0, "An administrator already exists; bootstrap refuses to replace credentials")
    val canonical = Account.canonicalEmail(email)
    require(Account.byEmail(canonical).isEmpty, "The email is already assigned to an account")
    val account = new Account()
    account.email = canonical
    account.role = "ADMIN"
    account.passwordHash = PasswordHash.create(password)
    manager.persist(account)
    manager.flush()
    account.id
  }
}

