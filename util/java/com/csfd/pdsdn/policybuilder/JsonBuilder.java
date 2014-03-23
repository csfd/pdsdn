/**
 * 
 */
package com.csfd.pdsdn.policybuilder;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author shic
 *
 */
public class JsonBuilder {
   private String jsonString;
   private JSONObject jsonObject;
   private HashMap<String, Object> hm;
   private static final String DEFAULT_VERSTION = "2012-10-17";

   public JsonBuilder() {
      super();
      this.hm = new HashMap<String, Object>();
   }

   public void generator(JSONObject sistatement) {
      hm.put("Version", DEFAULT_VERSTION);
      hm.put("SIStatement", sistatement);
      jsonObject = new JSONObject(hm);
      jsonString = jsonObject.toString();
   }
   /**
    * @return the jsonString
    */
   public String getJsonString() {
      return jsonString;
   }

   /**
    * @param jsonString the jsonString to set
    */
   public void setJsonString(String jsonString) {
      this.jsonString = jsonString;
   }

   /**
    * @return the jsonObject
    */
   public JSONObject getJsonObject() {
      return jsonObject;
   }

   /**
    * @param jsonObject the jsonObject to set
    */
   public void setJsonObject(JSONObject jsonObject) {
      this.jsonObject = jsonObject;
   }
}
