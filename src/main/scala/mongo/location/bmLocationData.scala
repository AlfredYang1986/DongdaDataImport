package mongo.location

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

/**
  * Created by jeorch on 17-12-18.
  */
trait bmLocationData {
    implicit val m2d : Map[String, String] => DBObject = { map =>
        val ftmp = map.get("场地友好性").getOrElse("").split(";")

        val _id = ObjectId.get()
        DBObject(
            "_id" -> _id,
            "address" -> map.get("场地位置").getOrElse(""),
            "friendliness" -> ftmp, // map.get("场地友好性").getOrElse(""),
            "date" -> new Date().getTime
//            "location_images" -> map.get("场地图片").getOrElse("")
        )
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "_id" -> toJson(obj.getAs[ObjectId]("_id").get.toString),
            "address" -> toJson(obj.getAs[String]("address").get),
            "friendliness" -> toJson(obj.getAs[List[String]]("friendliness").get),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }
}
