/**
 * 
 */
package com.csfd.pdsdn.test.policy.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONObject;

import com.csfd.pdsdn.helper.FSDBHelper;
import com.csfd.pdsdn.policy.json.JsonLoader;

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
      String filePath = FSDBHelper.ROOT_TATH + "user1.json";
      File file = new File(filePath);
      InputStream is = new FileInputStream(file);
      JsonLoader jl = new JsonLoader(is);
      JSONObject jo = jl.getJsonObject();
      System.out.print(jo.toString());
   }

}
