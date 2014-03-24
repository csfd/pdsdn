/**
 * 
 */
package com.csfd.pdsdn.test.execute;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.floodlightcontroller.core.module.FloodlightModuleException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.csfd.pdsdn.helper.GlobalHelper;
import com.csfd.pdsdn.monitor.MonitorLoad;
import com.csfd.pdsdn.policy.json.JsonLoader;
import com.csfd.pdsdn.test.core.Controller;
import com.csfd.pdsdn.test.core.RestClient;

/**
 * @author shic
 *
 */
public class FireWallAclRestTest {
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
         JSONObject policy = jl.getJsonObject();
         JSONArray ruleSet = policy.getJSONObject("SIStatement").getJSONObject("Resource").getJSONArray("rules");
         String tenant = policy.getJSONObject("SIStatement").getJSONObject("Principal").getString("SDN");
         String method = "POST";
         String path = "/wm/firewall/rules/json";

         for (int j = 0; j < ruleSet.length(); j++) {
            String data = ruleSet.getJSONObject(j).toString();
            RestClient rc = new RestClient(method, path, data);
            executorService.submit(rc);
         }
      }

      try {
         Thread.sleep(6 * 1000);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      System.out.println(GlobalHelper.getTaskEndTime() - GlobalHelper.getTaskStartTime());
      GlobalHelper.monitor = false;
   }
}
