package v1.mongo.bind

import com.mongodb.casbah.Imports.{DBObject, ObjectId}

/**
  * Created by jeorch on 17-12-18.
  */
trait bmBindData {
    implicit val bl : Map[String, String] => DBObject = { map =>
        val _id = new ObjectId()
        val brand_id = new ObjectId(map.get("brand_id").getOrElse(""))
        val location_id = new ObjectId(map.get("location_id").getOrElse(""))
        DBObject("_id" -> _id,
            "brand_id" -> brand_id,
            "location_id" -> location_id
        )
    }

    implicit val bs : Map[String, String] => DBObject = { map =>
        val _id = new ObjectId()
        val brand_id = new ObjectId(map.get("brand_id").getOrElse(""))
        val service_id = new ObjectId(map.get("service_id").getOrElse(""))
        DBObject("_id" -> _id,
            "brand_id" -> brand_id,
            "service_id" -> service_id
        )
    }

    implicit val sl : Map[String, String] => DBObject = { map =>
        val _id = new ObjectId()
        val service_id = new ObjectId(map.get("service_id").getOrElse(""))
        val location_id = new ObjectId(map.get("location_id").getOrElse(""))
        DBObject("_id" -> _id,
            "service_id" -> service_id,
            "location_id" -> location_id
        )
    }
}
