package org.openskye;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.openskye.TemplateJsonWriter.*;
import static org.openskye.TemplateJsonWriter.setitemUriNull;
import static org.openskye.Utility.*;


@Slf4j
public class TemplateJsonReader {
    @Getter
    @Setter
    public File SOURCE_JSON_FILE;
    @Getter
    @Setter
    public File DESTINATION_JSON_FILE;
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
        } else {
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
            if ((operator) == '9') {
                innerJsonObject.put("ItemUri", setitemUriNull);
            } else {
                innerJsonObject.put("ItemUri", setitemUri(DESTINATION_JSON_FILE.getName()) + "-" + page);
            }
        } else {
            if ((operator) == '9') {
                innerJsonObject.put("ItemUri", setitemUriNull);
            } else {
                innerJsonObject.put("ItemUri", setitemUri(DESTINATION_JSON_FILE.getName()));
            }
        }

        innerJsonObject.put("Version", CmsVersion);
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
                    for (int i = 1; i <= componentJsonArray.length(); i++) {

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
                        systemMetadataJson.put("Location", ComponentLocation);


                        componentJsonObject.put("Component", compoJson);


                        if (i == Integer.parseInt(NoOfComponentCreate)) {
                            cmpJsonArry.put(componentJsonObject);

                        } else {
                            componentJsonObject.remove("Component");
                            componentJsonObject.remove("ComponentTemplate");


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
        File[] files = new File(TemplateJsonWriter.SourcePaths).listFiles();
        file = files[rand.nextInt(files.length)];
        return file;
    }

    static File getCmsFilesFromDestination() throws IOException, ParseException, java.text.ParseException, JSONException {
        Random rand = new Random();
        File[] files = new File(CmsFileFromBatch1).listFiles();
        File file = files[rand.nextInt(files.length)];
        return file;
    }
}
