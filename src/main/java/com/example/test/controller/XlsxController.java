package com.example.test.controller;

import com.example.test.service.FileProcessorService;
import com.example.test.service.NthMinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class XlsxController {

    private final FileProcessorService fileProcessorService;
    private final NthMinService nthMinService;

    public XlsxController(@Autowired FileProcessorService fileProcessorService, @Autowired NthMinService nthMinService) {
        this.fileProcessorService = fileProcessorService;
        this.nthMinService = nthMinService;
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный результат",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Integer.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Пример успешного ответа",
                                    value = "2"
                            )
                    )
            )})

    @Operation(
            summary = "Найти N-ое минимальное число в XLSX файле",
            description = "Принимает путь к локальному XLSX файлу и номер N. Возвращает N-ое по величине минимальное число из столбца чисел."
    )
    @GetMapping("/nth-min")
    public int getNthSmallest(
            @Parameter(description = "Путь к локальному XLSX файлу", required = true, example = "/tmp/numbers.xlsx")
            @RequestParam String filePath,

            @Parameter(description = "Номер минимального числа", required = true, example = "2")
            @RequestParam int n)
            throws IOException {
        var numbers = fileProcessorService.readNumbersFromXlsx(filePath);
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Пустой файл");
        }
        return nthMinService.findNthSmallest(numbers, n);
    }
}

