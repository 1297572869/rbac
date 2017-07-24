package com.cgeel.utils;


import com.cgeel.common.utils.StringUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信
 */
public class SendMsg {

	public static String url = "http://gw.api.taobao.com/router/rest";
	public static String appkey = "23305417";
	public static String secret = "b5c8815e8c44bd84a1e00a587ccca258";
	public static String smsType = "normal";
	public static String signName = "维森科技";

	public static String SMS_LOGIN = "SMS_5000296";
	public static String SMS_REGIST = "SMS_5000294";
	public static String SMS_CHANGEPHONE = "SMS_5000291";
	
	
//	模板编号	变量	模板内容	需求内容
//	SMS_5000296 ${code},${product}  验证码${code}，您正在登录${product}，若非本人操作，请勿泄露。
//	SMS_5000294	${code},${product}	验证码${code}，您正在注册成为${product}用户，感谢您的支持！	用户注册
//	SMS_5000291	${code},${product}	验证码${code}，您正在尝试变更${product}重要信息，请妥善保管账户信息。	变更绑定手机号
//	SMS_6100283	${name}	尊敬的“${name}”您好，您的账号已经绑定新的手机号${mobi}，请妥善保管此手机号码	尊敬的“*企业名称*用户”您好，您的账号已经绑定新的手机号“*****”，请妥善保管此手机号码
//	SMS_6090319	${name}	尊敬的“${name}”您好，您的密码已经被系统重置，现在您可以前往您的邮箱查看您的新密码并且登录相应的业务系统来更改您的密码	尊敬的“*企业名称*用户”您好，您的密码已经被系统重置，现在您可以前往您的邮箱查看您的新密码并且登录相应的业务系统来更改您的密码
//	SMS_6085380	${name}	尊敬的"${name}"您好，您的账号已经成功开通，现在您可以登录您的邮箱查看您的账号和密码来登录相应的业务系统	尊敬的“*企业名称*用户”您好，您的账号已经成功开通，现在您可以登录您的邮箱查看您的账号和密码来登录相应的业务系统
//	SMS_6075315	${product}	尊敬的用户您好，您的VIP已经被${product}撤销，如果有任何问题请联系客服	尊敬的用户您好，您的VIP已经被“企业名称”撤销，如果有任何问题请联系客服
//	SMS_6090317	${product}	尊敬的用户您好，恭喜您的账号已经升级成${product}的VIP，请妥善保管好您的账号	尊敬的用户您好，恭喜您的账号已经升级成“企业名称”的VIP，请妥善保管好您的账号
//	SMS_6085378	${time}	尊敬的客户您好，${time}您的密码已经被更改，如果不是您本人操作，请立即联系客服	尊敬的客户您好，****时刻您的密码已经被更改，如果不是您本人操作，请立即联系客服
//	SMS_6155278	${time},${mobi}	尊敬的用户您好，${time}您的账号已经成功绑定${mobi}手机号，如不是本人操作，请立即联系物业管理员	尊敬的用户您好，****时刻您的账号已经成功绑定***手机号，如不是本人操作，请立即联系物业管理员
//	SMS_6085377		尊敬的用户您好，恭喜您的账号注册成功，请妥善保管您的账号	

	
	


	/**
	 * 发送短信
	 * @param extend 公共回传参数
	 * @param smsType 短信类型，传入值请填写normal
	 * @param signName 短信签名
	 * @param map 短信模板变量 {"key":"value"}
	 * @param phoneNum 短信接收号码  群发短信需传入多个号码，以英文逗号分隔
	 * @param templateCode 短信模板ID
	 * @return
	 */
	public static String sendMsg(String extend,Map map,String phoneNum,String templateCode){
		String param = StringUtils.toJson(map);

		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(extend);
		req.setSmsType(smsType);
		req.setSmsFreeSignName(signName);
		req.setSmsParamString(param);
		req.setRecNum(phoneNum);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
			return rsp.getBody();

		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("code", "65656");
		map.put("product", "红木平台");
		sendMsg(null, map, "1242212212", "SMS_5000291");
	}
}
