package mongo.bind

import com.mongodb.DBObject
import com.pharbers.mongodbDriver.MongoDB.MongoDBImpl

/**
  * Created by jeorch on 17-12-18.
  */
trait bmBindModule {
    implicit val db_driver : MongoDBImpl

    object bind_traits extends bmBindData

    def bindBrandLocation(data: List[Map[String, String]]) = {
        import bind_traits.bl
        data.foreach{ line =>
            val o: DBObject = line
            db_driver.insertObject(o, "brand_location", "_id")
        }
    }
    def bindBrandService(data: List[Map[String, String]]) = {
        import bind_traits.bs
        data.foreach{ line =>
            val o: DBObject = line
            db_driver.insertObject(o, "brand_service", "_id")
        }
    }
    def bindServiceLocation(data: List[Map[String, String]]) = {
        import bind_traits.sl
        data.foreach{ line =>
            val o: DBObject = line
            db_driver.insertObject(o, "service_location", "_id")
        }
    }
}
