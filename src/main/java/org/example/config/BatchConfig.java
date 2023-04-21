package org.example.config;

import org.example.model.IceCream;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1()).next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<IceCream, IceCream>chunk(5)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").<IceCream, IceCream>chunk(5)
                .reader(reader())
                .writer(fileWriter())
                .build();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FlatFileItemReader<IceCream> reader()
    {
        //Create reader instance
        FlatFileItemReader<IceCream> reader = new FlatFileItemReader<IceCream>();

        //Set input file location
        reader.setResource(new FileSystemResource(new File("input/IceCreamSales.csv")));

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "flavour", "price", "amount" });
                    }
                });
                //Set values in IceCream class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<IceCream>() {
                    {
                        setTargetType(IceCream.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public ConsoleItemWriter<IceCream> writer()
    {
        return new ConsoleItemWriter<IceCream>();
    }

    @Bean
    public FileItemWriter<IceCream> fileWriter()
    {
        return new FileItemWriter<IceCream>();
    }
}
