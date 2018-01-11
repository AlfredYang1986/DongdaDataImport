package entry

import data.bmAlDataModule
import data.image.bmAlImageModule
import data.image.bmImageTypes._
import mongo.brand.logic.bmAlBrandImport
import mongo.location.logic.bmAlLocationImport
import mongo.refactor.logic.bmAlBindImport
import mongo.service.logic.bmAlServiceImport
import mongo.users.logic.bmAlUserImport

object bmAlETMEntry extends App {
    println("Crazy demand！")
    bmAlDataModule.readOnlyXLS("test/test3.xls", 2, 1) { m =>
        val user = bmAlUserImport.insertUser(m).get

        val brand = bmAlBrandImport.insertBrand(m).get

        val location = bmAlLocationImport.insertLocation(m).get
        bmAlLocationImport.pushLocationImages(
            bmAlImageModule.refactorImages(location_image, brand, location, m.get("场地图片").get),
            location)

        val service = bmAlServiceImport.insertService(m).get
        bmAlServiceImport.pushServiceImages(
            bmAlImageModule.refactorImages(service_image, brand, location, m.get("服务图片").get),
            service)

        bmAlBindImport.bindUser2Brand(user, brand)
        bmAlBindImport.bindLocation2Brand(location, brand)
        bmAlBindImport.bindService2Location(service, location)
    }
}
