package v1.mongo.location.logic

import com.mongodb.casbah.Imports._
import v1.mongo.bmMongoDriver
import v1.mongo.location.bmLocationData
import v1.mongo.pin.logic.pin
import play.api.libs.json.JsValue

object bmAlLocationImport extends bmLocationData {
    /**
      * @张弛
      *    将程序写城范式化是写好代码的第一步，刚写了Brand后
      *    只需要简单的额修改就能Location
      *    后期只需要将类似的提出来，形成结构化的代码
      */
    def insertLocation(map : Map[String, String], pins : Seq[pin]) : (Option[BMLocation], Boolean) = {
        var is_new = false
        if (isNewLocation(map)) {
//            val o : DBObject = map

            val ad = map.get("场地位置").getOrElse("")
            val o : DBObject =
            pins.find(p => p.s == ad).map { pin =>
                val builder = MongoDBObject.newBuilder
                builder += "type" -> "Point"

                val lb = MongoDBList.newBuilder
                lb += pin.log
                lb += pin.lat

                builder += "coordinates" -> lb.result

                m2d2(map + ("pin" -> builder.result))
            }.getOrElse(m2d(map))

            bmMongoDriver.insertObject(o, "locations", "_id")
            is_new = true
        }

        (lastPushLoaction, is_new)
    }

    def pushLocationImages(images : DBObject, loc : BMLocation) : Unit = {
        val _id = new ObjectId(loc.locationId)
        bmMongoDriver.queryObject(DBObject("_id" -> _id), "locations") { x =>
            x += "location_images" -> images
            bmMongoDriver.updateObject(x, "locations", "_id")
            null
        }
    }

    def lastPushLoaction : Option[BMLocation] = {
        val result =
            bmMongoDriver.queryMultipleObject(DBObject(), "locations", "date", skip = 0, take = 1)

        if (result.isEmpty) None
        else Some(BMLocation(result.head))
    }

    def isNewLocation(map : Map[String, String]) : Boolean = !map.get("场地位置").get.isEmpty
}

case class BMLocation(val d : Map[String, JsValue]) {
    def locationId = d.get("_id").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
    def address = d.get("address").map (x => x.toString).getOrElse(throw new Exception)
    def friendliness = d.get("friendliness").map (x => x.toString).getOrElse(throw new Exception)
}