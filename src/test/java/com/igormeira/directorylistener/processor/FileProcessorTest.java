package com.igormeira.directorylistener.processor;

import com.igormeira.directorylistener.service.FileService;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.Message;

class FileProcessorTest {
    @Mock
    Log LOG;
    @Mock
    FileService fileService;
    @InjectMocks
    FileProcessor fileProcessor;
    @Mock
    Message<String> msg;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcess() {
        fileProcessor.process(msg);
    }
}

