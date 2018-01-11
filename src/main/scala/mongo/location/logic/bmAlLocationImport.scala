package mongo.location.logic

import com.mongodb.casbah.Imports._
import mongo.bmMongoDriver
import mongo.location.bmLocationData
import play.api.libs.json.JsValue

object bmAlLocationImport extends bmLocationData {
    /**
      * @张弛
      *    将程序写城范式化是写好代码的第一步，刚写了Brand后
      *    只需要简单的额修改就能Location
      *    后期只需要将类似的提出来，形成结构化的代码
      */
    def insertLocation(map : Map[String, String]) : Option[BMLocation] = {
        if (isNewLocation(map))
            bmMongoDriver.insertObject(map, "locations", "_id")

        lastPushLoaction
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