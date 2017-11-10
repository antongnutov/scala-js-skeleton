package server

import cats.effect.IO
import com.typesafe.config.{Config, ConfigFactory}
import fs2.Stream
import org.http4s.server.blaze._
import org.http4s.util.{ExitCode, StreamApp}

object Application extends StreamApp[IO] with ApplicationService with LazyLogging with JokesGenerator {

  val config: Config = ConfigFactory.load()

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] = {

    printRuntimeInfo()

    val host = config.getString("http.host")
    val port = config.getInt("http.port")

    BlazeBuilder[IO]
      .bindHttp(port, host)
      .mountService(service, "/")
      .serve
  }

  private def printRuntimeInfo(): Unit = {
    val runtime = Runtime.getRuntime
    val mb = 1024 * 1024
    val maxMemoryInMb = runtime.maxMemory() / mb
    logger.info("JVM max memory: {} MB", maxMemoryInMb)
  }
}