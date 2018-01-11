package mongo.service

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

/**
  * Created by jeorch on 17-12-18.
  */
trait bmServiceData {
    implicit val m2d : Map[String, String] => DBObject = { map =>

        val cat = if (map.get("service_type").getOrElse("") == "看顾") "看顾"
                  else "课程"

        val tags = map.get("服务Tags").getOrElse("").split(';')
        val oper = map.get("运营之力").getOrElse("").split(';')

        val age_boundary_low_limit = map.get("最小年龄").map(x => if (x == "") 2.0 else x.toDouble.asInstanceOf[Number]).getOrElse((2.0).asInstanceOf[Number])
        val age_boundary_up_limit = map.get("最大年龄").map(x => if (x == "") 12.0 else x.toDouble.asInstanceOf[Number]).getOrElse((12.0).asInstanceOf[Number])

        val _id = ObjectId.get()
        DBObject("_id" -> _id,
            "category" -> cat,
            "service_type" -> map.get("service_type").getOrElse(""),
            "service_tags" -> tags,
            "operation" -> oper,
            "service_leaf" -> map.get("服务Leaf").getOrElse(""),
            "age_boundary" -> DBObject(
                "low_limit" -> age_boundary_low_limit,
                "up_limit" -> age_boundary_up_limit
            ),
            "class_max_stu" -> map.get("满班人数").map(x => if (x == "") -1 else x.toInt.asInstanceOf[Number]).getOrElse((-1).asInstanceOf[Number]),
            "teacher_num" -> map.get("老师数量").map(x => if (x == "") -1 else x.toInt.asInstanceOf[Number]).getOrElse((-1).asInstanceOf[Number]),
            "punchline" -> map.get("一句话吸睛").getOrElse(""),
            "description" -> map.get("描述").getOrElse(""),
//            "service_images" -> map.get("服务图片").getOrElse("")
            "date" -> new Date().getTime
        )
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "_id" -> toJson(obj.getAs[ObjectId]("_id").get.toString),
            "category" -> toJson(obj.getAs[String]("category").get),
            "service_tags" -> toJson(obj.getAs[List[String]]("service_tags").get),
            "operation" -> toJson(obj.getAs[List[String]]("operation").get),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }
}