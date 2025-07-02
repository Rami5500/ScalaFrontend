package backend

import backend.controller.UserController
import cats.effect.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.circe.CirceEntityCodec._
import io.circe.generic.auto.*
import org.http4s.implicits.*
import com.comcast.ip4s.*
import shared.User
// import org.http4s.server.middleware.CORSConfig
import org.http4s.server.middleware.CORS
import scala.concurrent.duration.*


object Server extends IOApp {

  // val users = List(
  //   User(1, "Alice", "alice@example.com", 30, true),
  //   User(2, "Bob", "bob@example.com", 42, false),
  //   User(3, "Charlie", "charlie@example.com", 25, true)
  // )

  // val userRoutes = HttpRoutes.of[IO] {
  //   case GET -> Root / "api" / "users" =>
  //     Ok(users)
  // }

  // val httpApp = CORS.policy.withAllowOriginAll(userRoutes).orNotFound
  
  val httpApp = CORS.policy.withAllowOriginAll(UserController.routes).orNotFound

  def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder.default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .useForever
      .as(ExitCode.Success)
  }

}