package com.csfd.pdsdn.policy.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.csfd.pdsdn.helper.FSDBHelper;

public class JsonLoader {
   private JSONObject jsonObject;

   public JsonLoader(InputStream inputStream) {
      JSONTokener jsonTokener = new JSONTokener(inputStream);
      this.jsonObject = new JSONObject(jsonTokener);
   }

   public JsonLoader(String filename) {
      String filePath = FSDBHelper.ROOT_TATH + filename;
      File file = new File(filePath);
      InputStream is = null;
      try {
         is = new FileInputStream(file);
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      JSONTokener jsonTokener = new JSONTokener(is);
      this.jsonObject = new JSONObject(jsonTokener);
   }

   /**
    * @return the jsonObject
    */
   public JSONObject getJsonObject() {
      return jsonObject;
   }

   /**
    * @param jsonObject
    *           the jsonObject to set
    */
   public void setJsonObject(JSONObject jsonObject) {
      this.jsonObject = jsonObject;
   }
}