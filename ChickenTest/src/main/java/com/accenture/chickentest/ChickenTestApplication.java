package com.accenture.chickentest;

import com.accenture.chickentest.service.TransactionService;
import com.accenture.chickentest.service.TransformationService;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;





@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableEurekaClient
public class ChickenTestApplication {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;




    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    public static void main(String[] args) {

        SpringApplication.run(ChickenTestApplication.class, args);

    }

  @Scheduled(cron = "0 */1 * * * ?")
    public void perform() throws Exception
    {
       JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
       jobLauncher.run(job, params);
   }



}
