package frontend.controller

import com.raquo.laminar.api.L._
import org.scalajs.dom.ext.Ajax
import scala.concurrent.ExecutionContext.Implicits.global

object ZipCodeController {

  val suggestionsVar: Var[List[String]] = Var(Nil)

  def lookupZip(
      query: String,
      errorVar: Var[Option[String]]
  ): Unit = {
    val sanitized = query.trim
    val apiUrl = s"https://api.postcodes.io/postcodes?q=$sanitized"

    println(s"[INFO] 🔍 Calling Postcodes.io API: $apiUrl")
    suggestionsVar.set(Nil)
    errorVar.set(None)

    Ajax.get(apiUrl).map(_.responseText).foreach { response =>
      val json = ujson.read(response)
      val results = json("result").arrOpt.getOrElse(Seq.empty)

      if (results.isEmpty) {
        errorVar.set(Some(s"We couldn't find any matches for: '$query'. Try checking the spelling and searching again."))
      } else {
        val addresses = results.map(r =>
          s"${r("admin_district").str}, ${r("region").str}, ${r("country").str}, ${r("postcode").str}"
        ).toList

        suggestionsVar.set(addresses)
      }
    }
  }
}