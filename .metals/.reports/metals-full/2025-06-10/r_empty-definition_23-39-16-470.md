error id: file:///C:/Cognizant/Scala/FrontendPortal/backend/src/main/scala/backend/Server.scala:`<none>`.
file:///C:/Cognizant/Scala/FrontendPortal/backend/src/main/scala/backend/Server.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -cats/effect/org/http4s/circe/CirceEntityCodec.
	 -org/http4s/org/http4s/circe/CirceEntityCodec.
	 -org/http4s/dsl/io/org/http4s/circe/CirceEntityCodec.
	 -org/http4s/circe/CirceEntityCodec.org.http4s.circe.CirceEntityCodec.
	 -io/circe/generic/auto/org/http4s/circe/CirceEntityCodec.
	 -org/http4s/implicits/org/http4s/circe/CirceEntityCodec.
	 -com/comcast/ip4s/org/http4s/circe/CirceEntityCodec.
	 -org/http4s/circe/CirceEntityCodec.
	 -scala/Predef.org.http4s.circe.CirceEntityCodec.
offset: 172
uri: file:///C:/Cognizant/Scala/FrontendPortal/backend/src/main/scala/backend/Server.scala
text:
```scala
package backend

import cats.effect.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.circe.CirceEn@@tityCodec._    // ✅ Enables automatic JSON encoding
import io.circe.generic.auto.*
import org.http4s.implicits.*
import com.comcast.ip4s.*                     // ✅ Needed for ipv4 and port
import shared.User

object Server extends IOApp {

  val users = List(User(1, "Alice"), User(2, "Bob"))

  val userRoutes = HttpRoutes.of[IO] {
    case GET -> Root / "api" / "users" =>
      Ok(users) // ✅ Now works because CirceEntityCodec is imported
  }

  val httpApp = userRoutes.orNotFound

  def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder.default[IO]
      .withHost(ipv4"0.0.0.0")  // ✅ Typed IP address from ip4s
      .withPort(port"8080")     // ✅ Typed port from ip4s
      .withHttpApp(httpApp)
      .build
      .useForever
      .as(ExitCode.Success)
  }

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.