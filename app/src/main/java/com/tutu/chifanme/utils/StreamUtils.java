package com.tutu.chifanme.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：曹贵生 on 2016/11/5.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class StreamUtils {

    public static String readFromStream (InputStream in) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len =0;
        byte[] bytes = new byte[1024];

        while ((len = in.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, len);
        }

        String result = byteArrayOutputStream.toString();
        in.close();
        byteArrayOutputStream.close();
        return result;

    }
}
