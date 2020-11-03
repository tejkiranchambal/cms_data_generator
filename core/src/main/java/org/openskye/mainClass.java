package org.openskye;

import org.openskye.Config.ConfigurationProperty;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Scanner;
@SpringBootApplication

@EnableConfigurationProperties({
        ConfigurationProperty.class
})
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
class mainClass {
    public static void createFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which function do you wish you use?\n" +
                "Enter 1 for create randomfiles\n" +
                "Enter 2 for create sequencefile \n" +
                "Enter 3 for create OverrideFiles \n" +
                "Enter 4 for create MissingCmsFile \n" +
                "Enter 5 for create Create0kbFile\n" +
                "Enter 6 for create MissingOutputFile \n" +
                "Enter 7 for create AllTypeFilesInDifferentBatches \n" +
                "Enter 8 for create AllTypeFilesInOneBatch \n" +
                "Enter 9 for create filesWithoutItemUri \n" +
                "Enter 0 for create MultimediaPageFileCreate  \n" +
                "Enter A for create MultimediaFilesInOneBatch  \n" +
                "Enter B for create ImageMultimediaFile \n" +
                "Enter C for create PdfFileMultimediaFile \n" +
                "Enter D for create AudioFile \n" +
                "Enter E for create VideosMultimediaFile");

       TemplateJsonWriter.operator  = (scanner.next().charAt(0));
    }

    public static void createFileFromDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which function do you wish you use?\n" +
                "Enter 1 for create Files from CurrentDate\n" +
                "Enter 2 for create Files from SpecificDate \n" +
                "Enter 3 for create Files from InFileDate \n" +
                "Enter 4 for create Files from Random Range \n" +
                "Enter 5 for create Files from Specific Range");

      TemplateJsonWriter.DateOperator = scanner.next().charAt(0);
    }
}