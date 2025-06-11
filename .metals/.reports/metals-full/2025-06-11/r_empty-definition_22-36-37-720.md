error id: file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/Server.scala:`<none>`.
file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/Server.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 376
uri: file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/Server.scala
text:
```scala
package backend

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


object Server extends IOApp {

  @@val users = List(User(1, "Alice"), User(2, "Bob"))

  val userRoutes = HttpRoutes.of[IO] {
    case GET -> Root / "api" / "users" =>
      Ok(users)
  }

  val httpApp = CORS.policy.withAllowOriginAll(userRoutes).orNotFound

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
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.