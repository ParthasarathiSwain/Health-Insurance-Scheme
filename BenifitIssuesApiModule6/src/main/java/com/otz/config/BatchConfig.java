package com.otz.config;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.otz.bindings.ElgibilityDetails;
import com.otz.entity.ElgibilityDetailsEntity;
import com.otz.processor.EDDetailsProcessor;
import com.otz.repository.IElgibilityDetermineRepository;


@Configuration
public class BatchConfig {
	@Autowired
	private IElgibilityDetermineRepository elgiRepo;
	@Autowired
	private EDDetailsProcessor edProcessor;
	@Bean(name="reader")
	public RepositoryItemReader<ElgibilityDetailsEntity> createReader(){
		return new RepositoryItemReaderBuilder<ElgibilityDetailsEntity>()
				.name("repoReader")
				.repository(elgiRepo)
				.methodName("findAll")
				.sorts(Map.of("caseNo",Sort.Direction.ASC))
				.build();
	} 


	@Bean(name="writer")
	public FlatFileItemWriter<ElgibilityDetails> createWriter(){
		return new FlatFileItemWriterBuilder<ElgibilityDetails>()
				.name("file-writer")
				.resource(new FileSystemResource("beneficiries_list.csv"))
				.lineSeparator("\r\n")
				.delimited().delimiter(",")
				.names("caseNo","holderName","holderssn","planName","planStatus","benifitAmount","bankName","accountNumber")
				.build()
				;
	}
	//step obj
	@Bean(name="step1")
	public Step createStep1(JobRepository jobRepository,PlatformTransactionManager platformTransactionManager){
		return new StepBuilder("step1",jobRepository)
				.<ElgibilityDetailsEntity,ElgibilityDetails>chunk(3,platformTransactionManager)
				.reader(createReader())
				.processor(edProcessor)
				.writer(createWriter())
				.build()
				;
	}
	//step obj
	@Bean(name="job1")
	public Job createJob(JobRepository jobRepository,Step step1){
		return new JobBuilder("job1",jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(step1)
				.build()
				;
	}
}
















