error id: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/view/View.scala:`<none>`.
file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/view/View.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1606
uri: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/view/View.scala
text:
```scala
package frontend.view

import com.raquo.laminar.api.L.*
import shared.User
import frontend.controller.FrontendController

val usersVar: Var[List[User]] = Var(List.empty)

    val appElement = div(
    h1("Frontend Portal", cls := "title"),
    nav(
        cls := "navbar",
        a(href := "#", "Home", onClick --> (_ => usersVar.set(Nil)), cls := "nav-link"),
        span(" | "),
        a(href := "#users", "Users", onClick --> (_ => FrontendController.fetchUsers(usersVar)), cls := "nav-link")
    ),
    div(
        cls := "content",
        
        child <-- usersVar.signal.map {
        case Nil => div("Welcome to Home Page", cls := "home-message")
        case _   => emptyNode
        },

        ul(cls := "feature-list",
        li("Built with Scala 3, Laminar, and HTTP4s"),
        li("Reactive UI using Laminar's observable system"),
        li("Backend/Frontend shared models using crossProject"),
        li("Hosted locally and ready for GitHub deployment")
        ),

        child <-- usersVar.signal.map {
        case Nil => emptyNode
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
    ),
    footer(
        cls := "footer",
        p("© 2025 Frontend Portal. All rights reser@@ved.")
    )
    )

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.