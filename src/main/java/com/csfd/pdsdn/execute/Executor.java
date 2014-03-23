/**
 * 
 */
package com.csfd.pdsdn.execute;

import net.floodlightcontroller.core.module.FloodlightModuleException;

import com.amazonaws.auth.policy.Statement;
import com.csfd.pdsdn.policy.json.JsonLoader;
import com.csfd.pdsdn.policy.json.JsonPolicyParser;
import com.csfd.pdsdn.aws.extender.SIPrincipal;
import com.csfd.pdsdn.aws.extender.SIResource;
import com.csfd.pdsdn.aws.extender.SIStatement;
import com.csfd.pdsdn.aws.impl.SDNAction;
import com.csfd.pdsdn.helper.ActionMethods;

/**
 * @author shic
 *
 */
public class Executor {
   private JsonLoader jsonLoader;

   public Executor(JsonLoader jl) {
      jsonLoader = jl;
   }

   public void run() {
      JsonPolicyParser jpp = new JsonPolicyParser(jsonLoader);
      SIStatement sistatement = jpp.getSistatement();
      if (sistatement.getEffect() == Statement.Effect.Allow) {
         runSIStatement(sistatement);
      }

   }

   private boolean runSIStatement(SIStatement sistatement) {
      SIPrincipal principal = sistatement.getSiprincipal();
      SDNAction action = sistatement.getAction();
      SIResource resource = sistatement.getResource();
      TaskExecutor taskExecutor = null;

      switch (action.getActionName()) {
      case ActionMethods.CREATE_VIRTUAL_NETWORK: {
         taskExecutor = new VirtualNetworkExecutor(principal.getId(), resource);
      }
      }
      try {
         taskExecutor.execute();
      } catch (FloodlightModuleException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return true;

   }
}
