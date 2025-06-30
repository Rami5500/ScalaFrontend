package backend.controller

import cats.effect.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.circe.CirceEntityCodec._
import io.circe.generic.auto.*
import org.http4s.implicits.*
import com.comcast.ip4s.*
import shared.User
import org.http4s.server.middleware.CORS

object UserController {
    val users = List(
        User(1, "Alice", "alice@example.com", 30, true),
        User(2, "Bob", "bob@example.com", 42, false),
        User(3, "Charlie", "charlie@example.com", 25, true)
    )

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "api" / "users" =>
      Ok(users)
  }
}