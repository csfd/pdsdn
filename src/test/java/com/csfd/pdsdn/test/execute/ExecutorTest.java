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
      JsonLoader jl = new JsonLoader("user1.json");
      Executor exe = new Executor(jl);
      exe.run();
   }

}
