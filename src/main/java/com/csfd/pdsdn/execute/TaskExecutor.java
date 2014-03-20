/**
 * 
 */
package com.csfd.pdsdn.execute;

import net.floodlightcontroller.core.module.FloodlightModuleException;

import com.csfd.pdsdn.aws.extender.SIResource;

/**
 * @author shic
 *
 */
public abstract class TaskExecutor {
   String userName;
   SIResource siresource;

   /**
    * @param userName
    * @param resource
    */
   public TaskExecutor(String userName, SIResource siresource) {
      super();
      this.userName = userName;
      this.siresource = siresource;
   }

   public void execute() throws FloodlightModuleException {
   }

   protected void parseResource() {

   }
}
