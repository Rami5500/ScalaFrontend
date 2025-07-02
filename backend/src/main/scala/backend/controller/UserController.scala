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
import pdi.jwt.{JwtCirce, JwtClaim}
import io.circe._
import io.circe.parser._
import controllers.LoginController

object UserController {
  val users = List(
      User(1, "Alice", "alice@example.com", 30, true),
      User(2, "Bob", "bob@example.com", 42, false),
      User(3, "Charlie", "charlie@example.com", 25, true)
  )

  def validateToken(token: String): Boolean = {
    JwtCirce.decode(token, LoginController.secretKey, Seq(JwtAlgorithm.HS256)).isSuccess
  }

  val routes = HttpRoutes.of[IO] {
    case req @ GET -> Root / "api" / "users" =>
    req.headers.get(org.http4s.headers.Authorization).map(_.value.split(" ").last) match {
      case Some(token) if validateToken(token) =>
        Ok(userList.asJson)
      case _ =>
        Forbidden("Invalid or missing token")
    }
  }
}