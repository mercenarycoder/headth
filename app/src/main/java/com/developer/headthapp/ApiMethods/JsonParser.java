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
    public String acceptingOrderFinally(String url, String appointment_id, String status)
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
            params.put("status",status);
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
    public String changingProduct(String url, String staff_id, String service_id, String status)
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
            params.put("service_id",service_id);
            params.put("staff_id",staff_id);
            params.put("status",status);
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
    public String updateSlots(String url, String slot_id, String day_name, String start_time, String end_time,
                              String status, String max_appointment)
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
            //params.put("service_id",service_id);
            params.put("slot_id",slot_id);
            params.put("day_name",day_name);
            params.put("start_time",start_time);
            params.put("end_time",end_time);
            //params.put("day_repeate",day_repeate);
            params.put("max_appointment",max_appointment);
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
    public String getServices(String url, String product_id)
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
            //params.put("service_id",service_id);
            params.put("category_id",product_id);
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
    public String updateSettings(String url, String cmp_id, String name, String cmp_city,
                                 String email, String gstin, String nature, String pan, String state,
                                 String account_name, String account_number, String bank_ifsc_code, String address,
                                 String number, String tnc,
                                 String bus_cat, String bus_status, String service_id, String img,
                                 String doc, String latitude, String longitude, String areas)
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
            params.put("cmp_id",cmp_id);

            params.put("cmp_name",name);
            params.put("city_name",cmp_city);
            params.put("logo",img);
            params.put("email",email);
            params.put("gstin","GST");
            params.put("nature_of_busniess",nature);
            params.put("pan",pan);
            params.put("state",state);
            params.put("account_name",account_name);
            params.put("account_number",account_number);
            params.put("bank_ifsc_code",bank_ifsc_code);
            params.put("address",address);
            params.put("contact_no",number);
            params.put("term_condition",tnc);
            params.put("business_status",bus_status);
            params.put("business_category",bus_cat);
            params.put("service_at",service_id);
            params.put("business_doc_upload",doc);
            if(!latitude.equals("empty")) {
                params.put("longitude", longitude);
                params.put("latitude", latitude);
            }
            params.put("localitybycity",areas);
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
    public String updateServices2(String url, String prod_id, String name, String rate, String description,
                                  String image, String hsn_id, String cat_id, String status)
    {
        HttpURLConnection httpURLConnection = null;
        try
        {

            URL url2 = new URL(url);



            httpURLConnection=(HttpURLConnection)url2.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            //httpURLConnection.connect();


            JSONObject params = new JSONObject();
            params.put("product_id",prod_id);
            params.put("name",name);
            params.put("category_id",cat_id);
            params.put("rate",rate);
            params.put("status",status);
            params.put("description",description);
            //img.compress()
            params.put("photo",image);
            params.put("hsn_id",hsn_id);

            Log.d("LoginData", "---> " + params);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(params.toString());
            wr.flush();
            wr.close();

/*
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            HashMap<String,String> params = new HashMap<>();
            params.put("product_id",prod_id);
            params.put("name",name);
            params.put("category_id",cat_id);
            params.put("rate",rate);
            params.put("status",status);
            params.put("description",description);
            //img.compress()
            params.put("photo",image);
            params.put("hsn_id",hsn_id);
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
*/
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
    public String updateServices(String url, String prod_id, String name, String rate, String description,
                                 String image, String hsn_id, String cat_id, String status, String unit_id, String pp, String old_mrp
    , String barcode)
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
            params.put("product_id",prod_id);
            params.put("name",name);
            params.put("category_id",cat_id);
            params.put("rate",rate);
            params.put("status",status);
            params.put("description",description);
            //img.compress()
            params.put("photo",image);
            params.put("hsn_id",hsn_id);
            params.put("pp",pp);
            params.put("old_mrp",old_mrp);
            params.put("unit_id",unit_id);
            params.put("barcode",barcode);
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
            System.out.println(current);
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
    public String getProduct(String url, String prod_id)
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
            params.put("product_id",prod_id);
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
            //    System.out.print(current);
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
    public String getcityState(String url, String state_id)
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
            params.put("state_id",state_id);
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
                //    System.out.print(current);
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
    public String updateCategory(String url, String category_id, String name, String position, String status, String image)
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
            params.put("category_id",category_id);
            params.put("name",name);
            params.put("position",position);
            params.put("status",status);
            params.put("image",image);
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
    public String addSlots(String url, String staff_id, String day_name, String start_time, String end_time,
                           String max_appointment)
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
            params.put("day_name",day_name);
            params.put("start_time",start_time);
            params.put("end_time",end_time);
            params.put("max_appointment",max_appointment);
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
    public String getOffers(String url, String staff_id, String code, String status)
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
            params.put("code",code);
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
    public String add_offer(String url, String code, String name, String uses, String max_uses, String amount
     , String percentage, String min_sell, String description, String type, String fixed, String start_date,
                            String end_date, String image, String staff_id)
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
            params.put("offer_code",code);
            params.put("offer_name",name);
            params.put("offer_uses",uses);
            params.put("offer_max_uses_user",max_uses);
            params.put("offer_type",type);
            params.put("offer_amount",amount);
            params.put("offer_percentage",percentage);
            params.put("offer_fixed",fixed);
            params.put("offer_min_selling_price",min_sell);
            params.put("offer_start_date",start_date);
            params.put("offer_close_date",end_date);
            params.put("offer_discription",description);
            params.put("offer_image",image);
            //params.put("status",status);
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
 public String updateOffer(String url, String code, String name, String uses, String max_uses, String amount
         , String percentage, String min_sell, String description, String type, String fixed, String start_date,
                           String end_date, String image, String status, String offer_id)
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
         params.put("offer_id",offer_id);
         params.put("offer_code",code);
         params.put("offer_name",name);
         params.put("offer_uses",uses);
         params.put("offer_max_uses_user",max_uses);
         params.put("offer_type",type);
         params.put("offer_amount",amount);
         params.put("offer_percentage",percentage);
         params.put("offer_fixed",fixed);
         params.put("offer_min_selling_price",min_sell);
         params.put("offer_start_date",start_date);
         params.put("status",status);
         params.put("offer_close_date",end_date);
         params.put("offer_discription",description);
         params.put("offer_image",image);
         //params.put("status",status);
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
 public String notifications(String url, String staff_id)
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
 public String placeAuto(String url, String cmp_id, String place_id, String lat, String lng, String country, String google
 , String business_name, String address, String city_name, String postal_code)
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
         params.put("cmp_id",cmp_id);
         params.put("place_id",place_id);
         params.put("latitude",lat);
         params.put("longitude",lng);
         params.put("country","India");
         params.put("city_name",city_name);
         params.put("postalcode",postal_code);
         params.put("address",address);
         params.put("business_name",business_name);
         params.put("google_map_url",google);
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
}
