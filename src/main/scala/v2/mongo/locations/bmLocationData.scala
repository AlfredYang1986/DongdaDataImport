package v2.mongo.locations

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

trait bmLocationData {
    implicit val m2d : Map[String, String] => DBObject = { map =>
//        val ftmp = map.get("场地友好性").getOrElse("").split(";")
        val ftmp = map.get("场地友好性").getOrElse("").split("\n")

        val _id = ObjectId.get()
        DBObject(
            "_id" -> _id,
            "address" -> map.get("地理位置").getOrElse(""),
            "friendliness" -> ftmp, // map.get("场地友好性").getOrElse(""),
            "date" -> new Date().getTime
            //            "location_images" -> map.get("场地图片").getOrElse("")
        )
    }

    implicit val m2d2 : Map[String, Any] => DBObject = { map =>
//        val ftmp = map.get("场地友好性").getOrElse("").toString.split(";")
        val ftmp = map.get("场地友好性").getOrElse("").toString.split("\n")
        val pin = map.get("pin").map (x => x).getOrElse("")

        val _id = ObjectId.get()
        DBObject(
            "_id" -> _id,
            "address" -> map.get("地理位置").getOrElse(""),
            "friendliness" -> ftmp, // map.get("场地友好性").getOrElse(""),
            "pin" -> pin,
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
