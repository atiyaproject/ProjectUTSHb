package com.atiya.projectuts;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class prodhalal_HTTPHandler {

	private static final String TAG = prodhalal_HTTPHandler.class.getSimpleName(); 
	 // public static final String MYURL = "http://172.18.12.67/halal_beauty/"; 
	 // 127.0.0.1
	  public static final String MYURL = "http://192.168.1.108/halal_beauty/"; 
	  //public static final String MYURL = "http://169.254.39.153/json3tib/";

	 // public static final String MYURL = "http://127.0.0.1/json3tib/";
	  public static final String VIEW = "viewProdHalal.php"; 
	  public static final String INSERT = "insertProdHalal.php"; 
	  public static final String UPDATE = "updateProdHalal.php"; 
	  public static final String DELETE = "deleteProdHalal.php"; 
	 
	  public prodhalal_HTTPHandler() { 
	  } 

	  public String callJson() { 
		  String response = null; 
		  try { 
		    URL url = new URL(MYURL + VIEW); 
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
		    conn.setRequestMethod("GET"); 
		    // read the response 
		    InputStream in = new BufferedInputStream(conn.getInputStream()); 
		    response = convertStreamToString(in); 
		  } catch (MalformedURLException e) { 
		    Log.e(TAG, "MalformedURLException: " + e.getMessage()); 
		  } catch (ProtocolException e) { 
		    Log.e(TAG, "ProtocolException: " + e.getMessage()); 
		  } catch (IOException e) { 
		    Log.e(TAG, "IOException: " + e.getMessage()); 
		  } catch (Exception e) { 
		    Log.e(TAG, "Exception: " + e.getMessage()); 
		  } 
		  return response; 
		} 
	  
	  public String sendJson(String url, entity_produk_halal produkhalal) { 
		  InputStream inputStream = null; 
		  String result = ""; 
		  try { 
		    // create HttpClient 
		    HttpClient httpclient = new DefaultHttpClient(); 
		    // make POST request to the given URL 
		    HttpPost httpPost = new HttpPost(url); 
		    String json = ""; 
		    // build jsonObject 
		    JSONObject jsonObj = new JSONObject(); 
		    jsonObj.accumulate("nomor_sertifikat", produkhalal.getId()); 
		    jsonObj.accumulate("nama_produk", produkhalal.getName()); 
		    jsonObj.accumulate("nama_brand", produkhalal.getBrand()); 
		    jsonObj.accumulate("masa_berlaku", produkhalal.getMasaberlaku()); 
		    // convert JSONObject to JSON to String 
		    json = jsonObj.toString(); 
		    
		    
		      // set json to StringEntity 
		      StringEntity se = new StringEntity(json); 
		 
		      // set httpPost Entity 
		      httpPost.setEntity(se); 
		 
		      // Set some headers to inform server about the type of the content 
		      httpPost.setHeader("Accept", "application/json"); 
		      httpPost.setHeader("Content-type", "application/json"); 
		 
		      // Execute POST request to the given URL 
		      HttpResponse httpResponse = httpclient.execute(httpPost); 
		 
		      // receive response as inputStream 
		      inputStream = httpResponse.getEntity().getContent(); 
		 
		      // convert inputstream to string 
		      if (inputStream != null) 
		        result = convertStreamToString(inputStream); 
		      else 
		        result = "Did not work!"; 
		 
		    } catch (Exception e) { 
		      Log.d("InputStream", e.getLocalizedMessage()); 
		    } 
		     
		    return result; 
		  }	    
	  
	  private String convertStreamToString(InputStream is) { 
		  BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
		  StringBuilder sb = new StringBuilder(); 
		  String line; 
		  try { 
		    while ((line = reader.readLine()) != null) { 
		      sb.append(line).append('\n'); 
		    } 
		  } catch (IOException e) { 
		    e.printStackTrace(); 
		  } finally { 
		    try { 
		      is.close();
		      } catch (IOException e) { 
		          e.printStackTrace(); 
		        } 
		      } 
		      return sb.toString(); 
		    } 
		  }

