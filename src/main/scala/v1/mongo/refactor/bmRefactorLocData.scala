package v1.mongo.refactor

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
/**
  * Created by jeorch on 17-12-19.
  */
trait bmRefactorLocData {
    implicit val m2d_loc : Map[String, JsValue] => DBObject = { js_map =>
        val _id = new ObjectId(js_map.get("_id").get.asOpt[String].getOrElse(new ObjectId().toString))
        DBObject("_id" -> _id,
            "address" -> js_map.get("address").get.asOpt[String].getOrElse(""),
            "friendliness" -> js_map.get("friendliness").get.asOpt[String].map(x => if (x == "")List.empty else x.split("；\\n").toList).getOrElse(List.empty),
            "location_images" -> js_map.get("location_images").get.asOpt[String].getOrElse("")
        )
    }

    implicit val d2m_loc : DBObject => Map[String, JsValue] = { obj =>

        val di = obj.get("_id").toString
        val f_tmp = obj.getAs[String]("friendliness").map (x => List(x)).getOrElse {
            obj.get("friendliness").asInstanceOf[BasicDBList].toList.asInstanceOf[List[String]]
        }

        val lm_tmp = obj.getAs[String]("location_images").map (x => List(x)).getOrElse {
            obj.get("location_images").asInstanceOf[BasicDBList].toList.asInstanceOf[List[String]]
        }

        Map(
            "_id" -> toJson(di),
            "address" -> toJson(obj.get("address").asInstanceOf[String]),
            "friendliness" -> toJson(f_tmp),
            "location_images" -> toJson(lm_tmp)
        )
    }
}
