package v1.mongo.refactor

import com.mongodb.casbah.Imports._
import com.pharbers.mongodbDriver.MongoDB.MongoDBImpl

/**
  * Created by jeorch on 17-12-19.
  */
trait bmRefactorModule {
    implicit val db_driver : MongoDBImpl

    object refactor_traits extends bmRefactorLocData with bmRefactorSerData

    def mutiReplaceLocImg(data: Map[String, List[Map[String, String]]]) = {

        import refactor_traits.d2m_loc
        import refactor_traits.m2d_loc

        db_driver.queryMultipleObject(DBObject(), "locations").map { one_loc =>
            val location_id = one_loc.get("_id").get
            val loc_id = location_id.asOpt[String].get
            val lst = data.get(loc_id).map (x => x).getOrElse {
//                println(loc_id)
//                println(one_loc)
                List[Map[String, String]]()
            }
            val o : DBObject = one_loc
            o += "location_images" -> lst.map { m =>
                val detail = MongoDBObject.newBuilder
                m.keys.foreach(x => detail += x -> m.get(x).get)
                detail.result
            }
            val pin = MongoDBObject.newBuilder
            pin += "type" -> "Point" += "coordinates" -> List(116.311996459961, 39.9630012512207)
            o += "pin" -> pin.result
            db_driver.updateObject(o, "locations", "_id")
        }

    }

    def mutiReplaceSerImg(data: Map[String, List[Map[String, String]]]) = {

        import refactor_traits.d2m_ser
        import refactor_traits.m2d_ser

        db_driver.queryMultipleObject(DBObject(), "services").map { one_ser =>
            val service_id = one_ser.get("_id").get
            val ser_id = service_id.asOpt[String].get

            val lst = data.get(ser_id).map (x => x).getOrElse {
//                println(loc_id)
//                println(one_loc)
                List[Map[String, String]]()
            }

            val o : DBObject = one_ser
            o += "service_images" -> lst.map { m =>
                val detail = MongoDBObject.newBuilder
                m.keys.foreach(x => detail += x -> m.get(x).get)
                detail.result
            }
            db_driver.updateObject(o, "services", "_id")
        }

    }
}
