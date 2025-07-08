package frontend.view

import com.raquo.laminar.api.L.*
import frontend.controller.FrontendController
import frontend.controller.ZipCodeController
import org.scalajs.dom
import shared.User

object View {

  val zipCodeVar = Var("")
  val errorMessageVar = Var(Option.empty[String])
  val resultAddressVar = Var(Option.empty[String])

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
              div(b("Active: "), if (user.isActive) "✅" else "❌")
            )
          ))
        )
      }
    ),
    div(
      h2("Zip Code Lookup"),
      div(
        label("Enter Zip Code: "),
        input(
          typ := "text",
          onInput.mapToValue --> zipCodeVar
        ),
        button("Search", onClick --> { _ =>
          ZipCodeController.lookupZip(zipCodeVar.now(), errorMessageVar, resultAddressVar)
        })
      ),
      child.maybe <-- errorMessageVar.signal.map(_.map(msg => div(color := "red", msg))),
      child.maybe <-- resultAddressVar.signal.map(_.map(addr => div(color := "green", s"Address: $addr")))
    ),
    footer(
      cls := "footer",
      p("Â© 2025 Frontend Portal. All rights reserved.")
    )
  )
}