/**
 * 
 */
package com.csfd.pdsdn.test.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.csfd.pdsdn.helper.GlobalHelper;

/**
 * @author shic
 *
 */
public class RestClient implements Callable<String> {
   private CloseableHttpClient httpClient;
   private HttpHost target;
   private String method;
   private String path;
   private String data;

   public static final String DEFAULT_HOST_ADDRESS = "127.0.0.1";
   public static final int DEFAULT_PORT = 8080;

   /**
    * @param path
    * @param data
    */
   public RestClient(String method, String path, String data) {
      super();
      httpClient = HttpClientBuilder.create().build();
      target = new HttpHost(DEFAULT_HOST_ADDRESS, DEFAULT_PORT, "http");
      this.method = method;
      this.path = path;
      this.data = data;
   }

   /*
    * (non-Javadoc)
    * @see java.util.concurrent.Callable#call()
    */
   @Override
   public String call() {
      HttpResponse httpResponse = null;
      switch (method) {
      case "PUT": {
         HttpPut putRequest = new HttpPut(path);
         try {
            putRequest.setEntity(new StringEntity(data));
         } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         try {
            httpResponse = httpClient.execute(target, putRequest);
         } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      }
      try {
         httpClient.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      setEndTime();
      if (httpResponse.getStatusLine().getStatusCode() == 200)
         return "RESPONSE_OK";
      return "ERROR";
   }

   private synchronized void setEndTime() {
      long currentTime = System.currentTimeMillis();
      if (currentTime > GlobalHelper.getTaskEndTime())
         GlobalHelper.setTaskEndTime(currentTime);
   }

}
