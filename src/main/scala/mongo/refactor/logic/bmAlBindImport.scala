package mongo.refactor.logic

import com.mongodb.casbah.Imports._
import mongo.bmMongoDriver
import mongo.brand.logic.BMBrand
import mongo.location.logic.BMLocation
import mongo.service.logic.BMService
import mongo.users.logic.BMUser
import org.bson.types
import org.bson.types.ObjectId

object bmAlBindImport {

    def bindLocation2Brand(loc : BMLocation, brand : BMBrand) = {
//        val b_id = brand.brandId
//        val l_id = new ObjectId(loc.locationId)
//
//        bmMongoDriver.queryObject(DBObject("_id" -> l_id), "locations") { x =>
//            x += "brand" -> b_id
//            bmMongoDriver.updateObject(x, "locations", "_id")
//            null
//        }
        val _id = ObjectId.get()
        val b_id = new ObjectId(brand.brandId)
        val l_id = new ObjectId(loc.locationId)

        val t =
        DBObject(
            "_id" -> _id,
            "brand_id" -> b_id,
            "location_id" -> l_id
        )

        bmMongoDriver.insertObject(t, "brand_location", "_id")
    }

    def bindUser2Brand(user : BMUser, brand : BMBrand) = {
//        val u_id = new ObjectId(user.userId)
//        val b_id = brand.brandId
//
//        bmMongoDriver.queryObject(DBObject("_id" -> u_id), "users") { x =>
//            x += "brand" -> b_id
//            bmMongoDriver.updateObject(x, "users", "_id")
//            null
//        }

        val _id = ObjectId.get()
        val b_id = new ObjectId(brand.brandId)
        val u_id = new ObjectId(user.userId)

        val t =
            DBObject(
                "_id" -> _id,
                "brand_id" -> b_id,
                "user_id" -> u_id
            )

        bmMongoDriver.insertObject(t, "brand_user", "_id")
    }

    def bindService2Location(ser : BMService, loc : BMLocation) = {
//        val l_id = loc.locationId
//        val s_id = new ObjectId(ser.serviceId)
//
//        bmMongoDriver.queryObject(DBObject("_id" -> s_id), "services") { x =>
//            x += "location" -> l_id
//            bmMongoDriver.updateObject(x, "services", "_id")
//            null
//        }

        val _id = ObjectId.get()
        val s_id = new ObjectId(ser.serviceId)
        val l_id = new ObjectId(loc.locationId)

        val t =
            DBObject(
                "_id" -> _id,
                "service_id" -> s_id,
                "location_id" -> l_id
            )

        bmMongoDriver.insertObject(t, "service_location", "_id")
    }

    def bindBrand2Service(brand : BMBrand, ser : BMService) = {
        val _id = ObjectId.get()
        val b_id = new ObjectId(brand.brandId)
        val s_id = new ObjectId(ser.serviceId)

        val t =
            DBObject(
                "_id" -> _id,
                "brand_id" -> b_id,
                "service_id" -> s_id
            )

        bmMongoDriver.insertObject(t, "brand_service", "_id")
    }
}
