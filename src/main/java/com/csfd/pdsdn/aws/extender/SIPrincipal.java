/**
 * 
 */
package com.csfd.pdsdn.aws.extender;

import com.amazonaws.auth.policy.Principal;

/**
 * @author shic
 *
 */
public class SIPrincipal extends Principal {

   /**
    * @param service
    */
   public SIPrincipal(Services service) {
      super(service);
   }

   /**
    * @param accountId
    */
   public SIPrincipal(String accountId) {
      super(accountId);
   }

   /**
    * @param webIdentityProvider
    */
   public SIPrincipal(WebIdentityProviders webIdentityProvider) {
      super(webIdentityProvider);
   }

   /**
    * @param provider
    * @param id
    */
   public SIPrincipal(String provider, String id) {
      super(provider, id);
   }

}
