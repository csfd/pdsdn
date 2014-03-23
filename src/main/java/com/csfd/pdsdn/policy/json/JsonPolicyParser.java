package com.csfd.pdsdn.policy.json;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.auth.policy.Statement;
import com.csfd.pdsdn.aws.extender.SIPrincipal;
import com.csfd.pdsdn.aws.extender.SIResource;
import com.csfd.pdsdn.aws.extender.SIStatement;
import com.csfd.pdsdn.aws.impl.SDNAction;

public class JsonPolicyParser {
   protected JSONObject jsonObject;
   protected SIStatement sistatement;

   public JsonPolicyParser(JsonLoader jl) {
      this.jsonObject = jl.getJsonObject();
      parsePolicy();
   }

   private void parsePolicy() {
      JSONObject jsonStatement = jsonObject.getJSONObject("SIStatement");
      String effect = jsonStatement.getString("Effect");
      if (effect.equals("Allow"))
         sistatement = new SIStatement(Statement.Effect.Allow);
      else
         sistatement = new SIStatement(Statement.Effect.Deny);
      JSONObject jsonPricipal = jsonStatement.getJSONObject("Principal");
      sistatement.setSiprincipal(new SIPrincipal("SDN", jsonPricipal.getString("SDN")));
      sistatement.setAction(new SDNAction(jsonStatement.getString("Action")));
      JSONObject jsonResource = jsonStatement.getJSONObject("Resource");
      HashMap<String, Object> resourceMap = new HashMap<String, Object>();
      JSONArray ja = jsonResource.names();
      for (int i = 0; i < ja.length(); i++) {
         String name = ja.getString(i);
         resourceMap.put(name, jsonResource.get(name));
      }
      sistatement.setResource(new SIResource(resourceMap));
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

   /**
    * @return the sistatement
    */
   public SIStatement getSistatement() {
      return sistatement;
   }

   /**
    * @param sistatement
    *           the sistatement to set
    */
   public void setSistatement(SIStatement sistatement) {
      this.sistatement = sistatement;
   }



}