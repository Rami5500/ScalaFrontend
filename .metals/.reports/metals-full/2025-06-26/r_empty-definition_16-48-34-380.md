error id: file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/controller/UserController.scala:`<none>`.
file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/controller/UserController.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 447
uri: file:///C:/GitHub/ScalaFrontend/backend/src/main/scala/backend/controller/UserController.scala
text:
```scala
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
    User(1, "Alice", "alice@example.com", 30, tru@@e),
    User(2, "Bob", "bob@example.com", 42, false),
    User(3, "Charlie", "charlie@example.com", 25, true)
  )

  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "api" / "users" =>
      Ok(users)
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.