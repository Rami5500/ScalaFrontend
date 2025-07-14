package frontend.controller

import com.raquo.laminar.api.L.*
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.*
import shared.User
// import pdi.jwt.*
// import java.time.Instant

object FrontendController {

  val secretKey = "3f5a8c92e0b94a49a8e4b63f7cf08d0e3f5a8c92e0b94a49a8e4b63f7cf08d0e"

  val usersVar: Var[List[User]] = Var(List.empty)

  // def createToken(livesHere: Boolean): String = {
  //   val claimContent =
  //     if (livesHere) """{"role":"admin", "liveshere":"true"}"""
  //     else """{"role":"admin", "liveshere":"false"}"""

  //   val claim = pdi.jwt.JwtClaim(
  //     content = claimContent
  //   )

  //   pdi.jwt.Jwt.encode(claim, secretKey, pdi.jwt.JwtAlgorithm.HS256)
  // }

  def validateLivesHere(token: String): Unit = {
    println(s"[FRONTEND] Sending token to /api/validate: $token")

    Ajax.get(
      url = "http://localhost:8080/api/validate",
      headers = Map("Authorization" -> s"Bearer $token")
    ).map(_.responseText).foreach { response =>
      println(s"[VALIDATION] 🔎 Server responded: $response")
    }
  }

  def fetchUsers(): Unit = {
    Ajax.get("http://localhost:8080/api/generate-token?liveshere=true")
      .map(_.responseText)
      .foreach { token =>
        Ajax.get(
          url = "http://localhost:8080/api/users",
          headers = Map("Authorization" -> s"Bearer $token")
        ).map(xhr => read[List[User]](xhr.responseText))
          .foreach(usersVar.set)
      }
  }
}