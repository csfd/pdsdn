/**
 * 
 */
package com.csfd.pdsdn.aws.extender;

import com.amazonaws.auth.policy.Statement;
import com.csfd.pdsdn.aws.impl.SDNAction;

/**
 * @author shic
 *
 */
public class SIStatement extends Statement {
   private SIPrincipal siprincipal;
   private SDNAction action;
   private SIResource resource;
   /**
    * @param effect
    */
   public SIStatement(Effect effect) {
      super(effect);
      // TODO Auto-generated constructor stub
   }

   /**
    * @return the siprincipal
    */
   public SIPrincipal getSiprincipal() {
      return siprincipal;
   }

   /**
    * @param siprincipal
    *           the siprincipal to set
    */
   public void setSiprincipal(SIPrincipal siprincipal) {
      this.siprincipal = siprincipal;
   }

   /**
    * @return the action
    */
   public SDNAction getAction() {
      return action;
   }

   /**
    * @param action
    *           the action to set
    */
   public void setAction(SDNAction action) {
      this.action = action;
   }

   /**
    * @return the resource
    */
   public SIResource getResource() {
      return resource;
   }

   /**
    * @param resource
    *           the resource to set
    */
   public void setResource(SIResource resource) {
      this.resource = resource;
   }

}
