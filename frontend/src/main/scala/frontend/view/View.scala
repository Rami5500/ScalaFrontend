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
    cls := "app-container",

    header(
      cls := "main-header",
      div(cls := "header-title", "Frontend Portal"),
      nav(
        cls := "navbar",
        a(href := "#", "Home", onClick --> (_ => FrontendController.usersVar.set(Nil)), cls := "nav-link"),
        span(" | "),
        a(href := "#users", "Users", onClick --> (_ => FrontendController.fetchUsers()), cls := "nav-link")
      )
    ),

    main(cls := "main-content",
      child <-- FrontendController.usersVar.signal.map {
        case Nil => div(
          cls := "home-page",
          div(cls := "zip-section",
            h2("Find an address", cls := "zip-heading"),
            p("Type a part of address or postcode to begin", cls := "zip-subtitle"),
            div(cls := "zip-input-group",
              input(
                cls := "zip-input",
                typ := "text",
                placeholder := "E.g. 'CRO 3RL' or '36 Factory Lane'",
                onInput.mapToValue --> zipCodeVar
              ),
              button("Search", cls := "zip-button", onClick --> { _ =>
                ZipCodeController.lookupZip(zipCodeVar.now(), errorMessageVar, resultAddressVar)
              })
            ),
            child.maybe <-- errorMessageVar.signal.map(_.map(msg =>
              div(cls := "zip-error", msg)
            )),
            child.maybe <-- resultAddressVar.signal.map(_.map(addr =>
              div(cls := "zip-result", s"Address: $addr")
            )),
            div(cls := "zip-links",
              a(href := "#", "Alias Addresses", cls := "zip-link"), br(),
              a(href := "#", "Can't find the address you're looking for?", cls := "zip-link"), br(),
              a(href := "#", "If address looks incorrect please contact us to let us know.", cls := "zip-link")
            )
          )
        )
        case users => div(
          ul(cls := "user-list",
            children <-- Val(users.map(user =>
              li(cls := "user-item",
                div(b("Name: "), user.name),
                div(b("Email: "), user.email),
                div(b("Age: "), user.age.toString),
                div(b("Active: "), if (user.isActive) "✅" else "❌")
              )
            ))
          )
        )
      }
    ),

    footer(cls := "footer",
      p("Built with Scala 3, Laminar, and HTTP4s"),
      p("© 2025 Frontend Portal. All rights reserved.")
    )
  )

}