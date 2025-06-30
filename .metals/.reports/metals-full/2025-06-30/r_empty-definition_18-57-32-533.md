error id: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/controller/FrontendController.scala:`<none>`.
file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/controller/FrontendController.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -com/raquo/laminar/api/L.Ajax.
	 -scala/scalajs/js/annotation/Ajax.
	 -org/scalajs/dom/ext/Ajax.
	 -upickle/default/Ajax.
	 -Ajax.
	 -scala/Predef.Ajax.
offset: 397
uri: file:///C:/GitHub/ScalaFrontend/frontend/src/main/scala/frontend/controller/FrontendController.scala
text:
```scala
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

    @@Ajax.get("http://localhost:8080/api/users")
        .map(_.responseText)
        .map(read[List[User]])
        .foreach(usersVar.set)
    }

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.