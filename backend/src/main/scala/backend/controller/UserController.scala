package backend.controller

import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import io.circe.syntax.*
import io.circe.parser.*
import io.circe.generic.auto.*
import pdi.jwt.{Jwt, JwtClaim}
// import pdi.jwt.algorithms.JwtHmacAlgorithm
import shared.User
import org.http4s.headers.Authorization
import org.typelevel.ci.CIString

object UserController {

  val secretKey = "3f5a8c92e0b94a49a8e4b63f7cf08d0e3f5a8c92e0b94a49a8e4b63f7cf08d0e"
  val algo = pdi.jwt.JwtAlgorithm.HS256



  def validateToken(token: String): Either[String, JwtClaim] = {
    Jwt.decode(token, secretKey, Seq(algo)).toEither.left.map(_.getMessage)
  }

  val users = List(
    User(1, "Alice", "alice@example.com", 30, true),
    User(2, "Bob", "bob@example.com", 42, false),
    User(3, "Charlie", "charlie@example.com", 25, true)
  )

  val routes = HttpRoutes.of[IO] {
    case req @ GET -> Root / "api" / "users" =>
      println("[INFO] Incoming request to /api/users")

      req.headers.get[Authorization] match {
        case Some(Authorization(Credentials.Token(AuthScheme.Bearer, token))) =>
          println(s"[INFO] ✅ JWT token received: $token")

          validateToken(token) match {
            case Right(claim) =>
              println(s"[INFO] Token successfully validated")
              println(s"[INFO] Token payload: ${claim.content}")

              decode[Map[String, String]](claim.content) match {
                case Right(payload) =>
                  val role = payload.getOrElse("role", "unknown")
                  println(s"[INFO] 🧾 Role in token: $role")

                  if (role == "admin") {
                    println("[SUCCESS] Authorized as admin. Returning users list.")
                    Ok(users.asJson)
                  } else {
                    println(s"[WARN] Unauthorized role: $role")
                    Forbidden("Invalid token content")
                  }

                case Left(err) =>
                  println(s"[ERROR] Failed to decode token content: $err")
                  Forbidden("Invalid token content")
              }

            case Left(errorMsg) =>
              println(s"[ERROR] Token validation failed: $errorMsg")
              Forbidden(s"Invalid token: $errorMsg")
          }

        case None =>
          println("[ERROR] Missing Authorization header")
          Forbidden("Missing token")
      }
  }
}

  // val routes = HttpRoutes.of[IO] {
  //   case GET -> Root / "api" / "users" =>
  //     Ok(users)
  // }
