import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.scalajs.js.annotation.*
import shared.User
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.*

@main def runApp(): Unit = {
  render(dom.document.body, appElement)
}

val usersVar = Var[List[User]](Nil)

val appElement = div(
  h1("Frontend Portal", cls := "title"),
  nav(
    cls := "navbar",
    a(href := "#", "Home", onClick --> (_ => usersVar.set(Nil)), cls := "nav-link"),
    span(" | "),
    a(href := "#users", "Users", onClick --> (_ => fetchUsers()), cls := "nav-link")
  ),
  div(
    cls := "content",
    child <-- usersVar.signal.map {
      case Nil => div("Welcome to Home Page", cls := "home-message")
      case users => ul(
        cls := "user-list",
        children <-- Val(users.map(user =>
          li(cls := "user-item",
            div(b("Name: "), user.name),
            div(b("Email: "), user.email),
            div(b("Age: "), user.age.toString),
            div(b("Active: "), if (user.isActive) "✅" else "❌")
          )
        ))
      )
    }
  )
)


def fetchUsers(): Unit = {
  import org.scalajs.dom.ext.Ajax

  Ajax.get("http://localhost:8080/api/users")
    .map(_.responseText)
    .map(read[List[User]](_))
    .foreach(usersVar.set)
}

given ReadWriter[User] = macroRW
