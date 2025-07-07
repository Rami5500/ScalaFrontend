error id: file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/controller/UserController.scala:
file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/controller/UserController.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1637
uri: file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/controller/UserController.scala
text:
```scala
package backend.controller

import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import io.circe.parser.*
import io.circe.syntax.*
import io.circe.generic.auto.*
import pdi.jwt.{Jwt, JwtClaim}
import pdi.jwt.algorithms.JwtAlgorithm
import shared.User

object UserController {

  val secretKey = "my-secret"
  val algo = JwtAlgorithm.HS256

  val users = List(
    User(1, "Alice", "alice@example.com", 30, true),
    User(2, "Bob", "bob@example.com", 42, false),
    User(3, "Charlie", "charlie@example.com", 25, true)
  )

  def validateToken(token: String): Either[String, JwtClaim] =
    Jwt.decode(token, secretKey, Seq(algo)).toEither.left.map(_.getMessage)

  val routes = HttpRoutes.of[IO] {
    case req @ GET -> Root / "api" / "users" =>
      req.headers.get(org.http4s.headers.Authorization).flatMap { header =>
        val raw = header.value
        if (raw.startsWith("Bearer ")) Some(raw.stripPrefix("Bearer ").trim) else None
      } match {
        case Some(token) =>
          validateToken(token) match {
            case Right(claim) =>
              decode[Map[String, String]](claim.content) match {
                case Right(payload) if payload.get("role").contains("admin") =>
                  Ok(users.asJson)
                case _ =>
                  Forbidden("Invalid token content")
              }
            case Left(err) =>
              Forbidden(s"Invalid token: $err")
          }
        case None =>
          Forbidden("Missing token")
      }
  }
}

  // val routes = HttpRoutes.of[IO] {
  //   cas@@e GET -> Root / "api" / "users" =>
  //     Ok(users)
  // }

```


#### Short summary: 

empty definition using pc, found symbol in pc: 