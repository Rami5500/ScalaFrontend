error id: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/Main.scala:`<none>`.
file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/Main.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 299
uri: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/Main.scala
text:
```scala
package frontend

import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.scalajs.js.annotation.*
import shared.User
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.*

@main def runApp(): Unit = {
  render(dom.document.body, appElement)
}

@@val usersVar = Var[List[User]](Nil)

// val appElement = div(
//   h1("Frontend Portal", cls := "title"),
//   nav(
//     cls := "navbar",
//     a(href := "#", "Home", onClick --> (_ => usersVar.set(Nil)), cls := "nav-link"),
//     span(" | "),
//     a(href := "#users", "Users", onClick --> (_ => fetchUsers()), cls := "nav-link")
//   ),
//   div(
//     cls := "content",
    
//     child <-- usersVar.signal.map {
//       case Nil => div("Welcome to Home Page", cls := "home-message")
//       case _   => emptyNode
//     },

//     ul(cls := "feature-list",
//       li("Built with Scala 3, Laminar, and HTTP4s"),
//       li("Reactive UI using Laminar's observable system"),
//       li("Backend/Frontend shared models using crossProject"),
//       li("Hosted locally and ready for GitHub deployment")
//     ),

//     child <-- usersVar.signal.map {
//       case Nil => emptyNode
//       case users => ul(
//         cls := "user-list",
//         children <-- Val(users.map(user =>
//           li(cls := "user-item",
//             div(b("Name: "), user.name),
//             div(b("Email: "), user.email),
//             div(b("Age: "), user.age.toString),
//             div(b("Active: "), if (user.isActive) "✅" else "❌")
//           )
//         ))
//       )
//     }
//   ),
//   footer(
//     cls := "footer",
//     p("© 2025 Frontend Portal. All rights reserved.")
//   )
// )

// def fetchUsers(): Unit = {
//   import org.scalajs.dom.ext.Ajax

//   Ajax.get("http://localhost:8080/api/users")
//     .map(_.responseText)
//     .map(read[List[User]](_))
//     .foreach(usersVar.set)
// }

given ReadWriter[User] = macroRW

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.