package frontend.controller

import com.raquo.laminar.api.L.*
import org.scalajs.dom
import scala.scalajs.js.annotation.*
import shared.User
import org.scalajs.dom.ext.Ajax
import com.raquo.laminar.api.L.Var
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.*

import pdi.jwt.{Jwt, JwtClaim}
import pdi.jwt.algorithms.JwtHmacAlgorithm
import java.time.Instant


object FrontendController {

    val secretKey = "my-secret"

    val claim = JwtClaim(
        content = """{"role":"admin"}""",
        expiration = Some(Instant.now.plusSeconds(3600).getEpochSecond),
        issuedAt = Some(Instant.now.getEpochSecond)
    )

    val token = Jwt.encode(claim, secretKey, JwtAlgorithm.HS256)

    var jwtToken: Option[String] = Some(token)

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
            println("JWT not set.")
        }
    }

    // def fetchUsers(usersVar: Var[List[User]]): Unit = {
    //     Ajax.get("http://localhost:8080/api/users")
    //         .map(_.responseText)
    //         .map(read[List[User]](_))
    //         .foreach(usersVar.set)
    // }
}

// return view
// bind information from model