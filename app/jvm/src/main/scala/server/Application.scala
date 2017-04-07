package server

import com.typesafe.config.{Config, ConfigFactory}
import org.http4s.server.blaze._
import org.http4s.server.{Server, ServerApp}

import scalaz.concurrent.Task

object Application extends ApplicationService with ServerApp with LazyLogging with JokesGenerator {

  val config: Config = ConfigFactory.load()

  override def server(args: List[String]): Task[Server] = {

    printRuntimeInfo()

    val host = config.getString("http.host")
    val port = config.getInt("http.port")

    BlazeBuilder
      .bindHttp(port, host)
      .mountService(service, "/")
      .start
  }

  private def printRuntimeInfo() = {
    val runtime = Runtime.getRuntime
    val mb = 1024 * 1024
    val maxMemoryInMb = runtime.maxMemory() / mb
    logger.info("JVM max memory: {} MB", maxMemoryInMb)
  }
}