package mongo

import mongo.bind.bmBindModule
import mongo.brand.bmBrandModule
import mongo.location.bmLocationModule
import mongo.refactor.bmRefactorModule
import mongo.service.bmServiceModule

/**
  * Created by jeorch on 17-12-18.
  */
object bmMongoLogic extends bmBrandModule with bmLocationModule with bmServiceModule with bmBindModule with bmRefactorModule {
    implicit val db_driver = bmMongoDriver
}
