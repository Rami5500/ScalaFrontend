package backend.controller

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import pdi.jwt.{JwtAlgorithm, JwtCirce, JwtClaim}
import pdi.jwt.algorithms.JwtHmacAlgorithm
// import pdi.jwt.JwtAlgorithm
import org.http4s.circe.CirceEntityDecoder._

import java.time.Instant

case class LoginRequest(username: String, password: String)
case class LoginResponse(token: String)

object LoginController {
  val secretKey = "supersecret"

  val routes = HttpRoutes.of[IO] {
    case req @ POST -> Root / "api" / "login" =>
      for {
        login <- req.as[LoginRequest]
        token = createToken(login.username)
        res <- Ok(LoginResponse(token).asJson)
      } yield res
  }

  def createToken(username: String): String = {
    val claim = JwtClaim(
      expiration = Some(Instant.now.plusSeconds(3600).getEpochSecond),
      issuedAt = Some(Instant.now.getEpochSecond),
      content = s"""{"user":"$username"}"""
    )
    JwtCirce.encode(claim, secretKey, JwtAlgorithm.HS256)
  }
}