package v2.entry

import data.bmAlDataModule
import v2.mongo.bind.logic.bmAlBindImport
import v2.mongo.brand.logic.bmAlBrandImport
import v2.mongo.locations.logic.bmAlLocationImport
import v2.mongo.service.logic.bmAlServiceImport
import v2.mongo.users.logic.bmAlUserImport

object bmAlETMEntry extends App {
    println("咚哒数据导入第二个版本，对应2018年6月1日活动")
    bmAlDataModule.readOnlyXLS("test/test0601.xls", 1, 1) { m =>
        val user = bmAlUserImport.insertUser(m).get

        val (location_opt, is_new) = bmAlLocationImport.insertLocation(m, Nil)
        val location = location_opt.get

        val service = bmAlServiceImport.insertService(m).get
        val brand = bmAlBrandImport.insertBrand(m).get

        bmAlBindImport.bindUser2Brand(user, brand)
        bmAlBindImport.bindLocation2Brand(location, brand)
        bmAlBindImport.bindService2Location(service, location)
        bmAlBindImport.bindBrand2Service(brand, service)
    }

//    val brand = bmAlBrandImport.insertBrand(m).get
//
//    val (location_opt, is_new) = bmAlLocationImport.insertLocation(m, pins)
//    val location = location_opt.get
//    if (is_new) {
//        bmAlLocationImport.pushLocationImages(
//            bmAlImageModule.refactorImages(location_image, brand, location, m.get("场地图片").get),
//            location)
//    }
//
//    val service = bmAlServiceImport.insertService(m).get
//    bmAlServiceImport.pushServiceImages(
//        bmAlImageModule.refactorImages(service_image, brand, location, m.get("服务图片").get),
//        service)
}
