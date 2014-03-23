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
   HashMap<String, Object> resourceMap;

   /**
    * @param resource
    * @param resourceMap
    */
   public SIResource(HashMap<String, Object> resourceMap) {
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
   public HashMap<String, Object> getResourceMap() {
      return resourceMap;
   }

   /**
    * @param resourceMap
    *           the resourceMap to set
    */
   public void setResourceMap(HashMap<String, Object> resourceMap) {
      this.resourceMap = resourceMap;
   }

}
