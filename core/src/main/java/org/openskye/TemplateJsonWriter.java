package org.openskye;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
import org.json.simple.parser.ParseException;
import org.openskye.Config.ConfigurationProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.openskye.JsonFileTemplate.parseJSONFile;
import static org.openskye.TemplateJsonReader.*;
import static org.openskye.Utility.*;
import static org.openskye.Utility.cmsOriginalFileName;
import static org.openskye.mainClass.createFile;
import static org.openskye.mainClass.createFileFromDate;

@Component
@SpringBootApplication
public class TemplateJsonWriter implements StatisticFiles {
    @Autowired
    ConfigurationProperty configuration;

    static String jsonString = null;
    public static JSONObject jo = null;
    public static String SourcePaths = null;
    public static String MultimediaSourcePath = null;
    public static String MultimediaOutputSrcFile = null;
    public static String PageMultimediaSourcePath = null;
    public static String PageMultimediaOutputSrcFile = null;
    public static String VideoSourcePaths = null;
    public static String VideoOutputSrcFile = null;
    public static String AudioSourcePaths = null;
    public static String AudioOutputSrcFile = null;
    public static String PdfSourcePaths = null;
    public static String PdfOutputSrcFile = null;
    public static String ImageSourcePaths = null;
    private static String NumberOfFiles = null;
    private static String NumberOfFile = null;
    private static String NumberOfSequenceFiles = null;
    public static String CreateFilesFromDate = null;
    public static String CreateFilesFromDatess = null;
    private static String OutputSrcFile = null;
    private static String ImageOutputSrcFile = null;
    private static String NumberOfRandomFiles = null;
    public static String CreateFilesFromDates = null;
    private static String NumberOfSequencesFiles = null;
    public static String NumberOfUpdatedFiles = null;
    private static String NumberOfMissingCMSFiles = null;
    private static String NumberOfMissingOutputFiles = null;
    private static String NumberOf0kbFiles = null;
    private static String NumberOfFilesNullItemURI = null;

    public static String NoOfComponentCreate = null;
    private static String ItemUri = null;
    private static String Version = null;
    private static String Output_Type = null;
    private static String Cms_Type = null;
    static String duplicate = null;
    static String FileNameCms = null;
    public static String page ;
    public static String directory;
    public static char operator;
    public static char batch;
    public static char batchName;
    public static char DateOperator;
    public static File dir;
    public static File dirOutput;
    public static File dirCms;
    public static File file;
    public static File OutputDestFile;
    public static String FileNames;
    public static File OutputSourceFile;
    public static File random_OutputFile;
    public static int randomTime;
    public static SimpleDateFormat sdf = null;
    int count = 1;
    public static int i;
    static TemplateJsonReader templateJsonReader = new TemplateJsonReader();
    Map<String, Integer> mapOfRepeatedWordName = new HashMap<String, Integer>();
    Map<String, Integer> mapOfRepeatedWordTcmId = new HashMap<String, Integer>();
    Map<String, Integer> mapOfRepeatedWordVersion = new HashMap<String, Integer>();
    Map<String, Integer> mapOfRepeatedWordLocation = new HashMap<String, Integer>();
    Map<String, Integer> mapOfRepeatedComponentName = new HashMap<String, Integer>();
    Map<String, Integer> mapOfRepeatedComponentVersion = new HashMap<String, Integer>();


