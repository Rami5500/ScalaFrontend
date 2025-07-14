package frontend.view

import com.raquo.laminar.api.L.*
import frontend.controller.{FrontendController, ZipCodeController}
import shared.User
import org.scalajs.dom.ext.Ajax
import scala.concurrent.ExecutionContext.Implicits.global

object View {

  val zipCodeVar = Var("")
  val errorMessageVar = Var(Option.empty[String])
  val resultAddressVar = Var(Option.empty[String])
  val livesHereVar = Var(Option.empty[Boolean])
  val validationMessageVar = Var(Option.empty[String])

  val appElement = div(
    cls := "app-container",

    header(
      cls := "main-header",
      div(
        cls := "header-left",
        div(cls := "header-title", "Frontend Portal"),
        nav(
          cls := "navbar",
          a(href := "#", "Home", onClick --> (_ => FrontendController.usersVar.set(Nil)), cls := "nav-link"),
          span(" | "),
          a(href := "#users", "Users", onClick --> (_ => FrontendController.fetchUsers()), cls := "nav-link")
        )
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
            child.maybe <-- resultAddressVar.signal.map {
              case Some(_) => Some(
                div(
                  label(
                    input(
                      typ := "radio",
                      name := "livesHere",
                      onChange.mapTo(Some(true)) --> livesHereVar.writer
                    ),
                    " Yes, I live here"
                  ),
                  label(
                    input(
                      typ := "radio",
                      name := "livesHere",
                      onChange.mapTo(Some(false)) --> livesHereVar.writer
                    ),
                    " No, I do not live here"
                  )
                )
              )
              case None => None
            },
            child.maybe <-- livesHereVar.signal.map {
              case Some(_) => Some(
                button(
                  "Next",
                  onClick --> { _ =>
                    println(s"[STEP 6] Lives here selected: ${livesHereVar.now()}")

                    livesHereVar.now() match {
                      case Some(true) =>
                        println("[INFO] ✅ User confirmed they live at this address.")
                        Ajax.get("http://localhost:8080/api/validate?liveshere=true")
                          .map(_.responseText)
                          .foreach(response => {
                            println(s"[VALIDATION] Server says: $response")
                            validationMessageVar.set(Some(response))
                          })

                      case Some(false) =>
                        println("[INFO] ❌ User said they do NOT live there.")
                        Ajax.get("http://localhost:8080/api/validate?liveshere=false")
                          .map(_.responseText)
                          .foreach(response => {
                            println(s"[VALIDATION] Server says: $response")
                            validationMessageVar.set(Some(response))
                          })

                      case None =>
                        println("[WARN] No selection made.")
                    }
                  }
                )
              )
              case None => None
            },
            child.maybe <-- validationMessageVar.signal.map(_.map(msg =>
              div(cls := "validation-message", msg)
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