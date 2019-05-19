package org.chenguoyu.learn.httpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 */
public class FileUpload {
    private static String PATH_NAME = "C:\\Users\\cheng\\Downloads\\1557904268510.xls";
    private static String FILE_NAME = "C:\\Users\\cheng\\Downloads\\1557904303677.xls";

    public static void main(String[] args) throws IOException {
//        fileUpload("http://localhost:8088/dcoos/api/v1/upload");

//        batchUpload("http://localhost:8088/dcoos/api/v1/uploads");

//        uploadWithForm("http://localhost:8088/dcoos/api/v1/uploadWithForm");

//        uploadsWithForm("http://localhost:8088/dcoos/api/v1/uploadsWithForm");


        uploadsWithJson("http://localhost:8088/dcoos/api/v1/uploadsWithJson");
//
//        exampleMultiPartUpload("http://localhost:8088/dcoos/api/v1/uploadsWithJson");
    }

    /**
     * 单文件上传
     *
     * @param url
     */
    private static void fileUpload(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            //File文件格式上传
            HttpEntity dataEntity = getMultiFileEntity();
//            //File文件格式上传（缺省）
//            HttpEntity dataEntity = getMultiDefaultFileEntity();
//            //byte数组格式上传
//            HttpEntity dataEntity = getMultiArrayEntity();
//            //byte数组格式上传（缺省）
//            HttpEntity dataEntity = getMultiDefaultArrayEntity();
//            //文件流格式上传
//            HttpEntity dataEntity = getMultiStreamEntity();
//            //文件流格式上传（缺省）
//            HttpEntity dataEntity = getMultiDefaultStreamEntity();
            post.setEntity(dataEntity);
            response = httpclient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeHttpClient(httpclient);
            CommonUtils.closeResponse(response);
        }
    }

    /**
     * 多文件上传
     *
     * @param url
     */
    private static void batchUpload(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            //多文件上传
            HttpEntity dataEntity = getBatchMultiDefaultFileEntity();
            post.setEntity(dataEntity);
            response = httpclient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeHttpClient(httpclient);
            CommonUtils.closeResponse(response);
        }
    }

    /**
     * 单文件上传+表单
     *
     * @param url
     */
    private static void uploadWithForm(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            //带表单上传传
            HttpEntity dataEntity = getMultiDefaultFileEntityWithForm();
            post.setEntity(dataEntity);
            response = httpclient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeHttpClient(httpclient);
            CommonUtils.closeResponse(response);
        }
    }

    /**
     * 多文件上传+表单
     *
     * @param url
     */
    private static void uploadsWithForm(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            //带表单上传传
            HttpEntity dataEntity = getBatchMultiDefaultFileEntityWithForm();
            post.setEntity(dataEntity);
            response = httpclient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeHttpClient(httpclient);
            CommonUtils.closeResponse(response);
        }
    }

    /**
     * 多文件上传+表单
     *
     * @param url
     */
    private static void uploadsWithJson(String url) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            HttpEntity dataEntity = getBatchMultiDefaultFileEntityWithJson();
            post.setEntity(dataEntity);
            response = httpclient.execute(post);
            System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeHttpClient(httpclient);
            CommonUtils.closeResponse(response);
        }
    }

    /**
     * File文件格式上传
     * getMultiFileEntity中调用了MultipartEntityBuilder中的addBinaryBody方法，
     * 第一个参数"file"代表字段名，相当于<input type="file" name="file"/>，
     * 第二个参数是读取的文件类，
     * 第三个参数ContentType.DEFAULT_BINARY指定了发送http请求时的content-type为application/octet-stream，
     * 第四个参数代表文件名。
     */
    public static HttpEntity getMultiFileEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(PATH_NAME);
        builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, System.currentTimeMillis() + ".xls");
        return builder.build();
    }

    /**
     * File文件格式上传（缺省）
     * 也可采用缺省方式getMultiDefaultFileEntity，查看源码可以发现，
     * content-type默认为ContentType.DEFAULT_BINARY，文件名会取文件本来的名字
     */
    public static HttpEntity getMultiDefaultFileEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(PATH_NAME);
        builder.addBinaryBody("file", file);
        return builder.build();
    }

    /**
     * byte数组格式上传
     * getMultiArrayEntity中调用addBinaryBody时，
     * 传入的是一个byte数组，
     * 其他参数与第一种方式相同。
     */
    public static HttpEntity getMultiArrayEntity() throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        byte[] byteArr = FileUtils.readFileToByteArray(new File(PATH_NAME));
        builder.addBinaryBody("file", byteArr, ContentType.DEFAULT_BINARY, System.currentTimeMillis() + ".xls");
        return builder.build();
    }

    /**
     * byte数组格式上传（缺省）
     * byte数组方式上传，同样提供了默认方式（参见getMultiDefaultArrayEntity），
     * 但是若采用默认方式，上传到服务器后，文件名为null且没有后缀，会影响文件正常显示
     */
    public static HttpEntity getMultiDefaultArrayEntity() throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        byte[] byteArr = FileUtils.readFileToByteArray(new File(PATH_NAME));
        builder.addBinaryBody("file", byteArr);
        return builder.build();
    }

    /**
     * 文件流格式上传
     * getMultiStreamEntity中调用addBinaryBody时，
     * 传入的是一个InputStream，其他参数与第一种方式相同。
     */
    public static HttpEntity getMultiStreamEntity() throws FileNotFoundException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(PATH_NAME);
        InputStream stream = new FileInputStream(file);
        builder.addBinaryBody("file", stream, ContentType.DEFAULT_BINARY, System.currentTimeMillis() + ".xls");
        return builder.build();
    }

    /**
     * 文件流格式上传（缺省）
     * 文件流方式上传，也提供了默认方式（参见getMultiDefaultStreamEntity），
     * 但是若采用默认方式，上传到服务器后，文件名也会为null且没有后缀，会影响文件正常显示。
     */
    public static HttpEntity getMultiDefaultStreamEntity() throws FileNotFoundException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(PATH_NAME);
        InputStream stream = new FileInputStream(file);
        builder.addBinaryBody("file", stream);
        return builder.build();
    }

    /**
     * 多文件上传
     * File文件格式上传（缺省）
     * 也可采用缺省方式getMultiDefaultFileEntity，查看源码可以发现，
     * content-type默认为ContentType.DEFAULT_BINARY，文件名会取文件本来的名字
     */
    public static HttpEntity getBatchMultiDefaultFileEntity() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file1 = new File(PATH_NAME);
        File file2 = new File(FILE_NAME);
        builder.addBinaryBody("files", file1);
        builder.addBinaryBody("files", file2);
        return builder.build();
    }

    /**
     * 文件与表单上传
     * File文件格式上传（缺省）
     * 也可采用缺省方式getMultiDefaultFileEntity，查看源码可以发现，
     * content-type默认为ContentType.DEFAULT_BINARY，文件名会取文件本来的名字
     */
    public static HttpEntity getMultiDefaultFileEntityWithForm() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file1 = new File(PATH_NAME);
        builder.addTextBody("tmpString", "/20170317/");
        builder.addBinaryBody("file", file1);
        return builder.build();
    }

    /**
     * 多文件上传
     * File文件格式上传（缺省）
     * 也可采用缺省方式getMultiDefaultFileEntity，查看源码可以发现，
     * content-type默认为ContentType.DEFAULT_BINARY，文件名会取文件本来的名字
     */
    public static HttpEntity getBatchMultiDefaultFileEntityWithForm() {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("tmpString", "/20170317/");
        File file1 = new File(PATH_NAME);
        File file2 = new File(FILE_NAME);
        builder.addBinaryBody("files", file1);
        builder.addBinaryBody("files", file2);
        return builder.build();
    }


    /**
     * 多文件上传+json
     *
     * @return
     * @throws IOException
     */
    public static HttpEntity getBatchMultiDefaultFileEntityWithJson() throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        File file1 = new File(PATH_NAME);
        File file2 = new File(FILE_NAME);
        builder.addBinaryBody("files", file1, ContentType.DEFAULT_BINARY, System.currentTimeMillis() + ".xls");
        builder.addBinaryBody("files", file2, ContentType.DEFAULT_BINARY, System.currentTimeMillis() + ".xls");

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "张三");
        jsonMap.put("age", "18");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(jsonMap);
        builder.addTextBody("jsonMap", jsonStr, ContentType.APPLICATION_JSON);
        return builder.build();
    }
}
