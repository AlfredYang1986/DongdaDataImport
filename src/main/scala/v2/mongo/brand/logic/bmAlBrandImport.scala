package v2.mongo.brand.logic

import com.mongodb.casbah.Imports._
import play.api.libs.json.JsValue
import v1.mongo.bmMongoDriver
import v2.mongo.brand.bmBrandData

object bmAlBrandImport extends bmBrandData {

    def insertBrand(map : Map[String, String]) : Option[BMBrand] = {
        if (isNewBrand(map))
            bmMongoDriver.insertObject(map, "brands", "_id")

        lastPushBrand
    }

    def lastPushBrand : Option[BMBrand] = {
        val result =
            bmMongoDriver.queryMultipleObject(DBObject(), "brands", "date", skip = 0, take = 1)

        if (result.isEmpty) None
        else Some(BMBrand(result.head))
    }

    def isNewBrand(map : Map[String, String]) : Boolean = !map.get("品牌全称").get.isEmpty
}

case class BMBrand(val d : Map[String, JsValue]) {
    def brandId = d.get("_id").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
    def brandName = d.get("brand_name").map (x => x.asOpt[String].get).getOrElse(throw new Exception)
//    def brandTag = d.get("brand_tag").map (x => x.toString).getOrElse(throw new Exception)
//    def aboutBrand = d.get("about_brand").map (x => x.toString).getOrElse(throw new Exception)
}