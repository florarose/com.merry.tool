package com.java.common.JiaMi;


import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 加密
 */
public class MD5Util {

  /**
   * 第一种，传入参数为string
   * @param inStr
   * @return
   */
  public static String getMD5(String inStr) {
          MessageDigest md5 = null;
          try {
            md5 = MessageDigest.getInstance("MD5");
          } catch (Exception e) {

            e.printStackTrace();
            return "";
          }
          char[] charArray = inStr.toCharArray();
          byte[] byteArray = new byte[charArray.length];

          for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

          byte[] md5Bytes = md5.digest(byteArray);

          StringBuffer hexValue = new StringBuffer();

          for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
              hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
          }

          return hexValue.toString();
      }

  /**
   *  hash加密，密码加密后，再通过hash加密。
   * @param userName
   * @param passwd
   * @return
   * @throws GeneralSecurityException
   */
      public static byte[] hash(String userName, byte[] passwd) throws GeneralSecurityException {
        //$NON-NLS-1$
          MessageDigest md5 = MessageDigest.getInstance("MD5");
          md5.update(toBytesQuietly(userName));
          md5.update(passwd);
          return md5.digest();
      }
      public static byte[] toBytesQuietly(String data)
      {
        return toBytesQuietly(data, "UTF-8");
      }

      public static byte[] toBytesQuietly(String data, String encoding)
      {
        try
        {
          return data.getBytes(encoding);
        } catch(UnsupportedEncodingException e)
        {
          throw new RuntimeException(e);
        }
      }

  /**
   * 加密 byte []类型的参数
   * @param content
   * @return
   */
  public static final byte[] computeMD5(byte[] content) {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      return md5.digest(content);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

}
