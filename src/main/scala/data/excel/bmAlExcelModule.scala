package data.excel

import java.io.FileInputStream
import jxl.{Workbook, WorkbookSettings}

trait bmAlExcelModule {
    def readOnlyXLS(xls_path: String, startRow: Int, startCol: Int)(f : Map[String, String] => Unit) : Unit = {
        val ins = new FileInputStream(xls_path)
        val setEncode = new WorkbookSettings()
        setEncode.setEncoding("UTF-8")
        val rwb = Workbook.getWorkbook(ins, setEncode)

        rwb.getSheets foreach { sheet =>
            val curSheetName = sheet.getName
            val rows = sheet.getRows // 行
            val cols = sheet.getColumns // 列

            val title = for (c <- startCol - 1 until cols)
                yield sheet.getCell(c, startRow - 1).getContents

            val cells =
                for (r <- startRow until rows; c <- startCol - 1 until cols)
                    yield sheet.getCell(c, r).getContents.replace("\n", "").replace("；", ";").replace("，", ",")

            cells.grouped(title.length) foreach (line => f(
                (title zip line.toList).toMap + ("service_type" -> curSheetName)
            ))
        }
    }
}
