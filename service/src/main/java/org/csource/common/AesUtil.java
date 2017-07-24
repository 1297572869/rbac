package org.csource.common;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 *
 * @author Administrator
 *
 */
public class AesUtil {
public static final String  key="89930300202020202020202020202020";
	// 加密
	public static String EncryptNoHex(String sSrc, String sKey) throws Exception {
		
		byte[] clear =sSrc.getBytes("utf-8");
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);

		return new Base64().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 加密
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		byte[] clear =sSrc.getBytes("utf-8");
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		byte[] raw = parseHexStr2Byte(sKey);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);

		return new Base64().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 加密
	public static byte[] Encrypt(byte[] clear, String sKey) throws Exception {
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		byte[] raw = parseHexStr2Byte(sKey);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);

		return encrypted;
	}
	// 加密
	public static byte[] Encrypt(byte[] clear, byte[] sKey) throws Exception {
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		
		byte[] raw = sKey;
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		
		return encrypted;
	}
	// 加密
	public static byte[] Encrypt2(String sSrc, String sKey) throws Exception {
		byte[] clear =sSrc.getBytes("utf-8");
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		byte[] raw = parseHexStr2Byte(sKey);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		
		return encrypted;
	}
	public static String Encrypt1(byte[] clear, String sKey) throws Exception {
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		byte[] raw = parseHexStr2Byte(sKey);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		
		return new Base64().encode(encrypted);
	}

	public static String Encrypt(String sSrc, byte[] raw) throws Exception {
		byte[] clear =sSrc.getBytes("utf-8");
		int len = 16 - clear.length%16;
		for (int i=0;i<len;i++){
			byte[] bytes={0};
			clear = ArrayUitl.concat(clear, bytes);
		}
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);

		return new Base64().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 解密
	public static String DecryptNoHex(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2= noPadding(original);
				String originalString = new String(original2, "utf-8");
				return originalString.trim();
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	// 解密
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			byte[] raw = parseHexStr2Byte(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2= noPadding(original);
				String originalString = new String(original2, "utf-8");
				return originalString.trim();
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static String Decrypt(byte[] encrypted1, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			byte[] raw = parseHexStr2Byte(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2= noPadding(original);
				String originalString = new String(original2, "utf-8");
				return originalString.trim();
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	// 解密
	public static String Decrypt(String sSrc, byte[] raw) throws Exception {
		try {
			System.out.println(raw.length);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2= noPadding(original);
				String originalString = new String(original2, "utf-8");
				return originalString.trim();
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
	/**
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return byte[]数组
	 * @throws Exception
	 */
	public static byte[] DecryptHex(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			byte[] raw = parseHexStr2Byte(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2 = noPadding(original);
				return original2;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	public static byte[] DecryptHex(String sSrc, String sKey,int datalength) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			byte[] raw = parseHexStr2Byte(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2 = noPadding(original,datalength);
				return original2;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	public static byte[] DecryptHex(byte[] sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			byte[] raw = parseHexStr2Byte(sKey);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = sSrc;// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2 = noPadding(original);
				return original2;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	public static byte[] Decrypt(byte[] sSrc, byte[] sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			byte[] raw = sKey;
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = sSrc;// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				byte[] original2 = noPadding(original);
				return original2;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String as=AesUtil.Encrypt("1", "89930300202020202020202020202020");
		System.out.println(as);
		System.out.println(AesUtil.Decrypt(as, "89930300202020202020202020202020"));
		
		
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
	public static byte[] hexStr2Byte(String hex) {


        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }
	
	/**
     * 去除数组中的补齐
     *
     * @param paddingBytes 源数组
     * @return 去除补齐后的数组
     */
    public static byte[] noPadding(byte[] paddingBytes) {
        int index = paddingIndex(paddingBytes);
        byte[] noPaddingBytes = null;
        if (index > 0) {
            int len = paddingBytes.length - index ;
            noPaddingBytes = new byte[index];
            System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, index);
        }
        return noPaddingBytes;
    }

    /**
     * 获取补齐的位置
     *
     * @param paddingBytes 源数组
     * @return 补齐的位置
     */
    private static int paddingIndex(byte[] paddingBytes) {
        for (int i = paddingBytes.length - 1; i >= 0; i--) {
            if (paddingBytes[i] != 0) {
                return i+1;
            }
        }
        return -1;
    }
	public static byte[] getKey(String code){
		String re = "";
		if(code.length()>16){
			re = code.substring(0, 16);
		}else{
			re=code;
			for (int i = 0; i < (16-code.length()); i++) {
				re+="h";
			}
		}
		try {
			return re.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
    
	/**
     * 去除数组中的补齐
     *
     * @param paddingBytes 源数组
     * @param dataLength   去除补齐后的数据长度
     * @return 去除补齐后的数组
     */
    public static byte[] noPadding(byte[] paddingBytes, int dataLength) {
        if (paddingBytes == null) {
            return null;
        }

        byte[] noPaddingBytes = null;
        if (dataLength > 0) {
            if (paddingBytes.length > dataLength) {
                noPaddingBytes = new byte[dataLength];
                System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, dataLength);

            } else if (paddingBytes.length == dataLength) {
                noPaddingBytes = paddingBytes;

            } else {
                int index = paddingIndex(paddingBytes);
                if (index > 0) {
                    noPaddingBytes = new byte[index];
                    System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, index);
                }
            }
        } else {
            int index = paddingIndex(paddingBytes);
            if (index > 0) {
                noPaddingBytes = new byte[index];
                System.arraycopy(paddingBytes, 0, noPaddingBytes, 0, index);
            }
        }

        return noPaddingBytes;
    }
	
}
