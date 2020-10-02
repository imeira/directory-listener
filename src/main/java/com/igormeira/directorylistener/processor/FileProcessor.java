package com.igormeira.directorylistener.processor;

import com.igormeira.directorylistener.service.FileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

public class FileProcessor {

    private static final Log LOG = LogFactory.getLog(FileProcessor.class);

    @Autowired
    private FileService fileService;

    public void process(Message<String> msg) {
        LOG.info("started -> process" );
        fileService.transferFileFolder(msg);
    }


}
