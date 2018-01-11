package entry

import data.bmDataModule
import mongo.bmMongoLogic

/**
  * Created by jeorch on 17-12-16.
  */
object bmETMEntry extends App {
    println("Crazy demand！")
    val excel_path = "test/test3.xls"
    val cache_data = bmDataModule.readOnlyXLS(excel_path, 2, 1)   //从第三行、第一列开始读
    println(s"DataLength = ${cache_data.length}")

    println("Start Insert Brands")
    bmMongoLogic.mutiInsertBrand(cache_data)
    println("Start Insert Locations")
    bmMongoLogic.mutiInsertLocation(cache_data)
    println("Start Insert Services")
    bmMongoLogic.mutiInsertService(cache_data)

    bmMongoLogic.bindBrandLocation(cache_data)
    bmMongoLogic.bindBrandService(cache_data)
    bmMongoLogic.bindServiceLocation(cache_data)

    val refactor_img_data = bmDataModule.refactorMongoImages(cache_data)
    Thread.sleep(1000)

    println("Start Replace LocImg")
    bmMongoLogic.mutiReplaceLocImg(refactor_img_data)
    println("Start Replace SerImg")
    bmMongoLogic.mutiReplaceSerImg(refactor_img_data)
}