    public TemplateJsonWriter() throws IOException, ParseException, JSONException, java.text.ParseException {

        jsonString = parseJSONFile(new File("ui\\ConfigCMSData.json"), "UTF-8");
        jo = (new org.codehaus.jettison.json.JSONObject(jsonString));
        SourcePaths = (String) jo.get("SourceFilePath");
        ImageSourcePaths = (String) jo.get("SourceFilePathForImage");
        MultimediaSourcePath = (String) jo.get("SourceFilePathForMultimedia");
        MultimediaOutputSrcFile = (String) jo.get("OutputSourceFilepathForMultimedia");
        PageMultimediaOutputSrcFile = (String) jo.get("OutputSourceFilepathForPageMultimedia");
        PageMultimediaSourcePath = (String) jo.get("SourceFilePathForPageMultimedia");
        VideoSourcePaths = (String) jo.get("SourceFilePathForVideo");
        AudioSourcePaths = (String) jo.get("SourceFilePathForAudio");
        PdfSourcePaths = (String) jo.get("SourceFilePathForPdf");
        VideoOutputSrcFile = (String) jo.get("OutputSourceFilepathForVideo");
        AudioOutputSrcFile = (String) jo.get("OutputSourceFilepathForAudio");
        PdfOutputSrcFile = (String) jo.get("OutputSourceFilepathForPdf");
        NumberOfFiles = (String) jo.get("NumberOfFiles");
        NumberOfSequenceFiles = (String) jo.get("NumberOfSequenceFiles");
        NoOfComponentCreate = (String) jo.get("NoOfComponentCreate");
        OutputSrcFile = (String) jo.get("OutputSourceFilepath");
        ImageOutputSrcFile = (String) jo.get("OutputSourceFilepathForImage");
        JSONObject NOF = (JSONObject) jo.get("NumberOfFile");
        NumberOfRandomFiles = (String) NOF.get("NumberOfRandomFiles");
        NumberOfFilesNullItemURI = (String) NOF.get("NumberOfFilesNullItemURI");
        NumberOfSequencesFiles = (String) NOF.get("NumberOfSequencesFiles");
        NumberOfUpdatedFiles = (String) NOF.get("NumberOfUpdatedFiles");
        NumberOfMissingCMSFiles = (String) NOF.get("NumberOfMissingCMSFiles");
        NumberOfMissingOutputFiles = (String) NOF.get("NumberOfMissingOutputFiles");
        NumberOf0kbFiles = (String) NOF.get("NumberOf0kbFiles");
        CreateFilesFromDates = (String) NOF.get("CreateFilesFromDates");
        CreateFilesFromDate = (String) jo.get("CreateFilesFromDate");
        CreateFilesFromDatess = (String) jo.get("CreateFilesFromDatess");
        if (CreateFilesFromDate.equals("")) {
            createCmsFile_CurrentDate();
        }

        createFile();
        createFileFromDate();

        File name = new File("Statistic_NameCount.txt");
        FileWriter fr = null;
        fr = new FileWriter(name.getAbsoluteFile(), true);

        File version = new File("Statistic_VersionCount.txt");
        FileWriter frVersion = null;
        frVersion = new FileWriter(version.getAbsoluteFile(), true);

        File Location = new File("Statistic_LocationCount.txt");
        FileWriter frLocation = null;
        frLocation = new FileWriter(Location.getAbsoluteFile(), true);

        File TcmId = new File("StatisticTcmId.txt");
        TcmId.createNewFile();
        FileWriter frtcmid = null;
        frtcmid = new FileWriter(TcmId.getAbsoluteFile(), true);

        File CmpName = new File("Statistics.txt");
        CmpName.createNewFile();
        FileWriter frcmp_name = null;
        frcmp_name = new FileWriter(CmpName.getAbsoluteFile(), true);


        File akshara = new File("akshara.txt");
        akshara.createNewFile();
        FileWriter frtcmid1 = null;
        frtcmid1 = new FileWriter(akshara.getAbsoluteFile(), true);

        File componentVersionStatistics = new File("componentVersionStatistics.txt");
        componentVersionStatistics.createNewFile();
        FileWriter cmpVerSta = null;
        cmpVerSta = new FileWriter(akshara.getAbsoluteFile(), true);

        fr.write("\n" + "Words in Name" + "\t\t" + "Count");
        fr.write("\n" + createCmsFile_CurrentDate());
        frVersion.write("\n" + "Version" + "\t\t" + "Count");
        frLocation.write("\n" + "Location" + "\t\t" + "Count");
        frtcmid.write("\n" + "Tcmid" + "\t\t" + "Count");
        frcmp_name.write("Field" + "\t\t" + "Count");
        frtcmid1.write("Field" + "\t\t" + "Count");
        cmpVerSta.write("Field" + "\t\t" + "Count");

        for (i = 1; i <= Integer.parseInt(NumberOfFiles); i++) {

            try {



                createCmsFile();

                templateJsonReader.setDESTINATION_JSON_FILE(file);
                //JsonFileTemplate jsonFileTemplate = templateJsonReader.getSourceFromInputFile();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writeCmsFile();
                createOutputFile();
                if ((operator) == '3' || (operator) == '7' || (operator) == '8') {
                    break;
                }

            } catch (Exception ex) {
                continue;
            }

        }


        fr.close();
        frVersion.close();
        frLocation.close();
        frtcmid.close();

    }


   /* public static File createDirectoriesWithCommonParent(
            File parent, String...subs) throws JSONException, java.text.ParseException, ParseException, IOException {
        String directory = "batch";
        parent.mkdirs();
        if (!parent.exists() || !parent.isDirectory()) {
            return false;
        }

        for (String sub : subs) {
            File subFile = new File(parent, sub);
            subFile.mkdir();
            if (!subFile.exists() || !subFile.isDirectory()) {
                String Directory1 = ((directory + 1));
                dir = new File(batchPath + getBatch() + "\\" + getBatchName() + "\\" + Directory1 +"\\" +"Cms_files" +"\\");
                dir.mkdirs();            }
        else
            {

            }
        }

        return true;
    }*/

    public static ArrayList<String> getComponentversion() throws IOException, ParseException, JSONException {
        JSONObject jsonobject = null;
        JSONObject compoJson = null;
        Object cmp_name = null;
        String s = null;

        ArrayList<String> list = new ArrayList<String>();
        for (int j = 0; j < NewJsonArray.length(); j++) {
            jsonobject = NewJsonArray.getJSONObject(j);
            compoJson = jsonobject.getJSONObject("Component");
            compoJson.get("Version");
            cmp_name = compoJson.get("Version");
            s = cmp_name.toString();
            list.add(s);
        }
        return (list);
    }*/

