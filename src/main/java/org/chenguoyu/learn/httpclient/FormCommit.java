package org.chenguoyu.learn.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 表单提交
 */
public class FormCommit {
    public static void main(String[] args) throws IOException {
        FormCommit();
    }

    private static void FormCommit() throws IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost("http://localhost:8080/Crayfish/HttpClientServer");
            HttpEntity formEntity = getFormEntity();
            post.setEntity(formEntity);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeHttpClient(httpclient);
            CommonUtils.closeResponse(response);
        }
    }

    /**
     * 表单数据
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpEntity getFormEntity() throws UnsupportedEncodingException {
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("name", "果林椰子"));
        formParams.add(new BasicNameValuePair("password", "helloWord!"));
        return new UrlEncodedFormEntity(formParams, "UTF-8");
    }

}
