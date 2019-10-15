package com.tavisca.SendData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @PostMapping("/add")
    public String addEmployee(@RequestBody String employee) {

        HttpPost post = new HttpPost("http://localhost:8080/add");
        System.out.println(employee.toString());
        System.out.println(employee);
        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("requestBody", employee.toString()));

        try {
            post.setHeader("content-type","application/json");
            post.setEntity(new StringEntity(employee));

            System.out.println(post.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
//            System.out.println(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error Occurred";
    }


    @GetMapping("/list")
    public ArrayList getList() {
        HttpGet request = new HttpGet("http://localhost:8080/employees");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
//            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
                Gson gson = new Gson();
                return gson.fromJson(result, ArrayList.class);
//                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/employee")
    public Object getEmployee(@RequestParam long id){
        HttpGet request = new HttpGet("http://localhost:8080/employees/"+id);

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
//            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
//                Gson gson = new Gson();
                return result;
//                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
