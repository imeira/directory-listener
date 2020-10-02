package com.igormeira.directorylistener.service;

import com.igormeira.directorylistener.data.*;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;

import java.util.Arrays;
import java.util.stream.Stream;

class FileServiceTest {
    @Mock
    Log LOG;
    @Mock
    Environment env;
    @InjectMocks
    FileService fileService;
    @Mock
    Message<String> msg;
    @Mock
    Stream<String> stream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testTransferFileFolder() {
        fileService.transferFileFolder(msg);
    }

    @Test
    void testCreateFileReport() {
        FileService.createFileReport("fileName", env);
    }

    @Test
    void testLineConvert() {
            FileService.lineConvert(stream, new Report("fileName", Arrays.<SalesMan>asList(new SalesMan("name", "document", Double.valueOf(0))), Arrays.<Client>asList(new Client("name", "document", "area")), Arrays.<Sale>asList(new Sale("id", Arrays.<Item>asList(new Item("id", "quantity", Double.valueOf(0))), "salesmanName")), Integer.valueOf(0), Integer.valueOf(0), "salesExpensiveID", "salesMansWorst"));
    }

    @Test
    void testWriteFileReportOUT() {
        FileService.writeFileReportOUT("src/main/resources/examples/file1.txt", new Report("fileName", Arrays.<SalesMan>asList(new SalesMan("name", "document", Double.valueOf(0))), Arrays.<Client>asList(new Client("name", "document", "area")), Arrays.<Sale>asList(new Sale("id", Arrays.<Item>asList(new Item("id", "quantity", Double.valueOf(0))), "salesmanName")), Integer.valueOf(0), Integer.valueOf(0), "salesExpensiveID", "salesMansWorst"), env, true);
    }
}
