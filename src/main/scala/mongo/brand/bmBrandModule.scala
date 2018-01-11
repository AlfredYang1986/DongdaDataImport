package mongo.brand

import com.mongodb.casbah.Imports._
import com.pharbers.mongodbDriver.MongoDB.MongoDBImpl

/**
  * Created by jeorch on 17-12-16.
  */
trait bmBrandModule {

    implicit val db_driver : MongoDBImpl

    object brand_traits extends bmBrandData

    def mutiInsertBrand(data: List[Map[String, String]]) = {
        import brand_traits.m2d
        var data_tmp : List[Map[String, String]] = Nil
        data.groupBy(_.get("brand_id").get) map { x =>
            data_tmp = (x._2.head + ("brand_id" -> x._1)) :: data_tmp
        }
        data_tmp.foreach{ line =>
            val o: DBObject = line
            db_driver.insertObject(o, "brands", "_id")
        }
    }

}
