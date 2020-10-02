package com.igormeira.directorylistener;

import com.igormeira.directorylistener.processor.FileProcessor;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.IOException;

class DirectoryListenerApplicationTest {
    @Mock
    Environment env;
    @Mock
    Log LOG;
    @InjectMocks
    DirectoryListenerApplication directoryListenerApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMain() {
        try {
            DirectoryListenerApplication.main(new String[]{"args"});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProcessFileFlow() {
        IntegrationFlow result = directoryListenerApplication.processFileFlow();
        Assertions.assertNotEquals(null, result);
    }

    @Test
    void testFileReadingMessageSource() {
        MessageSource<File> result = directoryListenerApplication.fileReadingMessageSource();
        Assertions.assertNotEquals(null, result);
    }

    @Test
    void testFileInputChannel() {
        MessageChannel result = directoryListenerApplication.fileInputChannel();
        Assertions.assertNotEquals(null, result);
    }

    @Test
    void testFileToStringTransformer() {
        FileToStringTransformer result = directoryListenerApplication.fileToStringTransformer();
        Assertions.assertNotEquals(null, result);
    }

    @Test
    void testFileProcessor() {
        FileProcessor result = directoryListenerApplication.fileProcessor();
        Assertions.assertNotEquals(new FileProcessor(), result);
    }
}

