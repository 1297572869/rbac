package com.cgeel.utils;


import com.cgeel.common.utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 网页授权获取用户信息
 */
public class WebAuthorizeUtil
{

	public static String SNSAPI_BASE = "snsapi_base";
	public static String SNSAPI_USERINFO = "snsapi_userinfo";
	//snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
	//snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息


	/**
	 * 用户同意授权，获取code
	 * @param redirectUri
	 * @param scope 授权作用域
	 */
	public static String getCode(String redirectUri,String scope){
		//用户同意授权 获取code
		String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		StringBuffer strBuf;

		try {
			System.out.println(URLEncoder.encode(redirectUri, "utf-8") + "===========");
			codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
					"appid=" + Configuration.getProperty("appid")+
					"&redirect_uri="+URLEncoder.encode(redirectUri, "utf-8")+
					"&response_type=code" +
					"&scope=" +scope+
					"&state=security#wechat_redirect";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

	/*		strBuf = new StringBuffer();

	try{
			URL url = new URL(codeUrl);
			URLConnection conn = url.openConnection();
			*//*HttpURLConnection http=(HttpURLConnection)url.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
*//*
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
//			BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));//转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}*/
		return codeUrl;
	}


	/**
	 * 通过code换取网页授权access_token
	 * @param code
	 * @return
	 */
	public static String  getAccessToken(String code){
		//用户同意授权 获取code
		String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		StringBuffer strBuf;

		try {
			codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
					"appid=" + Configuration.getProperty("appid")+
					"&secret=" + Configuration.getProperty("appsecret")+
					"&code="+code +
					"&grant_type=authorization_code";
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		strBuf = new StringBuffer();

		try{
			URL url = new URL(codeUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		return strBuf.toString();

	}


	/**
	 * 刷新access_token
	 * @param refreshToken
	 * @return
	 */
	public static String  refreshAccessToken(String refreshToken ){
		//用户同意授权 获取code
		String codeUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		StringBuffer strBuf;

		try {
			codeUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
					"appid=" + Configuration.getProperty("appid")+
					"&grant_type=refresh_token "+
					"&refresh_token="+refreshToken ;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		strBuf = new StringBuffer();

		try{
			URL url = new URL(codeUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		return strBuf.toString();

	}


	/**
	 * 获取用户信息(scope为 snsapi_userinfo)
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static String  getUserInfo(String accessToken,String openid){
		//用户同意授权 获取code
		String codeUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		StringBuffer strBuf;

		try {
			codeUrl = "https://api.weixin.qq.com/sns/userinfo?" +
					"access_token=" +accessToken+
					"&openid=" +openid+
					"&lang=zh_CN";
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		strBuf = new StringBuffer();

		try{
			URL url = new URL(codeUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		return strBuf.toString();

	}


	/**
	 * 检验授权凭证（access_token）是否有效
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static String  checkAccessToken(String accessToken,String openid){
		//用户同意授权 获取code
		String codeUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
		StringBuffer strBuf;

		try {
			codeUrl = "https://api.weixin.qq.com/sns/auth?" +
					"access_token=" +accessToken+
					"&openid="+openid;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		strBuf = new StringBuffer();

		try{
			URL url = new URL(codeUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return strBuf.toString();

	}
}
