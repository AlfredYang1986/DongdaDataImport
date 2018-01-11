package mongo.refactor

import com.mongodb.casbah.Imports.{DBObject, ObjectId}
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

/**
  * Created by jeorch on 17-12-20.
  */
trait bmRefactorSerData {
    implicit val m2d_ser : Map[String, JsValue] => DBObject = { js_map =>
        val _id = new ObjectId(js_map.get("_id").get.asOpt[String].getOrElse(new ObjectId().toString))
        DBObject("_id" -> _id,
            "category" -> js_map.get("category").get.asOpt[String].getOrElse(""),
            "service_type" -> js_map.get("service_type").get.asOpt[String].getOrElse(""),
            "service_tags" -> js_map.get("service_tags").get.asOpt[String].map(x => if (x == "")List.empty else x.split("；\\n").toList).getOrElse(List.empty),
            "operation" -> js_map.get("operation").get.asOpt[String].map(x => if (x == "")List.empty else x.split("；\\n").toList).getOrElse(List.empty),
            "service_leaf" -> js_map.get("service_leaf").get.asOpt[String].getOrElse(""),
            "min_age" -> js_map.get("min_age").get.asOpt[Double].getOrElse(2.0).asInstanceOf[Number],
            "max_age" -> js_map.get("max_age").get.asOpt[Double].getOrElse(12.0).asInstanceOf[Number],
            "class_max_stu" -> js_map.get("class_max_stu").get.asOpt[Int].getOrElse(-1).asInstanceOf[Number],
            "teacher_num" -> js_map.get("teacher_num").get.asOpt[Int].getOrElse(-1).asInstanceOf[Number],
            "punchline" -> js_map.get("punchline").get.asOpt[String].getOrElse(""),
            "description" -> js_map.get("description").get.asOpt[String].getOrElse(""),
            "service_images" -> js_map.get("service_images").get.asOpt[String].getOrElse("")
        )
    }

    implicit val d2m_ser : DBObject => Map[String, JsValue] = { obj =>

        Map(
            "_id" -> toJson(obj.get("_id").asInstanceOf[ObjectId].toString),
            "category" -> toJson(obj.get("category").asInstanceOf[String]),
            "service_type" -> toJson(obj.get("service_type").asInstanceOf[String]),
            "service_tags" -> toJson(obj.get("service_tags").asInstanceOf[String]),
            "operation" -> toJson(obj.get("operation").asInstanceOf[String]),
            "service_leaf" -> toJson(obj.get("service_leaf").asInstanceOf[String]),
            "min_age" -> toJson(obj.get("min_age").asInstanceOf[Double]),
            "max_age" -> toJson(obj.get("max_age").asInstanceOf[Double]),
            "class_max_stu" -> toJson(obj.get("class_max_stu").asInstanceOf[Int]),
            "teacher_num" -> toJson(obj.get("teacher_num").asInstanceOf[Int]),
            "punchline" -> toJson(obj.get("punchline").asInstanceOf[String]),
            "description" -> toJson(obj.get("description").asInstanceOf[String]),
            "service_images" -> toJson(obj.get("service_images").asInstanceOf[String])
        )
    }

}
