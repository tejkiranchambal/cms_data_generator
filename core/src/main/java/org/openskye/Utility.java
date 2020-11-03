package org.openskye;

import org.codehaus.jettison.json.JSONException;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static org.openskye.TemplateJsonReader.*;
import static org.openskye.TemplateJsonWriter.*;
import static org.openskye.Utility.sequence;

class Utility {
    public static String tcmId;
    public static String three;
    public static String four;
    public static String sequence;
    public static String s;

    public static String cmsFileName() throws java.text.ParseException, IOException, ParseException, JSONException {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        final String tcm = "tcm";
        String str = sf.getName();
        String array[] = str.split("[ _.]+");
        tcmId = array[0];
        String publishDateTime = array[1];
        sequence = array[3];
        sequence = createRandomNumberGenerate(100000, 999999);
        String FileNameCms;
        /*String originalTcmId = tcmId;
        String originalPublistDateTime = publishDateTime;
        String originalSequence = sequence;
        System.out.println(originalTcmId);*/
        FileNameCms = (cmsTcmId(FileNames)  + "_" + callDateMethod(DateOperator) + "_" + sequence + ".");
        return FileNameCms;
    }

    public static String componentCmsFileName() throws java.text.ParseException, IOException, ParseException, JSONException {

        if((operator) == 'A' )
        {
            s = createAllFiles();
        }
        else if  ( (operator) == 'B'  ) {
            s =   createImageComponentFiles();

        }
        else if  ( (operator) == 'C'  )
        {
            s =    createPdfComponentFiles();
        }
        else
        {
            s =     createAudioComponentFiles();
        }
        if((operator) == 'E' )
        {
            s =   createVideosComponentFiles();
        }
        File sf = new File(s);
        final String tcm = "tcm";
        String str = sf.getName();
        String array[] = str.split("[ :-]+");
        sequence = array[3];
        sequence = createRandomNumberGenerate(100000, 999999);
        page = "16";
        String FileNameCms;
        /*String originalTcmId = tcmId;
        String originalPublistDateTime = publishDateTime;
        String originalSequence = sequence;
        System.out.println(originalTcmId);*/
        FileNameCms = ( array[0] + "-" + array[1] + "-" + array[2] + "-" + page + "_"+ callDateMethod(DateOperator) + "_" + sequence + ".");
        return FileNameCms;
    }



    public static String cmsOriginalFileName() throws java.text.ParseException, IOException, ParseException, JSONException {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        String array[] = str.split("[- _.]+");
        page = array[3];
        FileNameCms = ( array[0] + "-" + array[1] + "-" + array[2] + "-" + page + "_"+ callDateMethod(DateOperator) + "_" +  array[5] + "." + array[6]);
        return FileNameCms;
    }




    public static String cmsTcmId(String FileNames) throws java.text.ParseException, IOException {

        final String tcm = "tcm";
        String array[] = tcmId.split("[-]+");
        String threes = array[1];
        four = array[2];
        page = array[3];
        threes = createRandomNumberGenerate(100, 999);
        four = createRandomNumberGenerate(1000, 9999);
        return ("tcm-" + threes + "-" + four + "-" + page);

    }

    public static String getComponentSuffix() throws java.text.ParseException, IOException {

        String array[] = ComponentId.split("[-]+");
        String threes = array[1];
        return (threes);

    }

    public static String getPagePrefix() throws java.text.ParseException, IOException {

        String array[] = TCMID.split("[-]+");
        return (array[0] + "-" + array[1] + "-" + getComponentSuffix() + "-");

    }

    public static String getVersionForComponentFile() throws java.text.ParseException, IOException {

        String array[] = s.split("[-]+");
        String Version = array[3];
        return (Version);

    }

    public static String getItemUriForComponentFile() throws java.text.ParseException, IOException {

        String array[] = s.split("[:-]+");
        String tcm = array[0];
        String threes = array[1];
        String four = array[2];
        return (tcm + "-" + threes + "-" + four);

    }

    public static String createRandomNumberGenerate(int minRange, int maxRange) throws java.text.ParseException, IOException {

        Random objGenerator = new Random();
        if(minRange <= 100 && maxRange >=999)
        {
            int randomNumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            three = Integer.toString(randomNumber);
        }
        if(minRange <= 1000 && maxRange >=9999)
        {
            int fournumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            three = Integer.toString(fournumber);
        }
        if(minRange <= 100000 && maxRange >=999999)
        {
            int sequencenumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            three = Integer.toString(sequencenumber);

        }

        return three;
    }

    public static String setitemUri(String FileNames) {
        String ItemUri;
        String array[] = FileNames.split("[ :-]+");
        String tcm = array[0];
        String three = array[1];
        String four = array[2];
        ItemUri = (tcm+ "-" + three + "-" + four);
        return ItemUri;
    }


    public static String currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String createCmsFile_CurrentDate() throws IOException {

        StringBuffer stringBuffer = new StringBuffer();
        Date now = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        simpleDateFormat.format(now, stringBuffer, new FieldPosition(0));

        return simpleDateFormat.format(now);
    }

    public static String createFilesFromRandomDates() throws java.text.ParseException {



        // Declare DateTimeFormatter with desired format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        // Save current LocalDateTime into a variable
        LocalDateTime localDateTime = LocalDateTime.now();

        // Format LocalDateTime into a String variable and print
        String formattedLocalDateTime = localDateTime.format(dateTimeFormatter);
        System.out.println("Current Date: " + formattedLocalDateTime);

        //Get random amount of days between 5 and 10
        Random random = new Random();
        int randomAmountOfDays = random.nextInt(10 - 5 + 1) + 5;
        System.out.println("Random amount of days: " + randomAmountOfDays);

        // Add randomAmountOfDays to LocalDateTime variable we defined earlier and store it into a new variable
        LocalDateTime futureLocalDateTime = localDateTime.plusDays(randomAmountOfDays);

        // Format new LocalDateTime variable into a String variable and print
        String formattedFutureLocalDateTime = futureLocalDateTime.format(dateTimeFormatter);
        System.out.println("Date " + randomAmountOfDays + " days in future: " + formattedFutureLocalDateTime);
        return formattedFutureLocalDateTime;
    }


    public static String datefrom() throws java.text.ParseException {
        String now = CreateFilesFromDates;

        Random random = new Random();

        DateTime startTime = new DateTime(now);

        String outputText1;
        Minutes minimumPeriod = Minutes.TWO;
        int minimumPeriodInSeconds = minimumPeriod.toStandardSeconds().getSeconds();
        int maximumPeriodInSeconds = Hours.ONE.toStandardSeconds().getSeconds();
        DateTime diff  =startTime.plus(1);
        Seconds randomPeriod = Seconds.seconds(random.nextInt(maximumPeriodInSeconds - minimumPeriodInSeconds));
        DateTime endTimes = startTime.plusDays(1).plus(minimumPeriod).plus(randomPeriod);
        DateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        Date date1 = inputFormat.parse(String.valueOf(endTimes));
        outputText1 = outputFormat.format(date1);


        return (outputText1);
    }

    public static String createCmsFileDate_Flag() throws IOException, java.text.ParseException {
        String now = CreateFilesFromDate;
        String outputText = null;
        DateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

        String inputText = now;
        Date date1 = inputFormat.parse(inputText);
        outputText = outputFormat.format(date1);
        return outputText;
    }

    public static String createFilesFromFileDates()
    {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        String array[] = str.split("[- _.]+");
        String date = array[4];
        return date;
    }



}