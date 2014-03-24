/**
 * 
 */
package com.csfd.pdsdn.execute;

import net.floodlightcontroller.core.module.FloodlightModuleException;

/**
 * @author shic
 *
 */
public interface TaskExecutor {

   public void execute() throws FloodlightModuleException;

   void parseResource();
}
