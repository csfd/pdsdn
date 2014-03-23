/**
 * 
 */
package com.csfd.pdsdn.test.core;

import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.internal.CmdLineSettings;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.FloodlightModuleLoader;
import net.floodlightcontroller.core.module.IFloodlightModuleContext;
import net.floodlightcontroller.restserver.IRestApiService;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.csfd.pdsdn.helper.GlobalHelper;

/**
 * @author shic
 *
 */
public class Controller implements Runnable {
   /**
    * @param args
    */
   public Controller(String[] args) {
      this.args = args;
   }

   String[] args;

   /*
    * (non-Javadoc)
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run() {
      CmdLineSettings settings = new CmdLineSettings();
      CmdLineParser parser = new CmdLineParser(settings);
      try {
         parser.parseArgument(args);
      } catch (CmdLineException e) {
         parser.printUsage(System.out);
         System.exit(1);
      }

      // Load modules
      FloodlightModuleLoader fml = new FloodlightModuleLoader();
      IFloodlightModuleContext moduleContext = null;
      try {
         moduleContext = fml.loadModulesFromConfig("floodlight/src/main/resources/vn.properties");
         GlobalHelper.setModuleContext(moduleContext);
      } catch (FloodlightModuleException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      // Run REST server
      IRestApiService restApi = moduleContext.getServiceImpl(IRestApiService.class);
      restApi.run();
      // Run the main floodlight module
      IFloodlightProviderService controller = moduleContext.getServiceImpl(IFloodlightProviderService.class);
      // This call blocks, it has to be the last line in the main
      controller.run();

   }

}
