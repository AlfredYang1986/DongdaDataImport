package v1.mongo

import com.pharbers.mongodbDriver.MongoDB.{MongoDBImpl, _data_connection}

/**
  * Created by jeorch on 17-12-16.
  */
object bmMongoDriver extends MongoDBImpl {
    implicit val dc = _data_connection
}
