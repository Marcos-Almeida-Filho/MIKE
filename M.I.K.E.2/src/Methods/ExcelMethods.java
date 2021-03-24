/*5430
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Marcos Filho
 */
public class ExcelMethods {

    String cellAdress;

    public void readExcel(String originalFile) throws IOException {
        // Retrieving the number of sheets in the Workbook
        try ( // Creating a Workbook from an Excel file (.xls or .xlsx)
                Workbook workbook = WorkbookFactory.create(new File(originalFile))) {
            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
            /*
            =============================================================
            Iterating over all the sheets in the workbook (Multiple ways)
            =============================================================
             */
            // 1. You can obtain a sheetIterator and iterate over it
            Iterator<org.apache.poi.ss.usermodel.Sheet> sheetIterator = workbook.sheetIterator();
            System.out.println("Retrieving Sheets using Iterator");
            while (sheetIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Sheet sheet = sheetIterator.next();
                System.out.println("=> " + sheet.getSheetName());
            }   // 2. Or you can use a for-each loop
            System.out.println("Retrieving Sheets using for-each loop");
            for (org.apache.poi.ss.usermodel.Sheet sheet : workbook) {
                System.out.println("=> " + sheet.getSheetName());
            }   // 3. Or you can use a Java 8 forEach with lambda
            System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
            workbook.forEach(sheet -> {
                System.out.println("=> " + sheet.getSheetName());
            });
            /*
            ==================================================================
            Iterating over all the rows and columns in a Sheet (Multiple ways)
            ==================================================================
             */
            // Getting the Sheet at index zero
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            // 1. You can obtain a rowIterator and columnIterator and iterate over them
            System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
            Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Row row = rowIterator.next();

                // Now let's iterate over the columns of the current row
                Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                }
                System.out.println();
            }   // 2. Or you can use a for-each loop to iterate over the rows and columns
            System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
            for (org.apache.poi.ss.usermodel.Row row : sheet) {
                for (org.apache.poi.ss.usermodel.Cell cell : row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                }
                System.out.println();
            }   // 3. Or you can use Java 8 forEach loop with lambda
            System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
            sheet.forEach(row -> {
                row.forEach(cell -> {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                });
                System.out.println();
            });
            // Closing the workbook
        }
    }

    public void readExtrato(String originalFile, JTable table) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        try {
            //Create Workbook instance holding reference to .xlsx file
            try (FileInputStream file = new FileInputStream(new File(originalFile))) {
                //Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = new XSSFWorkbook(file);

                //Get first/desired sheet from the workbook
                XSSFSheet sheet = workbook.getSheetAt(0);

                //Iterate through each rows one by one
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    model.addRow(new Object[]{
                        "",
                        ""
                    });
                    Row row = rowIterator.next();
                    //For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        //Check the cell type and format accordingly
                        switch (cell.getCellType().toString()) {
                            case "NUMERIC":
                                model.setValueAt(cell.getNumericCellValue(), cell.getRowIndex(), cell.getColumnIndex());
                                System.out.print(cell.getNumericCellValue() + " ");
                                break;
                            case "STRING":
                                model.setValueAt(cell.getStringCellValue(), cell.getRowIndex(), cell.getColumnIndex());
                                System.out.print(cell.getStringCellValue() + " ");
                                break;
                        }
                    }
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para exportação de tabelas em Excel
     * 
     * @param table Tabela que serão passados os dados
     * @param file Nome do arquivo que será exportado
     * @param col Coluna inicial para pegar os dados
     * @throws IOException 
     */
    public static void exportTable(JTable table, File file, int col) throws IOException {
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        File filePronto = new File(home + file.toString());
        
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(filePronto);

        for (int i = col; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
        
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = col; j < model.getColumnCount(); j++) {
                if (model.getValueAt(i, j) != null) {
                    out.write(model.getValueAt(i, j).toString() + "\t");
                } else {
                    out.write(" \t");
                }
            }
            out.write("\n");
        }
        out.close();
        JOptionPane.showMessageDialog(null, "Arquivo salvo em " + filePronto);
//        System.out.println("write out to: " + file);
    }
}
