package com.ph.shopping.facade.pay.utils.commonpay;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @项目：phshopping-parent
 * @描述：
 * @作者： Mr.chang
 * @创建时间：2017/6/29
 * @Copyright @2017 by Mr.chang
 */
public class EncodeUtils {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    public static String hexEncode(byte[] input)
    {
        return Hex.encodeHexString(input);
    }

    public static byte[] hexDecode(String input)
    {
        try
        {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    public static String base64Encode(byte[] input)
    {
        return new String(Base64.encodeBase64(input));
    }

    public static String base64UrlSafeEncode(byte[] input)
    {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] base64Decode(String input)
    {
        return Base64.decodeBase64(input);
    }

    public static String urlEncode(String input)
    {
        try
        {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    public static String urlDecode(String input)
    {
        try
        {
            return URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    public static String htmlEscape(String html)
    {
        return StringEscapeUtils.escapeHtml4(html);
    }

    public static String htmlUnescape(String htmlEscaped)
    {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    public static String xmlEscape(String xml)
    {
        return StringEscapeUtils.escapeXml11(xml);
    }

    public static String xmlUnescape(String xmlEscaped)
    {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
}
