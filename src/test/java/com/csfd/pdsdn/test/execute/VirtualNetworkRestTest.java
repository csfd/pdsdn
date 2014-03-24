/**
 * 
 */
package com.csfd.pdsdn.test.execute;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONObject;

import net.floodlightcontroller.core.module.FloodlightModuleException;

import com.csfd.pdsdn.helper.GlobalHelper;
import com.csfd.pdsdn.monitor.MonitorLoad;
import com.csfd.pdsdn.policy.json.JsonLoader;
import com.csfd.pdsdn.test.core.Controller;
import com.csfd.pdsdn.test.core.RestClient;

/**
 * @author shic
 *
 */
public class VirtualNetworkRestTest {
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
         String tenant = policy.getJSONObject("SIStatement").getJSONObject("Principal").getString("SDN");
         String networkName = policy.getJSONObject("SIStatement").getJSONObject("Resource").getString("networkName");
         JSONArray hosts = policy.getJSONObject("SIStatement").getJSONObject("Resource").getJSONArray("hosts");
         String method = "PUT";
         String path = "/networkService/v1.1/tenants/" + tenant + "/networks/" + networkName;
         String data = "{\"network\":{\"name\":\"virtualNetwork1\"}}";
         RestClient rc = new RestClient(method, path, data);
         Future<String> future = executorService.submit(rc);
         try {
            if (future.get() == "RESPONSE_OK") {
               for (int j = 0; j < hosts.length(); j++) {
                  String subpath = path + "/ports/port" + j + "/attachment";
                  String subdata = "{\"attachment\":{\"id\":\"" + networkName + "\",\"mac\":\"" + hosts.getString(j)
                     + "\"}}";
                  RestClient subrc = new RestClient(method, subpath, subdata);
                  executorService.submit(subrc);
               }
            }
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

      try {
         Thread.sleep(60 * 1000);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      System.out.print(GlobalHelper.getTaskEndTime() - GlobalHelper.getTaskStartTime());
      GlobalHelper.monitor = false;
   }
}
