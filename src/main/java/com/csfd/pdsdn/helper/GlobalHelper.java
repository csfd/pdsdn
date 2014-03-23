/**
 * 
 */
package com.csfd.pdsdn.helper;

import net.floodlightcontroller.core.module.IFloodlightModuleContext;

/**
 * @author shic
 *
 */
public class GlobalHelper {
   public static IFloodlightModuleContext moduleContext;
   public static long taskStartTime;
   public static long taskEndTime;

   /**
    * @return the moduleContext
    */
   public static IFloodlightModuleContext getModuleContext() {
      return moduleContext;
   }

   /**
    * @param moduleContext
    *           the moduleContext to set
    */
   public static void setModuleContext(IFloodlightModuleContext moduleContext) {
      GlobalHelper.moduleContext = moduleContext;
   }

   /**
    * @return the taskStartTime
    */
   public static long getTaskStartTime() {
      return taskStartTime;
   }

   /**
    * @return the taskEndTime
    */
   public static long getTaskEndTime() {
      return taskEndTime;
   }

   /**
    * @param taskStartTime
    *           the taskStartTime to set
    */
   public static void setTaskStartTime(long taskStartTime) {
      GlobalHelper.taskStartTime = taskStartTime;
   }

   /**
    * @param taskEndTime
    *           the taskEndTime to set
    */
   public static void setTaskEndTime(long taskEndTime) {
      GlobalHelper.taskEndTime = taskEndTime;
   }

}
