package com.example.demo.download;

import com.example.demo.javabean.DownedObject ;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GitHub {
    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }
    public static String transformUrl(String gitHubUrl)
    {
        String ret = "https://codeload.github.com/";
        //https://github.com/blacknight2018/gameDelayer
        //https://codeload.github.com/blacknight2018/gameDelayer/zip/master
        ret += gitHubUrl.substring(19,gitHubUrl.length());
        ret += "/zip/master";
        return ret;
    }
    public static DownedObject githubDownload(String requestUrl)  {
        if(null == requestUrl)
            return  null;
        requestUrl = transformUrl(requestUrl);
        try{
            //创建SSLContext
            SSLContext sslContext=SSLContext.getInstance("SSL");
            TrustManager[] tm={new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());;
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();
            URL url=new URL(requestUrl);

            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            byte [] readbs = getBytesFromStream(is);

            //文件名
            String dispositionField = conn.getHeaderField("Content-Disposition");
            String suggestFileName = null;
            int findResult = dispositionField.indexOf("filename");
            if(-1!=findResult) {
                suggestFileName = dispositionField.substring(findResult+8+1,dispositionField.length());
            }
            DownedObject retObj = new DownedObject();
            retObj.setFileName(suggestFileName);
            retObj.setFileDatas(readbs);

            conn.disconnect();
            return retObj;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        DownedObject downedObject = githubDownload("https://github.com/blacknight2018/gameDelayer");
        return ;
    }
}
