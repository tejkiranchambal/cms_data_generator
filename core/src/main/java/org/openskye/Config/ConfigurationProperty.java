package org.openskye.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import java.util.ArrayList;
import java.util.List;

/**
 * ConfigurationProperty for the worker.
 */

@Data
@Configuration
@EnableConfigurationProperties
@EnableSpringConfigured
@PropertySource(value = "config/application.yml")
@ConfigurationProperties(prefix = "configuration")
public class ConfigurationProperty {


    private String SourceFilePath = "C:\\trycms\\Batch1\\CMS_files";
    private String OutputSourceFilepath = "C:\\trycms\\Batch1\\Output_files";
    private String CreateFilesFromDate = "2019-01-20T10:31:43";
    private String CreateFilesFromDatess = "2020-01-20T10:31:43";
    private int NoOfComponentCreate = 10 ;
    public int NumberOfFiles = 10;
    private int NumberOfSequenceFiles = 10;

    private List<String> NumberOfFile = new ArrayList<>();

    {
         int NumberOfRandomFiles = 10;
         int NumberOfFilesNullItemURI = 10;
         int NumberOfSequencesFiles = 10;
         int NumberOfMissingCMSFiles = 9;
         int NumberOfMissingOutputFiles = 8;
         int NumberOf0kbFiles = 5;
         String CreateFilesFromDates = "2020-01-20T10:31:43";
    }
    }

