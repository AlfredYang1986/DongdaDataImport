package v2.mongo.service.logic

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue
import v1.mongo.bmMongoDriver
import v2.mongo.service.bmServiceData

object bmAlServiceImport extends bmServiceData {
    def insertService(map : Map[String, String]) : Option[BMService] = {
        if (isNewService(map))
            bmMongoDriver.insertObject(map, "services", "_id")

        lastPushService
    }

    def lastPushService : Option[BMService] = {
        val result =
            bmMongoDriver.queryMultipleObject(DBObject(), "services", "date", skip = 0, take = 1)

        if (result.isEmpty) None
        else Some(BMService(result.head))
    }

    def isNewService(map : Map[String, String]) : Boolean = true

    def pushServiceImages(images : DBObject, ser : BMService) : Unit = {
        val _id = new ObjectId(ser.serviceId)
        bmMongoDriver.queryObject(DBObject("_id" -> _id), "services") { x =>
            x += "service_images" -> images
            bmMongoDriver.updateObject(x, "services", "_id")
            null
        }
    }
}

case class BMService(val d : Map[String, JsValue]) {
    def serviceId = d.get("_id").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
    def category = d.get("category").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
    def title = d.get("title").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
//    def serviceTag = d.get("service_tags").map (x => x.toString).getOrElse(throw new Exception)
//    def operTags = d.get("operation").map (x => x.toString).getOrElse(throw new Exception)
}