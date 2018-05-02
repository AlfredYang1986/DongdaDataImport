package v1.mongo.brand

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

/**
  * Created by jeorch on 17-12-16.
  */
trait bmBrandData {
    implicit val m2d : Map[String, String] => DBObject = { map =>
        val _id = ObjectId.get()
        DBObject("_id" -> _id,
            "brand_name" -> map.get("品牌名").getOrElse(""),
            "brand_tag" -> map.get("品牌标识").getOrElse(""),
            "about_brand" -> map.get("关于").getOrElse(""),
            "date" -> new Date().getTime
        )
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "_id" -> toJson(obj.getAs[ObjectId]("_id").get.toString),
            "brand_name" -> toJson(obj.getAs[String]("brand_name").get),
            "brand_tag" -> toJson(obj.getAs[String]("brand_tag").get),
            "about_brand" -> toJson(obj.getAs[String]("about_brand").get),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }
}
