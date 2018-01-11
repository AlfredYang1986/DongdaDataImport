package mongo.service

import com.mongodb.DBObject
import com.pharbers.mongodbDriver.MongoDB.MongoDBImpl

/**
  * Created by jeorch on 17-12-18.
  */
trait bmServiceModule {

    implicit val db_driver : MongoDBImpl

    object serve_traits extends bmServiceData

    def mutiInsertService(data: List[Map[String, String]]) = {
        import serve_traits.m2d
        data.foreach{ line =>
            val o: DBObject = line
            db_driver.insertObject(o, "services", "_id")
        }
    }

}
