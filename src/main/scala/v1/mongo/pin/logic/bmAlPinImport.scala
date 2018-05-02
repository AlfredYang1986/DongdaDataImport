package v1.mongo.pin.logic

import com.mongodb.casbah.Imports._
import v1.mongo.bmMongoDriver
import v1.mongo.location.bmLocationData
import play.api.libs.json.JsValue

import scala.xml
import scala.xml.XML

case class pin(var s : String, var log : Double, var lat : Double)

object bmAlPinImport extends bmLocationData {

    def queryFromDataFile = {
        val data = XML.loadFile("test/location.xml")
        (data \ "dict").map { iter =>
            val p = new pin("", 0, 0)
            (iter \ "_").zipWithIndex.foreach { x =>
                if (x._2 == 1) p.s = x._1.text
                else if (x._2 == 3) p.lat = x._1.text.toDouble
                else if (x._2 == 5) p.log = x._1.text.toDouble
                else Unit
            }
            p
        }
    }
}

