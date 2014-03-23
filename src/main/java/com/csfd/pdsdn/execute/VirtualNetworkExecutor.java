/**
 * 
 */
package com.csfd.pdsdn.execute;

import java.util.HashMap;

import org.json.JSONArray;

import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.FloodlightModuleLoader;
import net.floodlightcontroller.core.module.IFloodlightModuleContext;
import net.floodlightcontroller.util.MACAddress;
import net.floodlightcontroller.virtualnetwork.VirtualNetworkFilter;

import com.csfd.pdsdn.aws.extender.SIResource;
import com.csfd.pdsdn.helper.IPHelper;

/**
 * @author shic
 *
 */
public class VirtualNetworkExecutor extends TaskExecutor {
   private String guid;
   private String networkName;
   private Integer gateway;
   private JSONArray macArray;


   /**
    * @param userName
    * @param resource
    */
   public VirtualNetworkExecutor(String userName, SIResource siresource) {
      super(userName, siresource);
      // TODO Auto-generated constructor stub
   }


   /*
    * (non-Javadoc)
    * @see com.csfd.pdsdn.execute.TaskExecutor#parseResource()
    */
   @Override
   protected void parseResource() {
      HashMap<String, Object> hm = siresource.getResourceMap();
      guid = (String) hm.get("guid");
      networkName = (String) hm.get("networkName");
      String gatewayIpString = (String) hm.get("gateway");
      gateway = new Integer(IPHelper.IpToInt(gatewayIpString));
      macArray = (JSONArray) hm.get("hosts");
   }

   /*
    * (non-Javadoc)
    * @see com.csfd.pdsdn.execute.TaskExecutor#execute()
    */
   @Override
   public void execute() throws FloodlightModuleException {
      parseResource();
      VirtualNetworkFilter vnf = new VirtualNetworkFilter();
      FloodlightModuleLoader fml = new FloodlightModuleLoader();
      IFloodlightModuleContext moduleContext = null;

      try {
         moduleContext = fml.loadModulesFromConfig("floodlight/src/main/resources/vn.properties");
      } catch (FloodlightModuleException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      vnf.init((FloodlightModuleContext) moduleContext);
      vnf.startUp((FloodlightModuleContext) moduleContext);
      vnf.createNetwork(guid, networkName, gateway);

      String portBase = networkName + "-port";
      for (int i = 0; i < macArray.length(); i++) {
         vnf.addHost(MACAddress.valueOf(macArray.getString(i)), guid, portBase + i);
      }

      System.out.println("Network created.");
   }

   /**
    * @return the guid
    */
   public String getGuid() {
      return guid;
   }

   /**
    * @param guid
    *           the guid to set
    */
   public void setGuid(String guid) {
      this.guid = guid;
   }

   /**
    * @return the networkName
    */
   public String getNetworkName() {
      return networkName;
   }

   /**
    * @param networkName
    *           the networkName to set
    */
   public void setNetworkName(String networkName) {
      this.networkName = networkName;
   }

   /**
    * @return the gateway
    */
   public Integer getGateway() {
      return gateway;
   }

   /**
    * @param gateway
    *           the gateway to set
    */
   public void setGateway(Integer gateway) {
      this.gateway = gateway;
   }

}
