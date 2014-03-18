package com.csfd.pdsdn.policy.json;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class jsonLoader {
   private JSONObject jsonObject;

   public jsonLoader(InputStream inputStream) {
      JSONTokener jsonTokener = new JSONTokener(inputStream);
      jsonObject = new JSONObject(jsonTokener);
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