package frontend.controller

import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.scalajs.js.annotation.*
import shared.User
import org.scalajs.dom.ext.Ajax
import com.raquo.laminar.api.L.Var
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.*

object FrontendController {

    var jwtToken: Option[String] = None

    def loginAndFetchToken(): Unit = {
        val loginJson = ujson.Obj("username" -> "test", "password" -> "1234")

        Ajax.post(
            url = "http://localhost:8080/api/login",
            data = loginJson.render(),
            headers = Map("Content-Type" -> "application/json")
        ).map { response =>
            val json = ujson.read(response.responseText)
            jwtToken = Some(json("token").str)
        }
    }

    def fetchUsers(): Unit = {
        jwtToken match {
            case Some(token) =>
            Ajax.get(
                url = "http://localhost:8080/api/users",
                headers = Map("Authorization" -> s"Bearer $token")
            ).map(_.responseText)
            .map(read[List[User]])
            .foreach(usersVar.set)

            case None =>
            println("No token, please log in first")
        }
    }
}

// return view
// bind information from model