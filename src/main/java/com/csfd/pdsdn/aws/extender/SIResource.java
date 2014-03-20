/**
 * 
 */
package com.csfd.pdsdn.aws.extender;

import java.util.HashMap;

import com.amazonaws.auth.policy.Resource;

/**
 * @author shic
 *
 */
public class SIResource extends Resource {
   HashMap<String, String> resourceMap;

   /**
    * @param resource
    * @param resourceMap
    */
   public SIResource(HashMap<String, String> resourceMap) {
      super(null);
      this.resourceMap = resourceMap;
   }
   /**
    * @param resource
    */
   public SIResource(String resource) {
      super(resource);
      // TODO Auto-generated constructor stub
   }

   /**
    * @return the resourceMap
    */
   public HashMap<String, String> getResourceMap() {
      return resourceMap;
   }

   /**
    * @param resourceMap
    *           the resourceMap to set
    */
   public void setResourceMap(HashMap<String, String> resourceMap) {
      this.resourceMap = resourceMap;
   }

}
