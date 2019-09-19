package pers.adlered.liteftpd.tool;

import pers.adlered.liteftpd.analyze.PrivateVariable;
import pers.adlered.liteftpd.variable.Variable;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class CharsetSelector {
    public static String getCharset(byte[] bytes) {
        //return guessEncoding(bytes);
        int UTF8ERR = 0;
        int GB2312ERR = 0;
        String UTF8 = null;
        String GB2312 = null;
        try {
            UTF8 = new String(bytes, StandardCharsets.UTF_8);
            GB2312 = new String(bytes, "GB2312");
        } catch (UnsupportedEncodingException UEE) {
            //TODO
            UEE.printStackTrace();
        }
        if (UTF8 != null) {
            int fromIndex = 0;
            int count = 0;
            while (true) {
                int index = UTF8.indexOf("�", fromIndex);
                if (-1 != index) {
                    fromIndex = index + 1;
                    count++;
                } else {
                    break;
                }
            }
            while (true) {
                int index = UTF8.indexOf("ţ", fromIndex);
                if (-1 != index) {
                    fromIndex = index + 1;
                    count++;
                } else {
                    break;
                }
            }
            while (true) {
                int index = UTF8.indexOf("Ƥ", fromIndex);
                if (-1 != index) {
                    fromIndex = index + 1;
                    count++;
                } else {
                    break;
                }
            }
            UTF8ERR = count;
            //System.out.println("UTF8 " + count);
        }
        if (GB2312 != null) {
            int fromIndex = 0;
            int count = 0;
            while (true) {
                int index = GB2312.indexOf("�", fromIndex);
                if (-1 != index) {
                    fromIndex = index + 1;
                    count++;
                } else {
                    break;
                }
            }
            GB2312ERR = count;
            //System.out.println("GB2312 " + count);
        }
        if (UTF8ERR < GB2312ERR) {
            return "UTF-8";
        } else if (UTF8ERR > GB2312ERR) {
            return "GB2312";
        } else {
            return Variable.defaultEncode;
        }
    }
}