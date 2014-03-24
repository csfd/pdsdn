/**
 * 
 */
package com.csfd.pdsdn.test.execute;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.floodlightcontroller.core.module.FloodlightModuleException;

import com.csfd.pdsdn.execute.Executor;
import com.csfd.pdsdn.helper.GlobalHelper;
import com.csfd.pdsdn.monitor.MonitorLoad;
import com.csfd.pdsdn.policy.json.JsonLoader;
import com.csfd.pdsdn.test.core.Controller;

/**
 * @author shic
 *
 */
public class ExecutorTest {
   private static final String BASE_DIR = "fsdb/";
   /**
    * @param args
    * @throws FloodlightModuleException
    */
   public static void main(String[] args) throws FloodlightModuleException {
      Controller con = new Controller(args);
      Thread conThread = new Thread(con);
      conThread.start();
      try {
         Thread.sleep(10 * 1000);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      MonitorLoad monitor = new MonitorLoad();
      Thread monitorThread = new Thread(monitor);
      GlobalHelper.monitor = true;
      GlobalHelper.onExecution = false;
      monitorThread.start();
      try {
         Thread.sleep(10 * 1000);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      GlobalHelper.onExecution = true;

      File dir = new File(BASE_DIR);
      File files[] = dir.listFiles();
      ExecutorService executorService = Executors.newFixedThreadPool(GlobalHelper.threadPoolSize);
      GlobalHelper.setTaskStartTime(System.currentTimeMillis());
      GlobalHelper.setTaskEndTime(0);
      // ThreadPoolExecutor threadPool = new ThreadPoolExecutor();
      for (int i = 0; i < files.length; i++) {
         JsonLoader jl = new JsonLoader(files[i].getName());
         Executor exe = new Executor(jl);
         executorService.submit(exe);
      }

      try {
         Thread.sleep(2 * 1000);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      System.out.print(GlobalHelper.getTaskEndTime() - GlobalHelper.getTaskStartTime());
      GlobalHelper.monitor = false;
   }

}
