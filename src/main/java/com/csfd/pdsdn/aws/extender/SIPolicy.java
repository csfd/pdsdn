/**
 * 
 */
package com.csfd.pdsdn.aws.extender;

import java.util.Collection;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Statement;

/**
 * @author shic
 *
 */
public class SIPolicy extends Policy {
   Collection<SIStatement> sistatements;
   /**
    * 
    */
   public SIPolicy() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @param id
    */
   public SIPolicy(String id) {
      super(id);
      // TODO Auto-generated constructor stub
   }

   /**
    * @param id
    * @param statements
    */
   public SIPolicy(String id, Collection<Statement> statements) {
      super(id, statements);
      // TODO Auto-generated constructor stub
   }

}
