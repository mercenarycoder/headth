package com.developer.headthapp.ApiMethods;

import android.net.Uri;
import android.util.Log;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class JsonParser {
    public JsonParser()
    {

    }
    public String updatePassword2(String new_pass, String confirm_pass, String url, String id)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //params are made over here
            HashMap<String, String> params = new HashMap<>();

            params.put("vendor_id", id);
            params.put("newpassword",new_pass);
            params.put("confirm_password",confirm_pass);

            StringBuilder builder = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String flow = builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current = "";
            InputStream ir = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String verifyOTP(String mobile, String otp, String url, String id)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //params are made over here
            HashMap<String, String> params = new HashMap<>();
            params.put("otp_no",otp);
            params.put("vendor_id",mobile);

            StringBuilder builder = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String flow = builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current = "";
            InputStream ir = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String sendOTPResend(String url, String mobile)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //params are made over here
            HashMap<String, String> params = new HashMap<>();

            params.put("vendor_id",mobile);

            StringBuilder builder = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String flow = builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current = "";
            InputStream ir = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

    public String sendOTPFirst(String url, String mobile)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //params are made over here
            HashMap<String, String> params = new HashMap<>();

            params.put("mobile_no",mobile);

            StringBuilder builder = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String flow = builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current = "";
            InputStream ir = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String userLoginFromJSON(String url, String id, String pass)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try{
            Log.d("LoginData", "I reached here---> "+id);

            URL url1=new URL(url);
            httpURLConnection=(HttpURLConnection)url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            //params are made over here
            HashMap<String, String> params = new HashMap<>();
            params.put("email",id);
            params.put("password",pass);

            StringBuilder builder = new StringBuilder();
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
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String dashboardData(String url, String staff_id, String status)
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
            params.put("status",status);
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
              //  System.out.print(current);
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
    public String getSupportInfo(String url2, String mobile, String title, String date, String image,
                                 String doctor,String observation)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {
            URL url = new URL(url2);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");


            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile", mobile);
            params.put("title", title);
            params.put("date", date);
            params.put("image_64", image);
            params.put("doctor", doctor);
            params.put("observation", observation); //params.put("status",status);
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
    public String saveCategory(String url2, String mobile, String title, String date, String image,
                               String doctor,String observation,String name)
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
            loginData.put("image_64", image);
            loginData.put("doctor", doctor);
            loginData.put("observation", observation);
            loginData.put("name", name);
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
    public String viewAppointment(String url, String appointment_id)
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
            params.put("appointment_id",appointment_id);
            //params.put("description",decription);
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
               // System.out.print(current);
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
  public String loadAreas(String city_id, String url)
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
          params.put("city_id",city_id);
          //params.put("description",decription);
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
              // System.out.print(current);
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
 public String getFinalPlace(String url, String find, String key, String place_id)
 {
     HttpURLConnection httpURLConnection = null;
     try
     {
         URL url2 = new URL(url);
         httpURLConnection=(HttpURLConnection)url2.openConnection();
         httpURLConnection.setRequestMethod("GET");


         OutputStream os=httpURLConnection.getOutputStream();
         BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
         HashMap<String, String> params = new HashMap<>();
         params.put("fields",find);
         params.put("key",key);
         params.put("place_id",place_id);
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
             //  System.out.print(current);
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
 public String updateHsnItem(String url2, String rec_id, String name, String phone)
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
}
