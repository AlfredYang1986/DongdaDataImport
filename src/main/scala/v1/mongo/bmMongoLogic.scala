package v1.mongo

import v1.mongo.bind.bmBindModule
import v1.mongo.brand.bmBrandModule
import v1.mongo.location.bmLocationModule
import v1.mongo.refactor.bmRefactorModule
import v1.mongo.service.bmServiceModule

/**
  * Created by jeorch on 17-12-18.
  */
object bmMongoLogic extends bmBrandModule with bmLocationModule with bmServiceModule with bmBindModule with bmRefactorModule {
    implicit val db_driver = bmMongoDriver
}
