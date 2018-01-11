package data

import data.excel.{bmAlExcelModule, bmExcelModule}
import data.image.bmImageModule

/**
  * Created by jeorch on 17-12-16.
  */
object bmDataModule extends bmExcelModule with bmImageModule

object bmAlDataModule extends bmAlExcelModule
