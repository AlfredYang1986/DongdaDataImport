package v2.mongo.service

import java.util.Date

import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson

trait bmServiceData {
    implicit val m2d : Map[String, String] => DBObject = { map =>

        val cat = if (map.get("service_type").getOrElse("") == "看顾") "看顾"
        else "课程"

//        val st2 = map.get("分类修正").getOrElse("")
        val st2 = map.get("服务类型").getOrElse("")
        val st = if (st2.isEmpty) map.get("service_type").getOrElse("")
        else st2

        val title = map.get("标题").getOrElse("")

//        val tags = map.get("服务Tags").getOrElse("").split(';')
//        val oper = map.get("运营之力").getOrElse("").split(';')
        val concept = map.get("教学理念").getOrElse("").replace("；", "").split("\n")
        val method = map.get("教学方式").getOrElse("").replace("；", "").split("\n")
        val language = map.get("授课语言").getOrElse("")

        val age_boundary = map.get("适应年龄").get.split("-")
        val age_boundary_low_limit = (age_boundary.head.toDouble * 10).toInt
        val age_boundary_up_limit = (age_boundary.tail.head.toDouble * 10).toInt

//        val age_boundary_low_limit = map.get("适应年龄").map(x => if (x == "") 2.0 else x.toDouble.asInstanceOf[Number]).getOrElse((2.0).asInstanceOf[Number])
//        val age_boundary_up_limit = map.get("最大年龄").map(x => if (x == "") 12.0 else x.toDouble.asInstanceOf[Number]).getOrElse((12.0).asInstanceOf[Number])

        val _id = ObjectId.get()
        DBObject("_id" -> _id,
            "title" -> title,
            "category" -> cat,
            "service_type" -> st, //map.get("service_type").getOrElse(""),
//            "service_tags" -> tags,
//            "operation" -> oper,
            "service_leaf" -> map.get("服务Leaf").getOrElse(""),
            "age_boundary" -> DBObject(
                "low_limit" -> age_boundary_low_limit,
                "up_limit" -> age_boundary_up_limit
            ),
//            "class_max_stu" -> map.get("满班人数").map(x => if (x == "") -1 else x.toInt.asInstanceOf[Number]).getOrElse((-1).asInstanceOf[Number]),
//            "teacher_num" -> map.get("老师数量").map(x => if (x == "") -1 else x.toInt.asInstanceOf[Number]).getOrElse((-1).asInstanceOf[Number]),
//            "punchline" -> map.get("一句话吸睛").getOrElse(""),
            "punchline" -> map.get("一句话").getOrElse(""),
//            "description" -> map.get("描述").getOrElse(""),
            "description" -> map.get("内容安排").getOrElse(""),
            "objective" -> map.get("目标").getOrElse(""),
            "concept" -> concept,
            "method" -> method,
            "language" -> language,
//            "album" -> map.get("首页专题").getOrElse(""),
//            "scores" -> - (map.get("首页分类").getOrElse("0").toInt),
            //            "service_images" -> map.get("服务图片").getOrElse("")
            "date" -> new Date().getTime
        )
    }

    implicit val d2m : DBObject => Map[String, JsValue] = { obj =>
        Map(
            "_id" -> toJson(obj.getAs[ObjectId]("_id").get.toString),
            "title" -> toJson(obj.getAs[String]("title").get),
            "category" -> toJson(obj.getAs[String]("category").get),
//            "service_tags" -> toJson(obj.getAs[List[String]]("service_tags").get),
//            "operation" -> toJson(obj.getAs[List[String]]("operation").get),
            "date" -> toJson(obj.getAs[Number]("date").get.longValue)
        )
    }
}
