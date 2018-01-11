package mongo.users

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

trait bmUserData {
    implicit val m2d : Map[String, String] => DBObject = { map =>
        val _id = ObjectId.get()
        DBObject(
            "_id" -> _id,
            "screen_name" -> map.get("老师昵称").getOrElse(""),
            "screen_photo" -> "",
            "date" -> new Date().getTime
        )
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "_id" -> toJson(obj.getAs[ObjectId]("_id").get.toString),
            "screen_name" -> toJson(obj.getAs[String]("screen_name").get),
            "screen_photo" -> toJson(obj.getAs[String]("screen_photo").get),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }
}