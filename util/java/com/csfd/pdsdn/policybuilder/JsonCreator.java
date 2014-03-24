/**
 * 
 */
package com.csfd.pdsdn.policybuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import net.floodlightcontroller.util.MACAddress;

import org.json.JSONArray;
import org.json.JSONObject;

import com.csfd.pdsdn.helper.GlobalHelper;

/**
 * @author shic
 *
 */
public class JsonCreator {
   private JSONObject sistatement;
   private HashMap<String, Object> hm;
   private Properties property;
   private FileInputStream inputFile;
   private JsonBuilder jsonBuilder;
   private final String BASE_DIR = "fsdb/";

   public JsonCreator() {
      property = new Properties();

   }

   public void vnfcreate() {
      try {
         inputFile = new FileInputStream("user.properties");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      try {
         property.load(inputFile);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      String Effect = property.getProperty("Effect");
      int TenantNum = Integer.parseInt(property.getProperty("TenantNum"));
      int NetworkNum = Integer.parseInt(property.getProperty("NetworkNum"));
      int hostsNum = Integer.parseInt(property.getProperty("hostsNum"));
      String action = property.getProperty("Action");
      String TenantPrefix = property.getProperty("TenantPrefix");
      String NetworkPrefix = property.getProperty("NetworkPrefix");
      for (int i=0; i<TenantNum;i++) {
         String userGuid = UUID.randomUUID().toString();
         String tenantName = TenantPrefix + userGuid;
         for (int j = 0; j < NetworkNum; j++) {
            hm = new HashMap<String, Object>();
            String networkName = NetworkPrefix + UUID.randomUUID().toString();
            hm.put("Effect", Effect);
            hm.put("Principal", new JSONObject("{\"SDN\":\"" + tenantName + "\"}"));
            hm.put("Action", action);
            HashMap<String, Object> resourceValue = new HashMap<String, Object>();
            resourceValue.put("guid", userGuid);
            resourceValue.put("networkName", networkName);
            resourceValue.put("gateway", "192.168." + (i % 253 + 1) + "." + (j % 253 + 1));
            JSONArray macArray = new JSONArray();
            for (int k = 0; k < hostsNum; k++) {
               MACAddress macAddress = getMac();
               macArray.put(macAddress.toString());
            }
            resourceValue.put("hosts", macArray);
            JSONObject resourceObject = new JSONObject(resourceValue);
            hm.put("Resource", resourceObject);
            sistatement = new JSONObject(hm);
            jsonBuilder = new JsonBuilder();
            jsonBuilder.generator(sistatement);
            writeToFile(jsonBuilder.getJsonString(), tenantName + "-" + networkName + ".json");
         }
      }

   }

   public void fwcreate() {
      try {
         inputFile = new FileInputStream("user.properties");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      try {
         property.load(inputFile);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      String Effect = property.getProperty("Effect");
      int TenantNum = Integer.parseInt(property.getProperty("TenantNum"));
      int hostsNum = Integer.parseInt(property.getProperty("hostsNum"));
      String action = property.getProperty("Action");
      String TenantPrefix = property.getProperty("TenantPrefix");
      for (int i = 0; i < TenantNum; i++) {
         String userGuid = UUID.randomUUID().toString();
         String tenantName = TenantPrefix + userGuid;
         hm = new HashMap<String, Object>();
         hm.put("Effect", Effect);
         hm.put("Principal", new JSONObject("{\"SDN\":\"" + tenantName + "\"}"));
         hm.put("Action", action);
         HashMap<String, JSONArray> resourceValue = new HashMap<String, JSONArray>();
         JSONArray macArray = new JSONArray();
         for (int k = 0; k < hostsNum; k++) {
            MACAddress macAddress = getMac();
            macArray.put(macAddress.toString());
         }

         JSONArray ruleSet = new JSONArray();
         for (int k = 0; k < hostsNum; k++) {
            Random random = new Random();
            for (int r = 0; r < 5; r++) {
               JSONObject oneRule = new JSONObject();
               oneRule.put("src-mac", macArray.get(k));
               oneRule.put("dst-mac", macArray.get(random.nextInt(hostsNum)));
               oneRule.put("nw-proto", "TCP");
               oneRule.put("tp-dst", Integer.toString(GlobalHelper.getPort()));
               oneRule.put("action", (random.nextInt() % 2 == 0) ? "ALLOW" : "DENY");
               ruleSet.put(oneRule);
            }

         }
         resourceValue.put("rules", ruleSet);

         JSONObject resourceObject = new JSONObject(resourceValue);
         hm.put("Resource", resourceObject);
         sistatement = new JSONObject(hm);
         jsonBuilder = new JsonBuilder();
         jsonBuilder.generator(sistatement);
         writeToFile(jsonBuilder.getJsonString(), tenantName + "-ACL.json");
      }

   }

   private void writeToFile(String jsonString, String fileName) {
      jsonString.replaceAll(",", ",\\n");
      BufferedWriter bufferedWriter = null;
      BufferedReader bufferedReader = null;
      try {
         File distFile = new File(BASE_DIR + fileName);
         bufferedWriter = new BufferedWriter(new FileWriter(distFile));
         bufferedReader = new BufferedReader(new StringReader(jsonString));
         char buf[] = new char[1024];
         int len;
         while ((len = bufferedReader.read(buf)) != -1) {
            bufferedWriter.write(buf, 0, len);
         }
         bufferedWriter.flush();
         bufferedReader.close();
         bufferedWriter.close();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         if (bufferedReader != null) {
            try {
               bufferedReader.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
   }
   private MACAddress getMac() {
      byte[] address = new byte[6];
      Random rand = new Random();
      rand.nextBytes(address);
      MACAddress macAddress = new MACAddress(address);
      return macAddress;
   }
   public static void main(String[] args) {
      JsonCreator jc = new JsonCreator();
      jc.fwcreate();
   }
}
