package data.image

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

import com.pharbers.baseModules.PharbersInjectModule


/**
  * Created by jeorch on 17-12-19.
  */
trait bmImageModule extends PharbersInjectModule {

    override val id: String = "dongda-resources"
    override val configPath: String = "pharbers_config/dongda_resources.xml"
    override val md = "origin-path" :: "images-path" :: Nil

    def origin_path = config.mc.find(p => p._1 == "origin-path").get._2.toString
    def images_path = config.mc.find(p => p._1 == "images-path").get._2.toString

    def get_dest_dir = {
        val dest_dir = new File(images_path)
        if (!dest_dir.exists()) dest_dir.mkdir()
        dest_dir
    }

    /**
      * 编程的思想，不要乱用整体法，整体法使用的情况是处理流程完全一致不会用中间其它步骤的干扰
      * 例如Map Reduce
      * 当数据处理过程中有大量的特殊处理或者数据不能完成统一处理的时候，需要使用meta的编程思想
      * 一次只处理一个保证完整的最小，然后去复制这个最小的单元知道出结果
      * 同样参考的思想也是 Map Reduce
      */
    def refactorMongoImages(data: List[Map[String, String]]): Map[String, List[Map[String, String]]] = {

        var result : Map[String, List[Map[String, String]]] = Map().empty
        val dest_dir = new File(images_path)
        if (!dest_dir.exists()) dest_dir.mkdir()
        var last_location_id : String = ""
        var last_loc_img_lst : List[Map[String, String]] = Nil
        data.map{ x =>
            val service_id = x.get("service_id").getOrElse(throw new Exception("no service_id found"))
            val location_id = x.get("location_id").getOrElse(throw new Exception("no location_id found"))
            val brand = x.get("品牌名").getOrElse(throw new Exception("no brand found"))
            val service_images = x.get("服务图片").getOrElse(throw new Exception("no brand found"))

            var ser_img_lst : List[Map[String, String]] = Nil
            var loc_img_lst : List[Map[String, String]] = Nil

            val origin_ser_images = origin_path + brand + "/服务图片"
            val origin_loc_images = origin_path + brand + "/场地图片"
            val osi_dir = new File(origin_ser_images)
            val oli_dir = new File(origin_loc_images)
            if (osi_dir.exists()) {
                val origin_service_images_leaf = origin_ser_images + "/" + service_images
                val osil_dir = new File(origin_service_images_leaf)
                if (osil_dir.exists()) {
                    ser_img_lst = generateImagesList(osil_dir, dest_dir)
                } else {
                    println(osil_dir)
                }

            }
            if (oli_dir.exists()) {
                if (last_location_id != location_id) loc_img_lst = generateImagesList(oli_dir, dest_dir)
                else loc_img_lst = last_loc_img_lst
            }
            last_location_id = location_id
            last_loc_img_lst = loc_img_lst
            result += (service_id -> ser_img_lst, location_id -> loc_img_lst)
        }
        result
    }

    def generateImagesList(source: File, target: File): List[Map[String, String]] = {
        try {
            if (source.isDirectory) {
                source.listFiles.toList.map(s => generateImagesList(s, target))flatMap(x => x)
            } else {
                val image_uuid = UUID.randomUUID().toString
                val tar = new File(target, image_uuid)
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
                Map("image" -> image_uuid,
                    "tag" -> source.getName.split(46.toChar)(0)) :: Nil
            }
        } catch {
            case ex: IOException => ex.printStackTrace(); Nil
        }

    }

    def generateImagesList(source: String, target: String): List[Map[String, String]] = {
        val sour = new File(source)
        val tar = new File(target)
        try {
            generateImagesList(sour, tar)
        } catch {
            case e: IOException => e.printStackTrace(); Nil
        }
    }
}
