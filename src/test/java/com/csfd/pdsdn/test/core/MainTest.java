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

/**
 * @author shic
 *
 */
public class MainTest {

   /**
    * @param args
    * @throws FloodlightModuleException
    */
   public static void main(String[] args) throws FloodlightModuleException {
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
      IFloodlightModuleContext moduleContext = fml.loadModulesFromConfig(settings.getModuleFile());
      // Run REST server
      IRestApiService restApi = moduleContext.getServiceImpl(IRestApiService.class);
      restApi.run();
      // Run the main floodlight module
      IFloodlightProviderService controller = moduleContext.getServiceImpl(IFloodlightProviderService.class);
      // This call blocks, it has to be the last line in the main
      controller.run();
   }

}
