package frontend.controller

import com.raquo.laminar.api.L._
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import upickle.default._
import scala.concurrent.ExecutionContext.Implicits.global

object ZipCodeController {

  def lookupZip(
      zip: String,
      errorVar: Var[Option[String]],
      addressVar: Var[Option[String]]
  ): Unit = {
    val sanitizedZip = zip.trim.replaceAll(" ", "")
    val fullUrl = s"https://api.postcodes.io/postcodes/$sanitizedZip"

    println(s"[INFO] Calling Postcodes.io API: $fullUrl")

    Ajax.get(fullUrl).map(_.responseText).foreach { response =>
      try {
        val address = extractAddressFromPostcode(response)
        address match {
          case Some(addr) =>
            errorVar.set(None)
            addressVar.set(Some(addr))
            println(s"[SUCCESS] Address found: $addr")
          case None =>
            errorVar.set(Some("No address found."))
            addressVar.set(None)
        }
      } catch {
        case e: Exception =>
          println(s"[ERROR] Failed to parse response: ${e.getMessage}")
          errorVar.set(Some("Invalid postcode or API error."))
          addressVar.set(None)
      }
    }
  }

  def extractAddressFromPostcode(json: String): Option[String] = {
    val parsed = ujson.read(json)
    val result = parsed("result")

    if (result.isNull) return None

    val postcode = result("postcode").str
    val region = result("region").str
    val adminDistrict = result("admin_district").str
    val country = result("country").str

    Some(s"$adminDistrict, $region, $country, $postcode")
  }
}