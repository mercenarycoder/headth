package com.developer.headthapp.ApiMethods;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JsonParser {
    public JsonParser()
    {

    }
    public String saveCategory(String url2, String mobile, String title, String date, String imagePath,
                               String doctor,String observation)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("mobile", mobile);
            loginData.put("title", title);
            loginData.put("date", date);
            loginData.put("imagePath", imagePath);
            loginData.put("doctor", doctor);
            loginData.put("observation", observation);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String updatePrescription(String url2,String id,String mobile,String title,String doctor,
                                     String image,String observation,String date)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("mobile", mobile);
            loginData.put("id", id);
            loginData.put("title", title);
            loginData.put("date", date);
            loginData.put("image", image);
            loginData.put("doctor", doctor);
            loginData.put("observation", observation);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String updateReport(String url2,String id,String mobile,String title,String observer,String date,String fileName
    ,String typeF,String category,String detail)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("mobile", mobile);
            loginData.put("title", title);
            loginData.put("date", date);
            loginData.put("filename", fileName);
            loginData.put("id", id);
            loginData.put("observer", observer);
            loginData.put("category", category);
            loginData.put("type", typeF);
            loginData.put("detail", detail);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String deleteImage(String url2,String file)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("file", file);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String recordLocation(String url2,String user,String accesser,String date,String time,String latitude,String longitude)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("user", user);
            loginData.put("accesser", accesser);
            loginData.put("date", date);
            loginData.put("time", time);
            loginData.put("latitude", latitude);
            loginData.put("longitude", longitude);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String addImage(String url2,String image,String name,String typef)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("image_64", image);
            loginData.put("name", name);
            loginData.put("type", typef);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String setEmergency(String url2, String mobile,String names,String phones)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("names", names);
            loginData.put("mobile", mobile);
            loginData.put("phones", phones);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String addServices(String url, String name, String rate, String cat_id,
                              String bitmap, String decription, String staff_id, String hsn_id, String gst_slot, String pp, String old_mrp)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url2 = new URL(url);
            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String, String> params = new HashMap<>();
            params.put("staff_id",staff_id);
            params.put("name",name);
            params.put("category_id",cat_id);
            params.put("rate",rate);
            params.put("status","active");
            params.put("hsn_id",hsn_id);
            params.put("unit_id",gst_slot);
            params.put("pp",pp);
            params.put("old_mrp",old_mrp);
            params.put("photo", bitmap);
            params.put("description",decription);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String, String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
            }
            System.out.print(current);
            return current;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String signingIn(String url2, String name, String mobile,String height,String weight,String blood,String dob)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("name", name);
            loginData.put("mobile", mobile);
            loginData.put("height", height);
            loginData.put("weight", weight);
            loginData.put("dob", dob);
            loginData.put("blood", blood);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String deleteEmergency(String url2,String rec_id)
    {
    HttpURLConnection connection = null;
    BufferedReader reader = null;

    try {
        URL url = new URL(url2);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();

        JSONObject loginData = new JSONObject();
        loginData.put("rec_id", rec_id);
        Log.d("LoginData", "---> " + loginData);
        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(loginData.toString());
        wr.flush();
        wr.close();

        InputStream stream = httpURLConnection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(stream));

        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String finalJson = buffer.toString();
        return finalJson;

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return null;
}
    public String deleteNotification(String url2,String id)
    {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("id", id);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
 public String viewOffer(String url2, String mobile)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("mobile", mobile);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String getCompleteHealth(String url2, String mobile, String level)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("mobile", mobile);
         loginData.put("level", level);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String deleteItems(String url2, ArrayList<String> arr,String number)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;
     HashMap<String, String> mapper=new HashMap<>();
     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();
         JSONArray array=new JSONArray();
         JSONObject loginData = new JSONObject();
         for(int i=0;i<arr.size();i++)
         {
             JSONObject object = new JSONObject();
            if(!mapper.containsKey(arr.get(i))) {
                object.put("id", arr.get(i));
                array.put(object);
            }
            else
            {
               mapper.put(arr.get(i),"is their");
            }
         }
         loginData.put("id", array);
         loginData.put("mobile", number);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
    public String deleteBigItems(String url2, ArrayList<String> arr,ArrayList<String> arr2,String number)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        HashMap<String, String> mapper=new HashMap<>();
        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            JSONArray array=new JSONArray();
            JSONArray array2 = new JSONArray();
            JSONObject loginData = new JSONObject();
            for(int i=0;i<arr.size();i++)
            {
                JSONObject object = new JSONObject();
                JSONObject object1 = new JSONObject();
                if(!mapper.containsKey(arr.get(i))) {
                    object.put("id", arr.get(i));
                    object1.put("path", arr2.get(i));
                    array.put(object);
                    array2.put(object1);
                }
                else
                {
                    mapper.put(arr.get(i),"is their");
                }
            }
            loginData.put("id", array);
            loginData.put("path", array2);
            loginData.put("mobile", number);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
 public String addReport(String url2,String mobile,String title,String observer,String date
         ,String detail,String type,String name,String category)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("mobile", mobile);
         loginData.put("filename",name);
         loginData.put("title",title);
         loginData.put("observer",observer);
         loginData.put("detail",detail);
         loginData.put("date",date);
//         loginData.put("data",data);
         loginData.put("type",type);
         loginData.put("category",category);
        // loginData.put("type",type);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String addEmergency(String url2, String mobile, String name, String phone)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("mobile", mobile);
         loginData.put("name",name);
         loginData.put("phone",phone);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String updateHsnItem(String url2, String rec_id, String name, String phone,String number)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("rec_id", rec_id);
         loginData.put("name",name);
         loginData.put("phone",phone);
         loginData.put("mobile",number);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String addDieseas(String url2,String mobile,String name,String details)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("mobile", mobile);
         loginData.put("name",name);
         loginData.put("details",details);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String updateDieseas(String url2,String id,String name,String details,String number)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("id", id);
         loginData.put("name",name);
         loginData.put("details",details);
         loginData.put("mobile",number);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String addAllergy(String url2,String mobile,String allergy,String triggers)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("mobile", mobile);
         loginData.put("allergy",allergy);
         loginData.put("triggers",triggers);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
 public String updateAllergy(String url2,String id,String allergy,String triggers,String number)
 {
     HttpURLConnection connection = null;
     BufferedReader reader = null;

     try {
         URL url = new URL(url2);
         HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
         httpURLConnection.setDoOutput(true);
         httpURLConnection.setRequestMethod("POST");
         httpURLConnection.setRequestProperty("Content-Type", "application/json");
         httpURLConnection.connect();

         JSONObject loginData = new JSONObject();
         loginData.put("id", id);
         loginData.put("allergy",allergy);
         loginData.put("triggers",triggers);
         loginData.put("mobile",number);
         Log.d("LoginData", "---> " + loginData);
         DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
         wr.writeBytes(loginData.toString());
         wr.flush();
         wr.close();

         InputStream stream = httpURLConnection.getInputStream();
         reader = new BufferedReader(new InputStreamReader(stream));

         StringBuffer buffer = new StringBuffer();
         String line = "";
         while ((line = reader.readLine()) != null) {
             buffer.append(line);
         }
         String finalJson = buffer.toString();
         return finalJson;

     } catch (MalformedURLException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (JSONException e) {
         e.printStackTrace();
     }
     return null;
 }
    public String addHistory(String url2,String mobile,String title,String description)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("mobile", mobile);
            loginData.put("title",title);
            loginData.put("description",description);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String updateHistory(String url2,String id,String title,String description,String number)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("id", id);
            loginData.put("title",title);
            loginData.put("description",description);
            loginData.put("mobile",number);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String addMedicine(String url2,String mobile,String name,String purpose,String duration,String dosage)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("mobile", mobile);
            loginData.put("name",name);
            loginData.put("purpose",purpose);
            loginData.put("duration",duration);
            loginData.put("dosage",dosage);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String updateMedicine(String url2,String id,String name,String purpose,String duration,String dosage,String number)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(url2);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("id", id);
            loginData.put("name",name);
            loginData.put("mobile",number);
            loginData.put("purpose",purpose);
            loginData.put("duration",duration);
            loginData.put("dosage",dosage);
            Log.d("LoginData", "---> " + loginData);
            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String notifications(String url, String staff_id) {
        HttpURLConnection httpURLConnection = null;
        try
        {

            URL url2 = new URL(url);

            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            System.out.println(staff_id);
            params.put("staff_id",staff_id);
            StringBuilder builder=new StringBuilder();
            boolean first=true;
            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
}
