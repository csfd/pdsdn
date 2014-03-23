/**
 * 
 */
package com.csfd.pdsdn.test.execute;

import net.floodlightcontroller.core.module.FloodlightModuleException;

import com.csfd.pdsdn.execute.Executor;
import com.csfd.pdsdn.policy.json.JsonLoader;
import com.csfd.pdsdn.test.core.Controller;

/**
 * @author shic
 *
 */
public class ExecutorTest {
   /**
    * @param args
    * @throws FloodlightModuleException
    */
   public static void main(String[] args) throws FloodlightModuleException {
      Controller con = new Controller(args);
      Thread conThread = new Thread(con);
      conThread.start();
      JsonLoader jl = new JsonLoader(
         "TenantUser-93c6695a-c6a9-42e9-8d9c-f7db1738fcbd-Subnet-939f04dd-49bb-4793-b4a5-d9d6eb714e99.json");
      Executor exe = new Executor(jl);
      exe.run();
   }

}
