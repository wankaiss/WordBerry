package com.youbanban.wordberry.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;



@Component
public class Utility {
	public static final Logger LOG = LoggerFactory.getLogger(Utility.class);
	
	//传递参数，post远程提交
		public static String requestPost(String request, String requestUrl) throws UnsupportedEncodingException {
			String response = "";
			try {
				URL url = new URL(requestUrl);
				URLConnection con = url.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection) con;
				httpURLConnection.setDoOutput(true);
				httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
				httpURLConnection.setRequestProperty("Content-Type", "text/json");
				OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
				out.write(new String(request.getBytes("UTF-8")));
				out.flush();
				out.close();
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuffer buffer = new StringBuffer();
				String str = null;
				while ((str = br.readLine()) != null) {
					buffer.append(str);
				}
				response = buffer.toString();
			} catch (Exception e) {
				LOG.info("e", e);
			}
			return response;
		}  
}
