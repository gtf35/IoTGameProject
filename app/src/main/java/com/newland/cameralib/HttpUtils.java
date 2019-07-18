//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.cameralib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public HttpUtils() {
    }

    public static void doGetAsyn(final String urlStr, final HttpUtils.CallBack callBack) {
        (new Thread() {
            public void run() {
                try {
                    String result = HttpUtils.doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }).start();
    }

    public static void doPostAsyn(final String urlStr, final String params, final HttpUtils.CallBack callBack) throws Exception {
        (new Thread() {
            public void run() {
                try {
                    String result = HttpUtils.doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }).start();
    }

    public static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            System.out.println("conn.getResponseCode() :" + conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
                if (conn.getResponseCode() == 401) {
                    return "401";
                } else {
                    throw new RuntimeException(" responseCode is not 200 ... ");
                }
            } else {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buf = new byte[128];

                int len;
                while((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }

                baos.flush();
                String var8 = baos.toString();
                return var8;
            }
        } catch (Exception var22) {
            var22.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException var21) {
            }

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException var20) {
            }

            conn.disconnect();
        }
    }

    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            if (param != null && !param.trim().equals("")) {
                out = new PrintWriter(conn.getOutputStream());
                out.print(param);
                out.flush();
            }

            String line;
            for(in = new BufferedReader(new InputStreamReader(conn.getInputStream())); (line = in.readLine()) != null; result = result + line) {
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return result;
    }

    public static void doGetImageAsyn(final String urlStr, final HttpUtils.ImageCallBack callBack) {
        (new Thread() {
            public void run() {
                try {
                    Bitmap Bitmap = HttpUtils.getBitmap(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(Bitmap);
                    }
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }).start();
    }

    public static Bitmap getBitmap(String path) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            url = new URL(path);
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

            is = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[128];

            int len;
            while((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }

            baos.flush();
            byte[] dataImage = baos.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(dataImage, 0, dataImage.length);
            Bitmap var10 = bitmap;
            return var10;
        } catch (Exception var22) {
            var22.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException var21) {
            }

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException var20) {
            }

            conn.disconnect();
        }

        return null;
    }

    public interface CallBack {
        void onRequestComplete(String var1);
    }

    public interface ImageCallBack {
        void onRequestComplete(Bitmap var1);
    }
}
