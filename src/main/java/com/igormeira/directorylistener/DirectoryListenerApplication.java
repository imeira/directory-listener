package com.igormeira.directorylistener;

import com.igormeira.directorylistener.processor.FileProcessor;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.LastModifiedFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SpringBootApplication
public class DirectoryListenerApplication {

	@Autowired
	private Environment env;

	private static final Log LOG = LogFactory.getLog(DirectoryListenerApplication.class);


	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(DirectoryListenerApplication.class, args);
	}

	@Bean
	public IntegrationFlow processFileFlow() {
		LOG.info("started -> processFileFlow" );
		return IntegrationFlows
				.from("fileInputChannel")
				.transform(fileToStringTransformer())
				.handle("fileProcessor", "process").get();
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> fileReadingMessageSource() {
		LOG.info("started -> fileReadingMessageSource" );
		CompositeFileListFilter<File> filters =new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter(env.getProperty("EXTENSION_ALLOWED")));
		filters.addFilter(new LastModifiedFileListFilter());

		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(new File(env.getProperty("HOMEPATH") + env.getProperty("DIR_IN")));
		source.setFilter(filters);
		LOG.info("*** Loading Source Files -> " + env.getProperty("HOMEPATH") + env.getProperty("DIR_IN") + " *** ");
		return source;
	}

	@Bean
	public MessageChannel fileInputChannel() {
		LOG.info("started -> fileInputChannel" );
		return new DirectChannel();
	}

	@Bean
	public FileToStringTransformer fileToStringTransformer() {
		LOG.info("started -> fileToStringTransformer" );
		return new FileToStringTransformer();
	}

	@Bean
	public FileProcessor fileProcessor() {
		LOG.info("started -> fileProcessor" );
		return new FileProcessor();
	}

}
