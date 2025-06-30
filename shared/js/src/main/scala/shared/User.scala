package shared

import upickle.default.{ReadWriter, macroRW}

case class User(id: Int, name: String, email: String, age: Int, isActive: Boolean)

object User {
    given ReadWriter[User] = macroRW
}