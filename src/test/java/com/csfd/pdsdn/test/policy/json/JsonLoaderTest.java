/**
 * 
 */
package com.csfd.pdsdn.test.policy.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.csfd.pdsdn.policy.json.jsonLoader;

/**
 * @author shic
 *
 */
public class JsonLoaderTest {

   /**
    * @param args
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException {
      File file = new File("user1.json");
      InputStream is = new FileInputStream(file);
      jsonLoader jl = new jsonLoader(is);
      jl.getJsonObject();
   }

}
