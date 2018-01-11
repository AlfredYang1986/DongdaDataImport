package mongo.refactor.logic

import com.mongodb.casbah.Imports._
import mongo.bmMongoDriver
import mongo.brand.logic.BMBrand
import mongo.location.logic.BMLocation
import mongo.service.logic.BMService
import mongo.users.logic.BMUser

object bmAlBindImport {

    def bindLocation2Brand(loc : BMLocation, brand : BMBrand) = {
        val b_id = brand.brandId
        val l_id = new ObjectId(loc.locationId)

        bmMongoDriver.queryObject(DBObject("_id" -> l_id), "locations") { x =>
            x += "brand" -> b_id
            bmMongoDriver.updateObject(x, "locations", "_id")
            null
        }
    }

    def bindUser2Brand(user : BMUser, brand : BMBrand) = {
        val u_id = new ObjectId(user.userId)
        val b_id = brand.brandId

        bmMongoDriver.queryObject(DBObject("_id" -> u_id), "users") { x =>
            x += "brand" -> b_id
            bmMongoDriver.updateObject(x, "users", "_id")
            null
        }
    }

    def bindService2Location(ser : BMService, loc : BMLocation) = {
        val l_id = loc.locationId
        val s_id = new ObjectId(ser.serviceId)

        bmMongoDriver.queryObject(DBObject("_id" -> s_id), "services") { x =>
            x += "location" -> l_id
            bmMongoDriver.updateObject(x, "services", "_id")
            null
        }
    }
}
