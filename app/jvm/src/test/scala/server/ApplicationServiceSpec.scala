package server

import cats.effect.IO
import com.typesafe.config.{Config, ConfigFactory}
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.util.CaseInsensitiveString
import org.scalatest.{Matchers, WordSpec}

/**
  * @author Anton Gnutov
  */
class ApplicationServiceSpec extends WordSpec with Matchers with ApplicationService with LazyLogging with JokesGenerator {
  val config: Config = ConfigFactory.load()

  "ApplicationService" should {
    var length: Int = 0

    "answer response for /" in {
      val req = Request[IO](Method.GET, uri("/"))
      val response = service.orNotFound.run(req).unsafeRunSync()

      response.status shouldEqual Status.Ok
      length = response.as[String].unsafeRunSync().length
    }

    "answer gzipped response for /" in {
      val acceptHeader = Header("Accept-Encoding", ContentCoding.gzip.coding.value)
      val req = Request[IO](Method.GET, uri("/")).putHeaders(acceptHeader)

      val response = service.orNotFound.run(req).unsafeRunSync()

      response.status shouldEqual Status.Ok
      response.headers.get(CaseInsensitiveString("Content-Encoding")) shouldEqual Some(Header("Content-Encoding", "gzip"))
      val size = response.as[String].unsafeRunSync().length
      size should be < length
    }

    "answer NotFound for unexisted resource" in {
      val req = Request[IO](Method.GET, uri("/images/ololo100500.jpeg"))
      val response = service.orNotFound.run(req).unsafeRunSync()

      response.status shouldEqual Status.NotFound
    }

    "answer joke on /getJoke request" in {
      val req = Request[IO](Method.GET, uri("/getJoke"))
      val response = service.orNotFound.run(req).unsafeRunSync()

      response.status shouldEqual Status.Ok
      val joke = response.as[String].unsafeRunSync()
      jokes should contain(joke)
    }
  }
}
