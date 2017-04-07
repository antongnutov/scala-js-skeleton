package server

import java.io.File

import com.typesafe.config.Config
import org.http4s.dsl._
import org.http4s.server.middleware.GZip
import org.http4s.{HttpService, Request, StaticFile}

import scalaz.concurrent.Task

/**
  * @author Anton Gnutov
  */
trait ApplicationService { self: LazyLogging with JokesGenerator =>
  def config: Config

  private lazy val staticPath = config.getString("static-path") + "/"

  private val staticExtensions = List(".js", ".css", ".png", ".jpg", ".jpeg", ".map", ".html", ".webm", ".ico")

  private def staticFile(file: String, request: Request) = {
    StaticFile.fromFile(new File(staticPath + file), Some(request)).map(Task.now).getOrElse(NotFound())
  }

  private def isStaticResource(resource: String): Boolean = staticExtensions.exists(resource.endsWith)

  val service = GZip(
    HttpService {
      case request@GET -> Root =>
        logger.debug("Detected connection from '{}'", request.remoteAddr.getOrElse(""))
        staticFile("index.html", request)

      case GET -> Root / "getJoke" =>
        Ok(getJoke)

      case request@GET -> Root / path / resource if isStaticResource(resource) =>
        staticFile(s"$path/$resource", request)
    }
  )
}
