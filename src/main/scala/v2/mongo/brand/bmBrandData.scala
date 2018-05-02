package v2.mongo.brand

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

trait bmBrandData {
    implicit val m2d : Map[String, String] => DBObject = { map =>
        val _id = ObjectId.get()
        DBObject("_id" -> _id,
//            "brand_name" -> map.get("品牌名").getOrElse(""),
            "brand_name" -> map.get("品牌全称").getOrElse(""),
            "brand_nickname" -> map.get("品牌简称").getOrElse(""),
//            "brand_tag" -> map.get("品牌标识").getOrElse(""),
            "brand_tag" -> map.get("LOGO").getOrElse(""),
            "brand_highlight" -> map.get("品牌亮点").getOrElse("").split("\n"),
            "about_brand" -> map.get("关于").getOrElse(""),
            "date" -> new Date().getTime
        )
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "_id" -> toJson(obj.getAs[ObjectId]("_id").get.toString),
            "brand_name" -> toJson(obj.getAs[String]("brand_name").get),
//            "brand_tag" -> toJson(obj.getAs[String]("brand_tag").get),
//            "about_brand" -> toJson(obj.getAs[String]("about_brand").get),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }
}
