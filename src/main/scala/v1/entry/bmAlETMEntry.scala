package v1.entry

import data.bmAlDataModule
import data.image.bmAlImageModule
import data.image.bmImageTypes._
import v1.mongo.brand.logic.bmAlBrandImport
import v1.mongo.location.logic.bmAlLocationImport
import v1.mongo.pin.logic.bmAlPinImport
import v1.mongo.refactor.logic.bmAlBindImport
import v1.mongo.service.logic.bmAlServiceImport
import v1.mongo.users.logic.bmAlUserImport

object bmAlETMEntry extends App {
    println("Crazy demand！")
    val pins = bmAlPinImport.queryFromDataFile
    bmAlDataModule.readOnlyXLS("test/test5.xls", 2, 1) { m =>
        val user = bmAlUserImport.insertUser(m).get

        val brand = bmAlBrandImport.insertBrand(m).get

        val (location_opt, is_new) = bmAlLocationImport.insertLocation(m, pins)
        val location = location_opt.get
        if (is_new) {
            bmAlLocationImport.pushLocationImages(
                bmAlImageModule.refactorImages(location_image, brand, location, m.get("场地图片").get),
                location)
        }

        val service = bmAlServiceImport.insertService(m).get
        bmAlServiceImport.pushServiceImages(
            bmAlImageModule.refactorImages(service_image, brand, location, m.get("服务图片").get),
            service)

        bmAlBindImport.bindUser2Brand(user, brand)
        bmAlBindImport.bindLocation2Brand(location, brand)
        bmAlBindImport.bindService2Location(service, location)
        bmAlBindImport.bindBrand2Service(brand, service)
    }
}
