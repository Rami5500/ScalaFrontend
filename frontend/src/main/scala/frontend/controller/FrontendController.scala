package frontend.controller

import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.scalajs.js.annotation.*
import com.raquo.laminar.api.L.Var
import org.scalajs.dom.ext.Ajax
import shared.User
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.*

object FrontendController {

  val secretKey = "3f5a8c92e0b94a49a8e4b63f7cf08d0e3f5a8c92e0b94a49a8e4b63f7cf08d0e"

//   val claim = JwtClaim(
//     content = """{"role":"admin"}""",
//     expiration = Some(Instant.now.plusSeconds(3600).getEpochSecond),
//     issuedAt = Some(Instant.now.getEpochSecond)
//   )

  val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4ifQ.X-1Z4_jOxBA9kWxxBeGwUFdvFXLi_f0tQIWdZYMW4wI"
  var jwtToken: Option[String] = Some(token)

  val usersVar: Var[List[User]] = Var(List.empty)

  def fetchUsers(): Unit = {
    jwtToken match {
      case Some(token) =>
        Ajax.get(
          url = "http://localhost:8080/api/users",
          headers = Map("Authorization" -> s"Bearer $token")
        ).map(_.responseText)
         .map(read[List[User]](_))
         .foreach(usersVar.set)

      case None =>
        println("JWT not set.")
    }
  }
}

    // def fetchUsers(usersVar: Var[List[User]]): Unit = {
    //     Ajax.get("http://localhost:8080/api/users")
    //         .map(_.responseText)
    //         .map(read[List[User]](_))
    //         .foreach(usersVar.set)
    // }

// return view
// bind information from model