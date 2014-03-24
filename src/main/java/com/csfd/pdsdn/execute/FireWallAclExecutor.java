/**
 * 
 */
package com.csfd.pdsdn.execute;

import net.floodlightcontroller.firewall.Firewall;
import net.floodlightcontroller.firewall.FirewallRulesResource;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.resource.ServerResource;

import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModuleContext;

import com.csfd.pdsdn.aws.extender.SIResource;
import com.csfd.pdsdn.helper.GlobalHelper;

/**
 * @author shic
 *
 */
public class FireWallAclExecutor extends ServerResource implements TaskExecutor {
   private String userName;
   private SIResource siresource;
   private JSONArray ja;

   /**
    * @param userName
    * @param siresource
    */
   public FireWallAclExecutor(String userName, SIResource siresource) {
      this.userName = userName;
      this.siresource = siresource;
   }

   /*
    * (non-Javadoc)
    * @see com.csfd.pdsdn.execute.TaskExecutor#execute()
    */
   @Override
   public void execute() throws FloodlightModuleException {
      parseResource();
      Firewall firewall = new Firewall();

      IFloodlightModuleContext moduleContext = GlobalHelper.getModuleContext();
      firewall.init((FloodlightModuleContext) moduleContext);
      firewall.startUp((FloodlightModuleContext) moduleContext);

      for (int i = 0; i < ja.length(); i++) {
         try {
            firewall.addRule(FirewallRulesResource.jsonToFirewallRule(ja.getJSONObject(i).toString()));
         } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

      System.out.println("Firewall created for " + userName);
   }

   /*
    * (non-Javadoc)
    * @see com.csfd.pdsdn.execute.TaskExecutor#parseResource()
    */
   @Override
   public void parseResource() {
      HashMap<String, Object> hm = siresource.getResourceMap();
      ja = (JSONArray) hm.get("rules");
   }

}
