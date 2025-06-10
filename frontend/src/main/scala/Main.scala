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
  h1("Frontend Portal"),
  nav(
    a(href := "#", "Home", onClick --> (_ => usersVar.set(Nil))),
    " | ",
    a(href := "#users", "Users", onClick --> (_ => fetchUsers()))
  ),
  child <-- usersVar.signal.map {
    case Nil => div("Welcome to Home Page")
    case users => ul(children <-- Val(users.map(user => li(s"${user.id}: ${user.name}"))))
  }
)

def fetchUsers(): Unit = {
  import org.scalajs.dom.ext.Ajax

  Ajax.get("http://localhost:8080/api/users")
    .map(_.responseText)
    .map(read[List[User]](_))
    .foreach(usersVar.set)
}

given ReadWriter[User] = macroRW
