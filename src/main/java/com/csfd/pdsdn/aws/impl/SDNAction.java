/**
 * 
 */
package com.csfd.pdsdn.aws.impl;

import com.amazonaws.auth.policy.Action;

/**
 * @author shic
 *
 */
public class SDNAction implements Action {

   private String actionName;

   /**
    * @param actionName
    */
   public SDNAction(String actionName) {
      this.actionName = actionName;
   }

   /*
    * (non-Javadoc)
    * @see com.amazonaws.auth.policy.Action#getActionName()
    */
   @Override
   public String getActionName() {
      return actionName;
   }

}
