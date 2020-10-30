package org.openskye;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static org.openskye.TemplateJsonWriter.*;
import static org.openskye.TemplateJsonWriter.createVideosComponentFiles;
import static org.openskye.TemplateJsonWriter.setitemUriNull;
import static org.openskye.Utility.*;


@Slf4j
public class TemplateJsonReader {
    @Getter
    @Setter
    public static File SOURCE_JSON_FILE;
    @Getter
    @Setter
    public File DESTINATION_JSON_FILE;
    Map<String, Integer> mapOfRepeatedWordVersion = new HashMap<String, Integer>();
    public static String CmsGetName;
    public static String CmsVersion;
    public static String CmsCreatedDateTimes;
    public static String CmsLocation;
    public static String TCMID;
    public static String setitemUriNull;
    public static String ComponentName;
    public static String ComponentId;
    public static String ComponentLocation;
    public static String ComponentVersion;
    public static String BundleId;
    public static String BundleName;
    public static String BundleApprovalId;
    public static File file;
    public static String COMPONENTID;
    public static String VERSION;
    public static String tcmid;
    public static String readComponent;
    public static File MultimediaFile ;
//    public static ArrayList<String> ComponentStatisticsCount;
  //  public static JSONArray NewJsonArray ;

    public JsonFileTemplate getSourceFromInputFile() throws IOException, ParseException, JSONException, java.text.ParseException {

        JsonFileTemplate jsonFileTemplate = new JsonFileTemplate(SOURCE_JSON_FILE);
        JSONObject multimediaJsonObject = jsonFileTemplate.getJsonObject();
        CmsGetName = CmsGetName();
        CmsVersion = CmsVersion();
        //  picksrc();
    /*    if(page.equalsIgnoreCase(String.valueOf(16))) {
            TCMID = setJSONFileName(DESTINATION_JSON_FILE.getName())  + "-" + "v" + CmsVersion;
            multimediaJsonObject.put("Id", TCMID);
        }
        else
        {
            TCMID = setJSONFileName(DESTINATION_JSON_FILE.getName()) + "-" + page + "-" + "v" + CmsVersion;
            multimediaJsonObject.put("Id", TCMID);
        }*/
        TCMID = setitemUri(DESTINATION_JSON_FILE.getName());

        setitemUriNull = setitemUriNull((DESTINATION_JSON_FILE.getName()));
        if (page.equalsIgnoreCase(String.valueOf(64))) {
            String tcmid = TCMID + "-" + page + "-" + "v" + CmsVersion;
            multimediaJsonObject.put("Id", tcmid);
        } else if((operator) == 'A' || (operator) == 'B' || (operator) == 'C' || (operator) == 'D' || (operator) == 'E' ) {
            String tcmid = TCMID + "-" + "16" + "-" + "v" + getVersionForComponentFile();
            multimediaJsonObject.put("Id", tcmid);
        }
        else {
            String tcmid = TCMID + "-" + "v" + CmsVersion;
            multimediaJsonObject.put("Id", tcmid);
        }
        multimediaJsonObject.put("Name", CmsGetName);
        if (page.equalsIgnoreCase(String.valueOf(64))) {
            multimediaJsonObject.put("FileName", CmsGetName);
        }

        JSONObject innerJsonObject = multimediaJsonObject.getJSONObject("SystemMetadata");
        SystemMetadata SystemMetadatas = new SystemMetadata();
        if (CreateFilesFromDate.equals("")) {
            CmsCreatedDateTimes = createCmsFile_CurrentDate();
        } else {
            CmsCreatedDateTimes = createCmsFileDate_Flag();
        }

        CmsLocation = CmsLocation();
        innerJsonObject.put("Name", CmsGetName);
        innerJsonObject.put("Location", CmsLocation());

        if (page.equalsIgnoreCase(String.valueOf(64))) {
            if ((operator) == '9' || (operator) == '7' && directory.contains("batch7")) {
                innerJsonObject.remove("ItemUri");
            }
         else {
                innerJsonObject.put("ItemUri", setitemUri(DESTINATION_JSON_FILE.getName()) + "-" + page);
            }
        }
        else {
              if((operator) == 'A' || (operator) == 'B' || (operator) == 'C' || (operator) == 'D' || (operator) == 'E' ) {
                innerJsonObject.put("ItemUri", getItemUriForComponentFile());

            }
              else if ((operator) == '9' || directory.contains("batch7")) {
                innerJsonObject.remove("ItemUri");
            }

            else {
                innerJsonObject.put("ItemUri", setitemUri(DESTINATION_JSON_FILE.getName()));
            }
        }

        if((operator) == 'A' || (operator) == 'B' || (operator) == 'C' || (operator) == 'D' || (operator) == 'E' ) {
            innerJsonObject.put("Version", getVersionForComponentFile());
        }
        else {
            innerJsonObject.put("Version", CmsVersion);
        }
        if (CreateFilesFromDate.equals("")) {
            innerJsonObject.put("CreatedDateTime", createCmsFile_CurrentDate());
        } else {
            innerJsonObject.put("CreatedDateTime", createCmsFileDate_Flag());
        }
        multimediaJsonObject.put("SystemMetadata", innerJsonObject);


        BundleId = Create_BundleId();
        BundleName = CmsGetName;
        JSONObject innerJsonObjectBundle = multimediaJsonObject.getJSONObject("Bundle");

        innerJsonObjectBundle.put("Id", BundleId);
        innerJsonObjectBundle.put("Name", BundleName);

        BundleApprovalId = Create_ApprovalId();
        JSONObject Metadatajson = innerJsonObjectBundle.getJSONObject("MetaData");
        Metadatajson.put("Approval_Tracking_Id", BundleApprovalId);

        JSONObject SystemMetaDataJson = innerJsonObjectBundle.getJSONObject("SystemMetadata");
        if ((operator) == '9') {
            SystemMetaDataJson.put("ItemUri", setitemUriNull);
        } else {
            SystemMetaDataJson.put("ItemUri", BundleId);
        }
        SystemMetaDataJson.put("Name", BundleName);

        JSONObject innerJsonObjects = multimediaJsonObject.getJSONObject("PublishTransaction");
        if (CreateFilesFromDate.equals("")) {
            innerJsonObjects.put("PublishDateTime", createCmsFile_CurrentDate());
        } else {
            innerJsonObjects.put("PublishDateTime", createCmsFileDate_Flag());
        }
        if ((operator) == '5') {
            innerJsonObjects.put("PublishActionType", PublishActionType());
        }
        List<String> componentTypeList = new ArrayList<>();
        componentTypeList.add("ComponentPresentations");
        componentTypeList.add("Metadata_ReferencedComponents");
        componentTypeList.add("ReferencedComponents");
        for (String componentType : componentTypeList) {
            try {

                if (multimediaJsonObject.getJSONArray(componentType) != null) {
                    JSONArray componentJsonArray = multimediaJsonObject.getJSONArray(componentType);
                    ComponentPresentations cp = new ComponentPresentations();
                    ArrayList<String> list = new ArrayList<String>();
                    JSONArray cmpJsonArry = new JSONArray();
                    for (int i = 0; i < componentJsonArray.length(); i++) {
                        if (page.equalsIgnoreCase(String.valueOf(64))) {
                            String ItemUri = setcomponent();
                            ComponentVersion = CmsVersion();
                            ComponentId = ItemUri + "-" + "v" + ComponentVersion;
                            ComponentLocation = CmsLocation();
                            ComponentName = CmsGetName();

                        JSONObject componentJsonObject = componentJsonArray.getJSONObject(i);
                        JSONObject compoJson = componentJsonObject.getJSONObject("Component");

                        compoJson.put("Id", ComponentId);

                        compoJson.put("Name", ComponentName);
                        JSONObject systemMetadataJson = compoJson.getJSONObject("SystemMetadata");

                        systemMetadataJson.put("Name", ComponentName);
                        if ((operator) == '9') {
                            systemMetadataJson.put("ItemUri", setitemUriNull);
                        } else {
                            systemMetadataJson.put("ItemUri", ItemUri);
                        }
                        systemMetadataJson.put("Version", ComponentVersion);
                            if ((operator) == '0' && page.equalsIgnoreCase(String.valueOf(64))) {

                                if (compoJson.get("Schema").equals("Video")) {
                                    MultimediaFile = new File("./MultimediaFiles/Videos.txt");
                                }
                                if (compoJson.get("Schema").equals("Image")) {
                                    MultimediaFile = new File("./MultimediaFiles/Image.txt");
                                }
                                if (compoJson.get("Schema").equals("PDF")) {
                                    MultimediaFile = new File("./MultimediaFiles/Pdf.txt");
                                }
                                if (compoJson.get("Schema").equals("Audio")) {
                                    MultimediaFile = new File("./MultimediaFiles/Audio.txt");
                                }
                                if (compoJson.get("Schema").equals("Video") || compoJson.get("Schema").equals("Image") || compoJson.get("Schema").equals("PDF") || compoJson.get("Schema").equals("Audio") ) {
                                    COMPONENTID = getPagePrefix();
                                    VERSION = ComponentVersion;
                                    tcmid = COMPONENTID + VERSION;
                                    PrintWriter out = new PrintWriter(new FileWriter(MultimediaFile, true));
                                    out.println(tcmid);
                                    out.flush();
                                    out.close();
                                }
                            }


//                                    File akshara = new File("akshita.txt");
//                                    akshara.createNewFile();
//                                    FileWriter fw = new FileWriter(akshara, true);
//
//                                    fw.write("\n" + tcmid + "\t\t" + "Count");



                                 /*   if (componentJsonObject.getJSONObject("Component").get("Content").toString().contains("img xlink:href"))
                                        ;
                                    jsonString.contains("img xlink:href");
                                    //("<img xlink:href=" + ComponentId + ">" + ComponentId + "</img");
                                    compoJson.put("Content", ComponentName);*/


                            systemMetadataJson.put("Location", ComponentLocation);


                        componentJsonObject.put("Component", compoJson);


                        if (i == Integer.parseInt(NoOfComponentCreate)) {
                            cmpJsonArry.put(componentJsonObject);
                            cp.getJsonArray().add(cmpJsonArry);
                         //   NewJsonArray = cmpJsonArry;
                        }
                        cp.getJsonArray().add(cmpJsonArry);
                    }
                }

            } catch (Exception ex) {
                log.info("Embedded components" + ex);
                continue;
            }
        }
        multimediaJsonObject.remove("{}");
        return jsonFileTemplate;

    }

    static File getRandomCmsSourceFile() {
        Random rand = new Random();
        File[] files;
        if( (operator) == '0'){
            files = new File(TemplateJsonWriter.PageMultimediaSourcePath).listFiles();
        }
        else if((operator) == 'A'){
            files = new File(TemplateJsonWriter.MultimediaSourcePath).listFiles();
        }
        else if((operator) == 'B'){
            files = new File(TemplateJsonWriter.ImageSourcePaths).listFiles();
        }
        else if((operator) == 'C'){
            files = new File(TemplateJsonWriter.PdfSourcePaths).listFiles();
        }
        else if((operator) == 'D'){
            files = new File(TemplateJsonWriter.AudioSourcePaths).listFiles();
        }
        else if((operator) == 'E'){
            files = new File(TemplateJsonWriter.VideoSourcePaths).listFiles();
        }
        else {
            files = new File(TemplateJsonWriter.SourcePaths).listFiles();
        }
        file = files[rand.nextInt(files.length)];

             return file;
    }



    static File getRandomCmsSourceFileForImage() {
        Random rand = new Random();
        File[] files = new File(TemplateJsonWriter.ImageSourcePaths).listFiles();
        file = files[rand.nextInt(files.length)];
        return file;
    }

    static File getCmsFilesFromDestination() throws IOException, ParseException, java.text.ParseException, JSONException {
        Random rand = new Random();
        File[] files = new File(CmsFileFromBatch1).listFiles();
        File file = files[rand.nextInt(files.length)];
        return file;
    }
    public static String fileToString(String filePath) throws Exception{
        String input = null;
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input);
        }
        return sb.toString();
    }
    public static String checkComponentFiles() throws IOException, ParseException, JSONException {
        JsonFileTemplate jsonFileTemplate = new JsonFileTemplate(SOURCE_JSON_FILE);
        JSONObject multimediaJsonObject = jsonFileTemplate.getJsonObject();
        String s;
        if (multimediaJsonObject.get("MultimediaType").equals("Video")) {
            s = createVideosComponentFiles();
        } else if (multimediaJsonObject.get("MultimediaType").equals("Image")) {
            s = createImageComponentFiles();
        } else if (multimediaJsonObject.get("MultimediaType").equals("Pdf document")){
            s = createPdfComponentFiles();
        } else {
            s = createAudioComponentFiles();
        }
        return s;
    }

}