    public static String getExtensionForCmsFile() {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        return FilenameUtils.getExtension(str);
    }

    public static String fullCmsFileName() throws java.text.ParseException, IOException {
        String Cms_File_Name = getExtensionForCmsFile();
        return Cms_File_Name;
    }

    public static String CmsFileNames() throws IOException, java.text.ParseException, ParseException, JSONException {
        templateJsonReader.setSOURCE_JSON_FILE(getRandomCmsSourceFile());
        FileNames = getCmsFileName();
        return FileNames;
    }

    public static void createOutputFile() throws JSONException, java.text.ParseException, ParseException, IOException {
            random_OutputFile = getRandomOutputSourceFile();
            OutputSourceFile = new File(String.valueOf(random_OutputFile));


        if ((operator) == '1'  || (operator) == '4' || (operator) == '9'  || (operator) == '0' || (operator) == 'A'  || (operator) == 'B' || (operator) == 'C'   || (operator) == 'D' || (operator) == 'E') {
            OutputDestFile = new File(createOutputFilePath() + "\\" + FileNameCms + getExtensionForOutputFile(OutputSourceFile));
        }

        else if ((operator) == '5') {
            OutputDestFile = new File(createOutputFilePath() + "\\" + FileNameCms);
            OutputDestFile.createNewFile();
        } else if ((operator) == '6') {
            if (i % 2 == 1) {
                OutputDestFile = new File(createOutputFilePath() + "\\" + FileNameCms + getExtensionForOutputFile(OutputSourceFile));
            }

        }
        if (OutputDestFile != null && (operator) != '5') {
            OutputDestFile.createNewFile();
            FileUtils.copyFile(random_OutputFile, OutputDestFile);
        }
        System.out.println("Success");
    }


    public static void createCmsFile() throws JSONException, java.text.ParseException, ParseException, IOException {
        templateJsonReader.setSOURCE_JSON_FILE(getRandomCmsSourceFile());
        File random_OutputFile = getRandomOutputSourceFile();
        String FileNames = getCmsFileName();
        String Cms_File_Name = FileNames + getExtensionForCmsFile();
        if ((operator) == '1' || (operator) == '5' || (operator) == '6' || (operator) == '9' || (operator) == '0' || (operator) == 'A'  || (operator) == 'B'  || (operator) == 'C'  || (operator) == 'D' || (operator) == 'E') {
            file = new File(createCmsFilePath() + "\\" + Cms_File_Name);
        }
        else if( (operator) == '3') {

            for (int i = 1; i <= 10; i++) {
                String directory = "batch" + i;
                dirCms = new File(createCmsFilePath_ForBatch3() + "\\" + directory + "\\" + "Cms_files");
                dirCms.mkdirs();

                file = new File(dirCms + "\\"  + FileNames);
                writeCmsFile();
                dirOutput = new File(createCmsFilePath_ForBatch3() + "\\" + directory + "\\" + "Output_files");
                dirOutput.mkdirs();
                fullOutputFileName();
                    OutputDestFile = new File(dirOutput + "\\" +FileNameCms + getExtensionForOutputFile(OutputSourceFile));


                OutputDestFile.createNewFile();
                FileUtils.copyFile(random_OutputFile, OutputDestFile);

            }

        }
        else if ((operator) == '7' || (operator) == '8') {
            for (int i = 1; i <= 7; i++) {
                 directory = "batch" + i;
                File dirCmsForBatch3 =  new File(createCmsFilePath_ForBatch3() + "\\" + directory + "\\" );
               if(directory.contains("batch3") == false) {
                   dirCms = new File(dirCmsForBatch3 + "\\" + "Cms_files");
                   dirCms.mkdirs();
               }
                File dirOutputForBatch3 = new File(createCmsFilePath_ForBatch3() + "\\" + directory + "\\" );
                if(directory.contains("batch3") == false) {
                    dirOutput = new File(dirOutputForBatch3 + "\\" + "Output_files");
                    dirOutput.mkdirs();
                }
                if (directory.contains("batch1")) {
                    if ((operator) == '1' || (operator) == '7' || (operator) == '8') {
                        for (int j = 0; j < Integer.parseInt(NumberOfRandomFiles); j++) {
                            String CmsFile_Name = CmsFileNames();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7')

                        {
                            file = new File(dirCms + "\\" + FullCmsFileName);

                        } else {
                            file = new File(createCmsFilePath() + "\\" + FullCmsFileName);

                        }

                        writeCmsFile();
                        fullOutputFileName();
                        if ((operator) == '7') {
                            OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                        } else {
                            OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                        }


                        OutputDestFile.createNewFile();
                        FileUtils.copyFile(random_OutputFile, OutputDestFile);
                        }
                    }
                }

                if (directory.contains("batch2")) {

                    if ((operator) == '2' || (operator) == '7' || (operator) == '8') {
                        for (int j = 0; j < Integer.parseInt(NumberOfSequencesFiles); j++) {
                            String CmsFile_Name = Cms_FileName_Sequence();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7') {
                                file = new File(dirCms + "\\" + FullCmsFileName);

                        } else {
                            file = new File(createCmsFilePath() + "\\" + FullCmsFileName);
                        }
                        writeCmsFile();
                        fullOutputFileName();
                        if ((operator) == '7') {
                            OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                            } else {
                                OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));
                            }
                            OutputDestFile.createNewFile();
                            FileUtils.copyFile(random_OutputFile, OutputDestFile);
                        }
                    }
                }
                if (directory.contains("batch3")) {
                    if ((operator) == '3' || (operator) == '7' || (operator) == '8') {

                        for (int j = 1; j <= Integer.parseInt(NumberOfUpdatedFiles); j++) {
                            String directory1 = "batch" + j;
                            File dirOverWriteCms = new File(dirCmsForBatch3 + "\\" + directory1 + "\\" + "Cms_files" + "\\");
                            dirOverWriteCms.mkdirs();
                            String CmsFile_Name = getCmsFileNameForOverrideFiles();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7') {
                                file = new File(dirOverWriteCms + "\\"  + Cms_File_Name);

                            } else {
                                file = new File(createCmsFilePath() + "\\" + FullCmsFileName);
                            }
                            writeCmsFile();
                            File dirOverWriteOutput = new File(dirOutputForBatch3 + "\\" + directory1 + "\\" + "Output_files" + "\\");
                            dirOverWriteOutput.mkdirs();
                            fullOutputFileName();

                            if ((operator) == '7') {
                                OutputDestFile = new File(dirOverWriteOutput + "\\" + FileNames + getExtensionForOutputFile(OutputSourceFile));

                        } else {
                            OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));
                        }

