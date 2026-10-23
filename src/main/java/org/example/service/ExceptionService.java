package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ExceptionService {

    private static final Logger log = LoggerFactory.getLogger(ExceptionService.class);

    public void checkCheckedExceptionRequiredThrows() throws IOException {
        throw new IOException("Это проверяемое исключение!");
    }

    public void checkUncheckedException() {
        throw new RuntimeException("Это непроверяемое исключение!");
    }

    public String logSample(String str) {
        log.info("Получена строка для логирования: {}", str);

        try {
            throw new FileNotFoundException("Файл не найден по пути: " + str);
        } catch (Exception exc) {
            log.error("Произошла ошибка при работе с файлом: ", exc);
            exc.printStackTrace();
        }

        return "Ошибка обработана и залогирована. Смотри консоль!";
    }
}