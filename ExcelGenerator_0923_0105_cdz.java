// 代码生成时间: 2025-09-23 01:05:46
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import play.Logger;
import play.libs.Files;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

// 用于生成Excel表格的类
public class ExcelGenerator {

    // 生成Excel表格
    public static XSSFWorkbook generateExcel(List<List<String>> dataList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Generated Data");
        int rowNum = 0;
        
        for (List<String> dataRow : dataList) {
            Row row = sheet.createRow(rowNum++);
            int cellNum = 0;
            for (String data : dataRow) {
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(data);
            }
        }
        
        return workbook;
    }

    // 上传文件并生成Excel
    public static Result uploadAndGenerateExcel(MultipartFormData<File> formData) {
        try {
            // 从formData中获取上传的文件
            FilePart<File> filePart = formData.getFile("file");
            if (filePart == null || filePart.getFile() == null) {
                return badRequest("Invalid file upload");
            }

            // 读取文件内容并生成Excel数据
            // 这里我们假设文件内容是逗号分隔的字符串，每一行是一个数据行
            List<List<String>> dataList = new ArrayList<>();
            try (FileInputStream fis = new FileInputStream(filePart.getFile())) {
                String line;
                while ((line = fis.readUTF()) != null) {
                    String[] dataRow = line.split(",");
                    dataList.add(new ArrayList<>(java.util.Arrays.asList(dataRow)));
                }
            }

            // 生成Excel文件
            XSSFWorkbook workbook = generateExcel(dataList);
            File excelFile = Files.createTempFile("excel_", ".xlsx");
            try (FileOutputStream out = new FileOutputStream(excelFile)) {
                workbook.write(out);
            }

            // 返回生成的Excel文件
            return ok(excelFile);
        } catch (IOException e) {
            Logger.error("Error generating Excel file", e);
            return internalServerError("Error generating Excel file");
        }
    }
}
