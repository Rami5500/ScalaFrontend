error id: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/controller/ZipCodeController.scala:`<none>`.
file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/controller/ZipCodeController.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 296
uri: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/controller/ZipCodeController.scala
text:
```scala
package frontend.controller

import com.raquo.laminar.api.L._
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax
import upickle.default._

object ZipCodeController {

  val apiKey = "vBtIGN4kYeKPCPahIAVS9YtXHqLVE0pe" // Replace this with your actual key
  val baseUrl = "https://api@@.os.uk/search/places/v1/find"

  def lookupZip(zip: String, errorVar: Var[Option[String]], addressVar: Var[Option[String]]): Unit = {
    val fullUrl = s"$baseUrl?query=${dom.raw.encodeURIComponent(zip)}&key=$apiKey"

    println(s"[INFO] Calling OS API: $fullUrl")

    Ajax.get(fullUrl).map(_.responseText).foreach { response =>
      try {
        val address = extractFirstAddress(response)
        address match {
          case Some(addr) =>
            errorVar.set(None)
            addressVar.set(Some(addr))
            println(s"[SUCCESS] Found address: $addr")
          case None =>
            errorVar.set(Some("No address found."))
            addressVar.set(None)
        }
      } catch {
        case e: Exception =>
          println(s"[ERROR] Failed to parse address: ${e.getMessage}")
          errorVar.set(Some("Failed to process address."))
      }
    }
  }

  def extractFirstAddress(json: String): Option[String] = {
    val parsed = ujson.read(json)
    val results = parsed("results").arr

    if (results.nonEmpty) {
      Some(results(0)("DPA")("ADDRESS").str)
    } else None
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.