package v2.mongo.users.logic

import play.api.libs.json.JsValue
import v1.mongo.bmMongoDriver
import v2.mongo.users.bmUserData
import com.mongodb.casbah.Imports._

object bmAlUserImport extends bmUserData {
    def insertUser(map : Map[String, String]) : Option[BMUser] = {
        if (isNewUser(map))
            bmMongoDriver.insertObject(map, "users", "_id")

        lastPushUser
    }

    def lastPushUser : Option[BMUser] = {
        val result =
            bmMongoDriver.queryMultipleObject(DBObject(), "users", "date", skip = 0, take = 1)

        if (result.isEmpty) None
        else Some(BMUser(result.head))
    }

    def isNewUser(map : Map[String, String]) : Boolean = !map.get("品牌简称").get.isEmpty
}

case class BMUser(val d : Map[String, JsValue]) {
    def userId = d.get("_id").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
    def screen_name = d.get("screen_name").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
}