                            OutputDestFile.createNewFile();
                            FileUtils.copyFile(random_OutputFile, OutputDestFile);
                        }
                    }
                }
                if (directory.contains("batch4")) {
                    if ((operator) == '4' || (operator) == '7' || (operator) == '8') {
                        for (int j = 1; j <= Integer.parseInt(NumberOfMissingCMSFiles); j++) {
                            String CmsFile_Name = CmsFileNames();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            fullOutputFileName();
                            if ((operator) == '7') {
                                OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                        } else {
                            OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));
                        }

                            OutputDestFile.createNewFile();
                            FileUtils.copyFile(random_OutputFile, OutputDestFile);

                            if ((operator) == '7') {
                                if (j % 2 == 1) {
                                    file = new File(dirCms + "\\" + FullCmsFileName);
                                }
                                else {
                                    break;
                                }
                            } else {
                                if (j % 2 == 1) {
                                    file = new File(createCmsFilePath() + "\\" + FullCmsFileName);
                                } else {
                                    break;
                                }


                            }

                            writeCmsFile();
                        }
                        FileUtils.copyFile(random_OutputFile, OutputDestFile);
                    }
                }


                if (directory.contains("batch5")) {
                    if ((operator) == '5' || (operator) == '7' || (operator) == '8') {
                        for (int j = 0; j < Integer.parseInt(NumberOf0kbFiles); j++) {
                            String CmsFile_Name = CmsFileNames();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7') {
                                file = new File(dirCms + "\\" + FullCmsFileName);

                        } else {
                            file = new File(createCmsFilePath() + "\\" + FullCmsFileName);

                        }
                        writeCmsFile();
                        fullOutputFileName();
                        if ((operator) == '7') {
                            OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                        } else {

                            OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name);
                        }

                        OutputDestFile.createNewFile();

                        }
                    }
                }
                if (directory.contains("batch6")) {
                    if ((operator) == '6' || (operator) == '7' || (operator) == '8') {
                        for (int j = 1; j <= Integer.parseInt(NumberOfMissingOutputFiles); j++) {
                            String CmsFile_Name = CmsFileNames();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7') {
                                file = new File(dirCms + "\\" + FullCmsFileName);

                            } else {
                                file = new File(createCmsFilePath() + "\\" + FullCmsFileName);

                            }
                            writeCmsFile();

                                fullOutputFileName();

                                if ((operator) == '7') {
                                    if (j % 2 == 1) {
                                        OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));
                                    } else {
                                        break;
                                    }

                                } else
                                if (j % 2 == 1) {
                                    OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));
                                } else {
                                    break;
                                }
                                OutputDestFile.createNewFile();
                                FileUtils.copyFile(random_OutputFile, OutputDestFile);
                            }

                    }
                }
                if (directory.contains("batch7")) {
                    if ((operator) == '9' || (operator) == '7' || (operator) == '8') {
                        for (int j = 0; j < Integer.parseInt(NumberOfFilesNullItemURI); j++) {
                            String CmsFile_Name = CmsFileNames();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7') {
                                file = new File(dirCms + "\\" + FullCmsFileName);

                        } else {
                            file = new File(createCmsFilePath() + "\\" + FullCmsFileName);
                        }

                        writeCmsFile();
                        fullOutputFileName();
                        if ((operator) == '7') {
                            OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                        } else {
                            OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name);

                        }
                        OutputDestFile.createNewFile();

                        }
                    }
                }

                if (directory.contains("batch10") || directory.contains("batch11") || directory.contains("batch12") || directory.contains("batch13") ) {
                    if ((operator) == 'A' || (operator) == 'B' || (operator) == 'C' || (operator) == 'D'   || (operator) == '7' || (operator) == '8') {
                        for (int j = 0; j < Integer.parseInt(NumberOfRandomFiles); j++) {
                            String CmsFile_Name = cmsComponentFileNamesForImage();
                            String FullCmsFileName = CmsFile_Name + getExtensionForCmsFile();
                            if ((operator) == '7')

                            {
                                file = new File(dirCms + "\\" + FullCmsFileName);

                        } else {
                            file = new File(createCmsFilePath() + "\\" + FullCmsFileName);

                            }

                            writeCmsFile();
                            fullOutputFileNameForImage();
                            if ((operator) == '7') {
                                OutputDestFile = new File(dirOutput + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                            } else {
                                OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));

                            }


                            OutputDestFile.createNewFile();
                            FileUtils.copyFile(random_OutputFile, OutputDestFile);

                        }
                    }
                }
            }
        }
        else {
            switch (operator) {
                case '2': {
                    if ((operator) == '2') {
                        templateJsonReader.setSOURCE_JSON_FILE(getCmsFilesFromDestination());
                        random_OutputFile = getOutputFilesFromDestination();

                        for (int j = 0; j < Integer.parseInt(NumberOfSequencesFiles); j++) {
                            String CmsFile_Name = Cms_FileName_Sequence();
                            String FullCmsFileName = CmsFile_Name + fullCmsFileName();
                            file = new File(createCmsFilePath() + "\\" + FullCmsFileName);
                            writeCmsFile();
                            fullOutputFileName();
                            OutputDestFile = new File(createOutputFilePath() + "\\" + CmsFile_Name + getExtensionForOutputFile(OutputSourceFile));


                            OutputDestFile.createNewFile();
                            FileUtils.copyFile(random_OutputFile, OutputDestFile);
                        }
                    }
                }
                case '4': {
                    if ((operator) == '4')
                    if (i % 2 == 1) {
                        file = new File(createCmsFilePath() + "\\" + Cms_File_Name);

                    } else {
                        break;
                    }
                }
            }
        }


    }


    public static void writeCmsFile() throws JSONException, java.text.ParseException, ParseException, IOException {
        templateJsonReader.setDESTINATION_JSON_FILE(file);
        JsonFileTemplate jsonFileTemplate = templateJsonReader.getSourceFromInputFile();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(file, true);) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonFileTemplate.getJsonObject().toString());
            String prettyJsonString = gson.toJson(je);
            JsonObject jsonObject = je.getAsJsonObject();


            fw.write(prettyJsonString);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fullOutputFileName() throws IOException, java.text.ParseException {
        random_OutputFile = getRandomOutputSourceFile();
        OutputSourceFile = new File(OutputSrcFile + random_OutputFile);

    }

    public static void fullOutputFileNameForImage() throws IOException, java.text.ParseException {
        random_OutputFile = getRandomOutputSourceFileForImage();
        OutputSourceFile = new File(OutputSrcFile + random_OutputFile);

    }

    private static File getRandomOutputSourceFile() {
        Random rand = new Random();
        File[] files;
        if( (operator) == '0'){
             files = new File(PageMultimediaOutputSrcFile).listFiles();
        }
        else if((operator) == 'A'){
             files = new File(MultimediaOutputSrcFile).listFiles();
        }
        else if((operator) == 'B'){
             files = new File(ImageOutputSrcFile).listFiles();
        }
        else if((operator) == 'C'){
             files = new File(PdfOutputSrcFile).listFiles();
        }
        else if((operator) == 'D'){
             files = new File(AudioOutputSrcFile).listFiles();
        }
        else if((operator) == 'E'){
             files = new File(VideoOutputSrcFile).listFiles();
        }
        else {
             files = new File(OutputSrcFile).listFiles();
        }
        File file = files[rand.nextInt(files.length)];
        return file;
    }

    private static File getRandomOutputSourceFileForImage() {
        Random rand = new Random();
        File[] files = new File(ImageOutputSrcFile).listFiles();
        File file = files[rand.nextInt(files.length)];
        return file;
    }

    private static File getOutputFilesFromDestination() throws IOException, ParseException, java.text.ParseException, JSONException {
        Random rand = new Random();
        File[] files = new File(OutputFileFromBatch1).listFiles();
        File file = files[rand.nextInt(files.length)];
        return file;
    }


    public int countWord(String word, File file) throws FileNotFoundException {
        int count = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String nextToken = scanner.next();
            if (nextToken.equalsIgnoreCase(word))
                count++;
        }
        return count;
    }

    public static void main(String[] args) throws IOException, ParseException, JSONException, java.text.ParseException {
        TemplateJsonWriter templateJsonWriter = new TemplateJsonWriter();

    }


    public static String setitemUriNull(String FileNames) {

        String array[] = FileNames.split("[- _.]+");
        ItemUri = ("Null");
        return ItemUri;
    }

    public static String setcomponent() {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        String array[] = str.split("[- _.]+");
        String tcm = array[0];
        String three = array[1];
        String four = array[2];
         page = array[3];
        String date = array[4];
        String sequence = array[5];
        int b = Integer.parseInt(four);
        int c = Integer.parseInt(sequence);
        Random objGenerator = new Random();
        SimpleDateFormat formatter = null;
        Date dates = null;
        {

            int minRange = 100, maxRange = 999;
            int randomNumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            three = Integer.toString(randomNumber);
        }
        {
            int minRange = 1000, maxRange = 9999;
            int fournumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            four = Integer.toString(fournumber);
        }

        String FileNameCms = ("tcm:" + three + "-" + four);

        return FileNameCms;
    }

    public static String getExtensionForOutputFile(File OutputSrcFile) {
        File sf = new File(String.valueOf(OutputSrcFile));
        String str = sf.getName();
        return FilenameUtils.getExtension(str);
    }


    private static String getCmsFileNameForOverrideFiles() throws java.text.ParseException, IOException {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        String array[] = str.split("[- _.]+");
        FileNameCms = ("tcm-" + array[1] + "-" + array[2] + "-" + page + "_" + array[3] + "_" + array[5] + ".");

        return FileNameCms;
    }

    private static String getCmsFileName() throws IOException, java.text.ParseException, ParseException, JSONException {

        if ((operator) == '1' || (operator) == '5' || (operator) == '6' ||  (operator) == '0') {
            FileNameCms = cmsFileName();

        }
        if ((operator) == '3') {
            FileNameCms = cmsOriginalFileName();
        }
        if ((operator) == '4') {
            FileNameCms = cmsFileName();
        }
        if ((operator) == '7') {
            if ((operator) == '3' && (operator) == '8') {
                FileNameCms = cmsOriginalFileName();
            } else {
                FileNameCms = cmsFileName();
            }
        }
        if ((operator) == '8') {
            if ((operator) == '3' && (operator) == '7') {
                FileNameCms = cmsOriginalFileName();
            } else {
                FileNameCms = cmsFileName();
            }
        }
        if ((operator) == '9') {
            FileNameCms = cmsFileName();

        }
        if ((operator) == 'A') {
            FileNameCms = componentCmsFileName();

        }
        if ((operator) == 'B') {
            FileNameCms = componentCmsFileName();

        }
        if ((operator) == 'C') {
            FileNameCms = componentCmsFileName();

        }
        if ((operator) == 'D') {
            FileNameCms = componentCmsFileName();

        }
        if ((operator) == 'E') {
            FileNameCms = componentCmsFileName();

        }

        return FileNameCms;
    }


    public static String callMethodResult(char operator) throws IOException, ParseException, java.text.ParseException, JSONException {

        String result = null;
        switch (operator) {
            case '1':
                getCmsFileName();
                break;
            case '2':
                Cms_FileName_Sequence();
                break;
            case '3':
                getCmsFileName();
                break;
            case '4':
                getCmsFileName();
                break;
            case '5':
                getCmsFileName();
                break;
            case '6':
                getCmsFileName();
            case '7':
                getCmsFileName();
            case '8':
                getCmsFileName();
            case '9':
                getCmsFileName();
                break;
            case '0':
                getCmsFileName();
                break;
            case 'A':
                getCmsFileName();
                break;
            case 'B':
                getCmsFileName();
                break;
            case 'C':
                getCmsFileName();
                break;
            case 'D':
                getCmsFileName();
                break;
            case 'E':
                getCmsFileName();
                break;
            default:
                System.out.println("Invalid operator!");
                break;
        }
        return String.valueOf(operator);
    }


    public static String callDateMethod(char DateOperator) throws IOException, ParseException, java.text.ParseException, JSONException {

        String result = null;
        switch (DateOperator) {
            case '1':
                result = currentDate();
                break;
            case '2':
                result = createCmsFileDate_Flag();
                break;
            case '3':
                result = createFilesFromFileDates();
                break;
            case '4':
                result = createFilesFromRandomDates();
                break;
            case '5':
                result = datefrom();
                break;
            default:
                System.out.println("Invalid operator!");
                break;
        }
        return result;
    }

    public static String Create_BundleId() {
        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        String array[] = str.split("[- _.]+");
        String tcm = array[0];
        String three = array[1];
        String four = array[2];
         page = array[3];
        String date = array[4];
        String sequence = array[5];
        int b = Integer.parseInt(four);
        int c = Integer.parseInt(sequence);
        Random objGenerator = new Random();
        {

            int minRange = 10, maxRange = 99;
            int randomNumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            three = Integer.toString(randomNumber);
        }
        {
            int minRange = 1000, maxRange = 9999;
            int fournumber = objGenerator.nextInt(maxRange - minRange) + minRange;
            four = Integer.toString(fournumber);
        }

        {
            int minRange = 100000, maxRange = 999999;
            int sequencenumber = objGenerator.nextInt(maxRange - minRange) + minRange;

            sequence = Integer.toString(sequencenumber);
        }

        String FileNameCms = ("tcm:" + three + "-" + four + "-" + sequence);
        System.out.println(FileNameCms);
        return FileNameCms;
    }

    public static String Create_ApprovalId() {
        Random objGenerator = new Random();
        String three;

        int minRange = 100, maxRange = 9999;
        int randomNumber = objGenerator.nextInt(maxRange - minRange) + minRange;
        three = Integer.toString(randomNumber);
        return three;
    }

    private static String Cms_FileName_Sequence() throws java.text.ParseException {

        File sf = templateJsonReader.getSOURCE_JSON_FILE();
        String str = sf.getName();
        String array[] = str.split("[- _.]+");
        String tcm = array[0];
        String three = array[1];
        String four = array[2];
         page = array[3];
        String date = array[4];
        int sequence = Integer.parseInt(array[5]);
        int b = Integer.parseInt(four);
        int c = Integer.parseInt(String.valueOf(sequence));
        Random objGenerator = new Random();
        SimpleDateFormat formatter = null;
        Date dates = null;
        String now = CreateFilesFromDate;
        String outputText = null;

        outerLoop:
        {
            if (CreateFilesFromDate.equals("")) {
                formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                dates = new Date(System.currentTimeMillis());
            } else {
                DateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

                String inputText = now;
                Date date1 = inputFormat.parse(inputText);
                outputText = outputFormat.format(date1);

            }
        }

        {

            int minRange = 100, maxRange = 999999;
            int sequencenumber =
                    objGenerator.nextInt(maxRange - minRange) + minRange;

            sequence = Integer.parseInt(Integer.toString(sequencenumber));
        }
        if (sequence > c) {
            if (sequence <= 999999) {
                if (CreateFilesFromDate.equals("")) {
                    FileNameCms = ("tcm-" + three + "-" + four + "-" + page + "_" + formatter.format(dates) + "_" + sequence + ".");

                } else {
                    FileNameCms = ("tcm-" + three + "-" + four + "-" + page + "_" + outputText + "_" + sequence + ".");

                }
                duplicate = FileNameCms;
            }
        } else {
            Cms_FileName_Sequence();

        }
        return FileNameCms;

    }

    public static String PublishActionType() {
        int totalLines = 0;
        File publishName = new File("PublishActionType.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(publishName));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader(publishName));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String CmsPublishActionType;
            while ((CmsPublishActionType = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return CmsPublishActionType;

                }
                count++;
            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + publishName.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + publishName.toString());
        }

        return "Exit";

    }


    public static File readFileForMultiMedia() {
        Random rand = new Random();
        File[] files = new File("./MultiMediaFiles").listFiles();
        file = files[rand.nextInt(files.length)];
        return file;
    }

    public static String createAllFiles() throws IOException, ParseException {
        int totalLines = 0;
     File cmsname =   readFileForMultiMedia();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader((cmsname)));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader((cmsname)));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String Cmsname;
            while ((Cmsname = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return Cmsname;

                }
                count++;

            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + cmsname);
        } catch (IOException e) {
            System.out.println("Unable to read file: " + cmsname);
        }

        return "Exit";

    }



    public static String createVideosComponentFiles() throws IOException, ParseException {
        int totalLines = 0;
        File cmsname = new File("./MultimediaFiles/Videos.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(cmsname));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader(cmsname));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String Cmsname;
            while ((Cmsname = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return Cmsname;

                }
                count++;

            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + cmsname.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + cmsname.toString());
        }

        return "Exit";

    }

    public static String createPdfComponentFiles() throws IOException, ParseException {
        int totalLines = 0;
        File cmsname = new File("./MultimediaFiles/pdf.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(cmsname));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader(cmsname));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String Cmsname;
            while ((Cmsname = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return Cmsname;

                }
                count++;

            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + cmsname.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + cmsname.toString());
        }

        return "Exit";

    }


    public static String createAudioComponentFiles() throws IOException, ParseException {
        int totalLines = 0;
        File cmsname = new File("./MultimediaFiles/Audio.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(cmsname));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader(cmsname));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String Cmsname;
            while ((Cmsname = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return Cmsname;

                }
                count++;

            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + cmsname.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + cmsname.toString());
        }

        return "Exit";

    }

    public static String createImageComponentFiles() throws IOException, ParseException {
        int totalLines = 0;
        File cmsname = new File("./MultimediaFiles/Image.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(cmsname));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader(cmsname));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String Cmsname;
            while ((Cmsname = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return Cmsname;

                }
                count++;

            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + cmsname.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + cmsname.toString());
        }

        return "Exit";

    }


    public static String CmsGetName() throws IOException, ParseException {
        int totalLines = 0;
        File cmsname = new File("cmsname.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(cmsname));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();
            br = new BufferedReader(new FileReader(cmsname));
            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String Cmsname;
            while ((Cmsname = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();

                    return Cmsname;

                }
                count++;
            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + cmsname.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + cmsname.toString());
        }

        return "Exit";

    }

    public static String CmsLocation() throws IOException, ParseException {
        int totalLines = 0;
        File file = new File("Location.txt");

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));

            while ((br.readLine()) != null) {
                totalLines++;
            }
            br.close();

            br = new BufferedReader(new FileReader(file));

            Random random = new Random();
            int randomInt = random.nextInt(totalLines);
            int count = 0;
            String CmsLocation;
            while ((CmsLocation = br.readLine()) != null) {
                if (count == randomInt) {
                    br.close();
                    return CmsLocation;
                }
                count++;
            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + file.toString());
        }

        return "Exit";

    }


    public static String CmsVersion() throws IOException, ParseException {

        Random objGenerator = new Random();

        String version;
        int extension = 0;
        int minRange = 1, maxRange = 99;
        int randomNumber = objGenerator.nextInt(maxRange - minRange) + minRange;
        version = Integer.toString(randomNumber);

        String Version = (version + "." + extension);

        return Version;


    }

    public static String getBatch() throws IOException, ParseException, java.text.ParseException, JSONException {

        String result = null;
        switch (operator) {
            case '1':
                result = "Batch1";
                break;
            case '2':
                result = "Batch2";
                break;
            case '3':
                result = "Batch3";
                break;
            case '4':
                result = "Batch4";
                break;
            case '5':
                result = "Batch5";
                break;
            case '6':
                result = "Batch6";
                break;
            case '7':
                result = "Batch7";
                break;
            case '8':
                result = "Batch8";
                break;
            case '9':
                result = "Batch9";
                break;
            case '0':
                result = "Batch10";
                break;
            case 'A':
                result = "Batch10";
                break;
            case 'B':
                result = "Batch11";
                break;
            case 'C':
                result = "Batch12";
                break;
            case 'D':
                result = "Batch13";
                break;
            case 'E':
                result = "Batch14";
                break;
            default:
                System.out.println("Invalid operator!");
                break;
        }
        return result;
    }



    public static File createBatchNamesFor_Batch7() throws IOException, ParseException, java.text.ParseException, JSONException {
        if (NumberOfRandomFiles != "null") {
            dir = new File(batchPath + getBatch()+ "\\" + getBatchName() + "\\" + "\\Cms_files\\");
        }
        if (NumberOfSequencesFiles != "null") {
            dir = new File(batchPath + "\\" + "Batch2\\Cms_files\\");
        }

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }




    public static String getBatchName() throws IOException, ParseException, java.text.ParseException, JSONException {

        String result = null;
        switch (operator) {
            case '1':
                result = "RandomFiles";
                break;
            case '2':
                result = "Sequence_files";
                break;
            case '3':
                result = "Overwrite_files";
                break;
            case '4':
                result = "Missing_Cms";
                break;
            case '5':
                result = "O_kb";
                break;
            case '6':
                result = "MissingOutput";
                break;
            case '7':
                result = "FilesInDiffBatch";
                break;
            case '8':
                result = "AllBatchesInOneFile";
                break;
            case '9':
                result = "WithoutItemUri";
                break;
            case '0':
                result = "MultimediaAllFile";
                break;
            case 'A':
                result = "MultimediaAllFile";
                break;
            case 'B':
                result = "ImageFiles";
                break;
            case 'C':
                result = "PdfFiles";
                break;
            case 'D':
                result = "AudioFiles";
                break;
            case 'E':
                result = "VideoFiles";
                break;

            default:
                System.out.println("Invalid operator!");
                break;
        }
        return result;
    }

    public static File createCmsFilePath() throws IOException, ParseException, java.text.ParseException, JSONException {

        dir = new File(batchPath + getBatch()+ "\\" + getBatchName() + "\\Cms_files\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }
    public static File createCmsFilePath_ForBatch3() throws IOException, ParseException, java.text.ParseException, JSONException {

        dir = new File(batchPath + getBatch()+ "\\"  + getBatchName() );
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }



    public static File createOutputFilePath() throws IOException, ParseException, java.text.ParseException, JSONException {
        dir = new File(batchPath + getBatch()+ "\\" + getBatchName() + "\\Output_files\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }




    }
