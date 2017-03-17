package server

import java.io.File

import com.typesafe.config.ConfigFactory
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze._

import scalaz.concurrent.Task

object Application extends ServerApp with LazyLogging {

  private val config = ConfigFactory.load()

  private val staticPath = config.getString("static-path") + "/"

  private val staticExtensions = List(".js", ".css", ".png", ".jpg", ".jpeg", ".map", ".html", ".webm", ".ico")

  override def server(args: List[String]): Task[Server] = {

    def staticFile(file: String, request: Request) = {
      StaticFile.fromFile(new File(staticPath + file), Some(request)).map(Task.now).getOrElse(NotFound())
    }

    val service = HttpService {
      case request @ GET -> Root =>
        logger.debug("Detected connection from '{}'", request.remoteAddr.getOrElse(""))
        staticFile("index.html", request)

      case request @ GET -> Root / path / resource if staticExtensions.exists(resource.endsWith) =>
        staticFile(s"$path/$resource", request)
    }

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