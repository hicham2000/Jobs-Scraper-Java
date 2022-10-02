import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;





public class Main {
    private static String[] columns = { "name", "descreption", "Pretime",
            "Cooktime","serves" };
    private static List<data> datas = new ArrayList<data>();
    public static void main(String[] args) throws IOException {
      // FileWriter recipesFile = new FileWriter("recipes.txt", true);
     //   recipesFile.write("name,descreption,Pre time,cook time,serves\n");

        Document doc = Jsoup.connect("https://foodnetwork.co.uk/").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select(".card-link");
        int x=1;
        for (Element hicham : newsHeadlines) {
            Elements Pretime = hicham.select(".card-content .recipe-card-content .recipe-card-item:nth-child(1) .recipe-card-text");


            if (Pretime.text().isEmpty() == true){
                continue;
            }
            else {
                Elements Cooktime = hicham.select(".card-content .recipe-card-content .recipe-card-item:nth-child(2) .recipe-card-text");
                Elements serves = hicham.select(".card-content .recipe-card-content .recipe-card-item:nth-child(3) .recipe-card-text");
                Elements name = hicham.select(".card-content h3");
                Elements descreption = hicham.select(".card-content .recipe-card__description");

            //    recipesFile.write(name.text()+","+descreption.text()+","+Pretime.text()+","+Cooktime.text()+","+serves.text()+"\n");
                datas.add(new data(name.text(),descreption.text(),Pretime.text(),Cooktime.text(),serves.text()) );
                System.out.println("item "+ x+ " succefully collected!!!!!");
                x++;
            }


        }
       // recipesFile.close();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("datas");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);


        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }


        int rowNum = 1;

        for (data data : datas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.name);
            row.createCell(1).setCellValue(data.descreption);
            row.createCell(2).setCellValue(data.Pretime);
            row.createCell(3).setCellValue(data.Cooktime);
            row.createCell(4).setCellValue(data.serves);
        }



        FileOutputStream fileOut = new FileOutputStream("datas.xlsx");
        workbook.write(fileOut);
        fileOut.close();




    }
}