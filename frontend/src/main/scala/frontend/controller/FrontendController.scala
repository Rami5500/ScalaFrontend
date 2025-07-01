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

    def fetchUsers(usersVar: Var[List[User]]): Unit = {
        Ajax.get("http://localhost:8080/api/users")
            .map(_.responseText)
            .map(read[List[User]](_))
            .foreach(usersVar.set)
    }
}

// return view
// bind information from model