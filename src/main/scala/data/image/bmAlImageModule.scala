package data.image

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.UUID

import com.mongodb.casbah.Imports._
import mongo.brand.logic.BMBrand
import mongo.location.logic.BMLocation

sealed case class ImageTypeDefines(t : Int)

object bmImageTypes {
    object location_image extends ImageTypeDefines(0)
    object service_image extends ImageTypeDefines(1)
}

object bmAlImageModule {

    /**
      * @张弛 你来把这个变成配置文件把
      */
    lazy val ori = "resources/images/"
    lazy val dst = "images/"

    def refactorImages(itd : ImageTypeDefines, brand : BMBrand, location : BMLocation, des : String) : DBObject = {
        val mid = if (itd.t == 0) "场地图片/"
                  else "服务图片/"
        val ori_path = ori + brand.brandName + "/" + mid + des

        val ori_dir = new File(ori_path)
        val lb = MongoDBList.newBuilder
        if (ori_dir.exists()) {
            val images =
            ori_dir.listFiles().filter(p => !p.getName.startsWith(".") && isImage(p.getName)).map { f =>
                val (tag, uuid) = copyFiles(f.getName, f.getPath)
                val ft = tag.replace("-", ".").split('.').head
                (ft -> uuid)
            }

            images.groupBy(_._1).map { iter =>
                val builder = MongoDBObject.newBuilder
                builder += "tag" -> iter._1
                builder += "image" -> iter._2.map (in => in._2)
                builder.result
            }.foreach ( x => lb += x)
//            lb += result
        }
        lb.result
    }

    def copyFiles(name : String, source : String) : (String, String) = {
        val image_uuid = UUID.randomUUID().toString
        val tar = new File(dst, image_uuid)
        val is = new FileInputStream(source)

        val os = new FileOutputStream(tar)
        val buf = new Array[Byte](1024)
        var len = is.read(buf)
        while (len != -1) {
            os.write(buf, 0, len)
            len = is.read(buf)
        }
        is.close()
        os.close()

        (name, image_uuid)
    }

    def isImage(name : String) = name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jepg")
}
