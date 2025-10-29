package com.example.test.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessorService {
    public List<Integer> readNumbersFromXlsx(String filePath) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    double numericValue = cell.getNumericCellValue();
                    numbers.add((int) numericValue);
                }
            }
        } catch (IOException e) {
            throw new IOException("Не удалось открыть файл");
        }
        return numbers;
    }
}
