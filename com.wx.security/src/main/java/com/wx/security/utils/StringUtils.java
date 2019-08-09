/**
 * Copyright (c) (2016-2017),VSI and/or its affiliates.All rights reserved.
 * VSI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
**/
package com.wx.security.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;


public final class StringUtils extends org.apache.commons.codec.binary.StringUtils
{

    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ssZ").setPrettyPrinting().create();

    private StringUtils()
    {
    }

    public static String trimRight(String text)
    {
        for(int i = text.length() - 1; i >= 0; i--)
            if(!Character.isWhitespace(text.charAt(i)))
                return text.substring(0, i + 1);

        return "";
    }

    public static String leftPad(String str, int size)
    {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char ch)
    {
        int len = size - str.length();
        if(len > 0)
        {
            StringBuilder sb = new StringBuilder(len);
            for(int i = 0; i < len; i++)
                sb.append(ch);

            sb.append(str);
            return sb.toString();
        } else
        {
            return str;
        }
    }

    public static int occurs(char character, char string[])
    {
        int count = 0;
        char arr$[] = string;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            char ch = arr$[i$];
            if(ch == character)
                count++;
        }

        return count;
    }

    public static int occurs(char character, String string)
    {
        return occurs(character, string.toCharArray());
    }

    public static String decodeEscapeString(String str)
    {
        char ch[] = str.toCharArray();
        boolean escape = false;
        StringBuilder buf = new StringBuilder(ch.length);
        for(int i = 0; i < ch.length; i++)
        {
            if(escape)
            {
                switch(ch[i])
                {
                case 92: // '\\'
                    buf.append('\\');
                    break;

                case 98: // 'b'
                    buf.append('\b');
                    break;

                case 102: // 'f'
                    buf.append('\f');
                    break;

                case 114: // 'r'
                    buf.append('\r');
                    break;

                case 110: // 'n'
                    buf.append('\n');
                    break;

                case 116: // 't'
                    buf.append('\t');
                    break;

                case 117: // 'u'
                    int hex = Integer.parseInt(new String(ch, i + 1, i + 5), 16);
                    buf.append((char)hex);
                    i += 4;
                    break;

                default:
                    if(Character.isDigit(ch[i]))
                    {
                        int j = 0;
                        for(j = 1; j < 2 && i + j < ch.length && Character.isDigit(ch[i + j]); j++);
                        int octal = Integer.parseInt(new String(ch, i, i + j), 8);
                        buf.append((char)octal);
                        i += j;
                    } else
                    {
                        buf.append(ch[i]);
                    }
                    break;
                }
                escape = false;
                continue;
            }
            if(ch[i] == '\\')
                escape = true;
            else
                buf.append(ch[i]);
        }

        return buf.toString();
    }

    public static boolean isEmpty(String data)
    {
        if(data == null)
            return true;
        int i = 0;
        for(int size = data.length(); i < size; i++)
            if(!Character.isWhitespace(data.charAt(i)))
                return false;

        return true;
    }

    public static String getNotNull(String data)
    {
        return getNotNull(data, "");
    }

    public static String getNotNull(String data, String def)
    {
        if(data == null)
            return def;
        else
            return data;
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
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String toStringQuietly(byte data[])
    {
        return toStringQuietly(data, "UTF-8");
    }

    public static String toStringQuietly(byte data[], String encoding)
    {
        return toStringQuietly(data, 0, data.length, encoding);
    }

    public static String toStringQuietly(byte data[], int offset, int length, String encoding)
    {
        try
        {
            return new String(data, offset, length, encoding);
        }
        catch(UnsupportedEncodingException e)
        {
        	throw new RuntimeException(e);
        }
    }

    public static boolean isAscii(String data)
    {
        int len = data.length();
        for(int i = 0; i < len; i++)
        {
            char c = data.charAt(i);
            if(c > '\177')
                return false;
        }

        return true;
    }

    public static byte[] toAsciiBytes(String data)
    {
        char chars[] = data.toCharArray();
        byte buf[] = new byte[chars.length];
        for(int i = 0; i < chars.length; i++)
            buf[i] = (byte)chars[i];

        return buf;
    }

    public static String toAsciiString(byte buf[])
    {
        return toAsciiString(buf, 0, buf.length);
    }

    public static String toAsciiString(byte buf[], int offset, int length)
    {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            sb.append((char)buf[offset + i]);

        return sb.toString();
    }

    public static String substringBefore(String data, String delimeter)
    {
        int pos = data.indexOf(delimeter);
        if(pos >= 0)
            return data.substring(0, pos);
        else
            return data;
    }

    public static String substringAfter(String data, String delimeter)
    {
        int pos = data.indexOf(delimeter);
        if(pos >= 0)
            return data.substring(pos + delimeter.length());
        else
            return data;
    }

    public static CharSequence join(Iterable list)
    {
        return join(list, ",");
    }

    public static String join(Object tokens[], CharSequence delimiter)
    {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        Object arr$[] = tokens;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            Object token = arr$[i$];
            if(firstTime)
                firstTime = false;
            else
                sb.append(delimiter);
            sb.append(token);
        }

        return sb.toString();
    }

    public static String join(Iterable tokens, CharSequence delimiter)
    {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        Object token;
        for(Iterator i$ = tokens.iterator(); i$.hasNext(); sb.append(token))
        {
            token = i$.next();
            if(firstTime)
                firstTime = false;
            else
                sb.append(delimiter);
        }

        return sb.toString();
    }

    public static String buildContextString(Map<String, String> contextMap) {
        if (null == contextMap) {
            return "";
        }
        return GSON.toJson(contextMap);
    }

    public static char[] generateRandomArray(int num) {
        String chars = "0123456789";
        char[] rands = new char[num];
        for (int i = 0; i < num; i++) {
            int rand = (int)(Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }

    //判空
    public static  boolean isNull(Object object){
        if(null != object && !"".equals(object)){
            return true;
        }
        return false;
    }

    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String US_ASCII = "US-ASCII";
    public static final String UTF_8 = "UTF-8";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_16 = "UTF-16";
}
