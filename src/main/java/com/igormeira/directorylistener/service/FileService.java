package com.igormeira.directorylistener.service;

import com.igormeira.directorylistener.data.Report;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileService {

    private static final String HEADER_FILE_NAME = "file_name";
    private static final String MSG = "*** %s received. ***";
    private static final Log LOG = LogFactory.getLog(FileService.class);

    @Autowired
    private Environment env;

    public void transferFileFolder(Message<String> msg) {
        LOG.info("started -> Service TransferFileFolder" );
        try {
            LOG.info("*** Reading Source Files in -> " + env.getProperty("HOMEPATH") + env.getProperty("DIR_IN") + " *** ");
            if (msg != null && msg.getHeaders() != null) {
                String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
                String content = msg.getPayload();
                LOG.info(String.format(MSG, fileName));
                writeFileOUT(fileName, content, true);
                deleteFileIN(fileName);
            } else {
                writeFileReportOUT(null, new Report(), env, true);
            }
        } catch (IOException | InterruptedException e) {
            LOG.error(e);
            writeFileReportOUT(null, new Report(), env, true);
        }

    }

    private void writeFileOUT(String fileName, String content, boolean append) throws IOException, InterruptedException {
        File newFile = new File(env.getProperty("HOMEPATH") + env.getProperty("DIR_OUT") + fileName);
        FileUtils.writeStringToFile(newFile, content, Charset.forName("UTF-8"), append);
        File newFileReport = new File(env.getProperty("HOMEPATH") + env.getProperty("DIR_OUT") + "Report_" + fileName);
        FileUtils.writeStringToFile(newFileReport, content, Charset.forName("UTF-8"), append);
        LOG.info("*** File was saved to -> " +  env.getProperty("HOMEPATH") + env.getProperty("DIR_OUT") + fileName + " ***");
        createFileReport(fileName, env);
        Thread.sleep(1000);
    }

    private void deleteFileIN(String fileName) throws IOException, InterruptedException {
        File newFile = new File(env.getProperty("HOMEPATH") + env.getProperty("DIR_IN") + fileName);
        FileUtils.forceDelete(newFile);
        Thread.sleep(1000);
    }

    public static void createFileReport(String fileName, Environment env) {
        Report report = new Report();
        try {
            report.setFileName(fileName);
            Stream<String> stream = Files.lines(Path.of(env.getProperty("HOMEPATH") + env.getProperty("DIR_IN") + fileName));

            lineConvert(stream, report);

            writeFileReportOUT(fileName, report, env, false);
        } catch (IOException e) {
            LOG.error(e);
            writeFileReportOUT(fileName, report, env, true);
        }
    }

    public static void lineConvert(Stream<String> stream, Report report){
        stream.map(line -> line.split("ç"))
                .collect(Collectors.toList())
                .forEach(line -> new <List>Report(report, line));
    }

    public static void writeFileReportOUT(String fileName, Report report, Environment env, boolean error){
        try {
            FileWriter fileWriter = new FileWriter( env.getProperty("HOMEPATH") + env.getProperty("DIR_OUT") + "Report_" + fileName );
            if (!error && (report.getSales().size() > 0 || report.getSalesMans().size() > 0 || report.getClients().size() > 0)) {
                fileWriter.write(lineConstructor("client Amount= " + report.getClientAmount()));
                fileWriter.write(lineConstructor("salesMans Amount= " + report.getSalesMansAmount()));
                fileWriter.write(lineConstructor("sales Expensive ID- " + report.getSalesExpensiveID()));
                fileWriter.write(lineConstructor("salesMans Worst= " + report.getSalesMansWorst()));
            } else {
                fileWriter.write(lineConstructor("Arquivo com formato invalido. Corrigir e tentar novamente!!! -> " + fileName));
            }

            fileWriter.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    private static String lineConstructor(String msg){
        return new String(msg + System.lineSeparator());
    }

}
