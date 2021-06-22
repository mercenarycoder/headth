package androidi.developer.headthapp.Covid;

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.networkData;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class Volunteer extends AppCompatActivity {
ImageButton back;
Context context;
TextView name_main,phone,blood;
EditText city,pincode,address;
Button submit;
String age="23",bloodR="",nameR="",addressR="",cityR="",pinR="";
ProgressDialog dialog;
FirebaseAuth mauth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        context=Volunteer.this;
        dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        initialize();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressR=address.getText().toString().trim();
                cityR=city.getText().toString().trim();
                pinR=pincode.getText().toString().trim();
                if(addressR.length()<5)
                {
                    Toast.makeText(context,"Please enter a address long enough to identify",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cityR.length()<2){
                    Toast.makeText(context,"Please enter a valid city",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pinR.length() != 6){
                    Toast.makeText(context,"Please enter a valid Pincode to identify",Toast.LENGTH_SHORT).show();
                    return;
                }
                new becomeVolunteer().execute();
            }
        });
        new getProfile().execute();
        new defaultVolunteer().execute();
    }
    public void initialize()
    {
        back=(ImageButton)findViewById(R.id.back);
        name_main=(TextView)findViewById(R.id.name_main);
        phone=(TextView)findViewById(R.id.phone);
        blood=(TextView)findViewById(R.id.blood);
        city=(EditText)findViewById(R.id.city);
        pincode=(EditText)findViewById(R.id.pincode);
        address=(EditText)findViewById(R.id.address);
        submit=(Button)findViewById(R.id.submit);
    }
    private class becomeVolunteer extends AsyncTask<String,String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().setVolunteer;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().becomeVolunteer(url,number,pinR,cityR,addressR,bloodR,nameR,age);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            dialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object=new JSONObject(s);
                    String status = String.valueOf(object.get("status"));
                    final String responce2=String.valueOf(object.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    if(status.equals("1"))
                    {
                        new updateVolunteer().execute();
                    }
                    else {
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                    }
                    builder.create();
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Please submit after a minute",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private class updateVolunteer extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().volunteer;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().changeStatus(url,number,1);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s!=null){
                try{
                    JSONObject object=new JSONObject(s);
                    String status = String.valueOf(object.get("status"));
                    final String responce2=String.valueOf(object.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    if(status.equals("1"))
                    {
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                });
                    }
                    else {
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                    }
                    builder.create();
                    builder.show();
                }catch (Exception e){

                }
            }
        }
    }
    private class defaultVolunteer extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().defaultVolunteer;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                try{
                    JSONObject object = new JSONObject(s);
                    String status=String.valueOf(object.get("status"));
                    if(status.equals("1")){
                        JSONArray array = object.getJSONArray("data");
                        JSONObject obj=array.getJSONObject(0);
                        String address1=String.valueOf(obj.get("address"));
                        String pin1=String.valueOf(obj.get("pincode"));
                        String city1=String.valueOf(obj.get("city")).toLowerCase();
                        addressR=address1;
                        cityR=city1;
                        pinR=pin1;
                        address.setText(addressR);
                        city.setText(cityR);
                        pincode.setText(pinR);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class getProfile extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getprofile;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        JSONArray data=jsonObject.getJSONArray("data");
                        JSONObject obj=data.getJSONObject(0);
                        String nameF=obj.getString("name");
                        String arr[]=nameF.split(" ");
                        String mobileF = obj.getString("mobile");
                        String heightF = obj.getString("height");
                        String weightF = obj.getString("weight");
                        String dobF = obj.getString("dob");

                        dobF=dobF.replace("/","-");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = formatter.parse(dobF);
                        //Converting obtained Date object to LocalDate object
                        Instant instant = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            instant = date.toInstant();
                            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                            LocalDate givenDate = zone.toLocalDate();
                            //Calculating the difference between given date to current date.
                            Period period = Period.between(givenDate, LocalDate.now());
                            age=String.valueOf(period.getYears());
                        }else{
                            age="23";
                        }

                        String bloodF = obj.getString("blood");
                        if(arr.length>1)
                        {
                            nameF=arr[0].substring(0,1).toUpperCase()+arr[0].substring(1)+" "+arr[1];
                        }
                        else
                        {
                            nameF=arr[0].substring(0,1).toUpperCase()+arr[0].substring(1);
                        }
                        name_main.setText(nameF);
                        nameR=nameF;
                        bloodR=bloodF.toLowerCase();
                        blood.setText("BloodGroup: "+bloodF.toUpperCase());
                        phone.setText("+91-"+mobileF);

                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage("Some Error in fetching info please swipe to refresh")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                }
                catch (JSONException | ParseException e) {
                    e.printStackTrace();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        final String responce2=String.valueOf(jsonObject.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce2)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                    catch (Exception e2)
                    {

                    }
                }
            }
            else
            {
                Toast.makeText(context,"Some Networking error try again later",Toast.LENGTH_SHORT).show();
            }
        }
    }
}