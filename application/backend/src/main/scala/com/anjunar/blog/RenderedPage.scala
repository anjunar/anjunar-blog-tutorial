package com.anjunar.blog

import org.graalvm.polyglot.{Context, Engine, HostAccess, Source, Value}
import org.graalvm.polyglot.proxy.{ProxyExecutable, ProxyObject}

import java.lang
import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Path
import java.time.Duration
import java.util.Map as JavaMap
import java.util.concurrent.{ArrayBlockingQueue, FutureTask, ThreadPoolExecutor, TimeUnit, TimeoutException}
import java.util.concurrent.atomic.{AtomicBoolean, AtomicReference}
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

final case class RenderedPage(html: String, status: Int)
