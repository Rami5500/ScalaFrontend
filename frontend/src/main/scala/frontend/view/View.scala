package frontend.view

import com.raquo.laminar.api.L.*
import frontend.controller.FrontendController
import shared.User

object View {
  val appElement = div(
    h1("Frontend Portal", cls := "title"),
    nav(
      cls := "navbar",
      a(href := "#", "Home", onClick --> (_ => FrontendController.usersVar.set(Nil)), cls := "nav-link"),
      span(" | "),
      a(href := "#users", "Users", onClick --> (_ => FrontendController.fetchUsers()), cls := "nav-link")
    ),
    div(
      cls := "content",

      child <-- FrontendController.usersVar.signal.map {
        case Nil => div("Welcome to Home Page", cls := "home-message")
        case _   => emptyNode
      },

      ul(cls := "feature-list",
        li("Built with Scala 3, Laminar, and HTTP4s"),
        li("Reactive UI using Laminar's observable system"),
        li("Backend/Frontend shared models using crossProject"),
        li("Hosted locally and ready for GitHub deployment")
      ),

      child <-- FrontendController.usersVar.signal.map {
        case Nil => emptyNode
        case users => ul(
          cls := "user-list",
          children <-- Val(users.map(user =>
            li(cls := "user-item",
              div(b("Name: "), user.name),
              div(b("Email: "), user.email),
              div(b("Age: "), user.age.toString),
              div(b("Active: "), if (user.isActive) "âœ…" else "âŒ")
            )
          ))
        )
      }
    ),
    footer(
      cls := "footer",
      p("Â© 2025 Frontend Portal. All rights reserved.")
    )
  )
}