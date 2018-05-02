package v1.mongo.location

import com.mongodb.casbah.Imports.DBObject
import com.pharbers.mongodbDriver.MongoDB.MongoDBImpl

/**
  * Created by jeorch on 17-12-18.
  */
trait bmLocationModule {
    implicit val db_driver : MongoDBImpl

    object loc_traits extends bmLocationData

    def mutiInsertLocation(data: List[Map[String, String]]) = {
        import loc_traits.m2d
        var data_tmp : List[Map[String, String]] = Nil
        data.groupBy(_.get("location_id").get) map { x =>
            data_tmp = (x._2.head + ("location_id" -> x._1)) :: data_tmp
        }
        data_tmp.foreach{ line =>
            val o: DBObject = line
            db_driver.insertObject(o, "locations", "_id")
        }

    }

}
