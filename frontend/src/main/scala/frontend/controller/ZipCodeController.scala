package frontend.controller

import com.raquo.laminar.api.L.*
import org.scalajs.dom

object ZipCodeController {

  val zipDatabase: Map[String, String] = Map(
    "10001" -> "New York, NY",
    "90001" -> "Los Angeles, CA",
    "94105" -> "San Francisco, CA",
    "SW1A1AA" -> "London, UK"
  )

  def lookupZip(
      zip: String,
      errorVar: Var[Option[String]],
      addressVar: Var[Option[String]]
  ): Unit = {
    println(s"[INFO] Looking up ZIP code: $zip")

    zipDatabase.get(zip.trim) match {
      case Some(address) =>
        errorVar.set(None)
        addressVar.set(Some(address))
        println(s"[SUCCESS] Found address: $address")
      case None =>
        addressVar.set(None)
        errorVar.set(Some("Invalid ZIP code"))
        println(s"[ERROR] ZIP code not found.")
    }
  }
}