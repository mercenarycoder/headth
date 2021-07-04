package androidi.developer.headthapp.Report;

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.networkData;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidi.developer.headthapp.DeleteClass;

//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
import androidi.developer.headthapp.bundleReport;
import androidi.developer.headthapp.reportClass;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class ReportActivity extends AppCompatActivity {
ImageButton back;
ArrayList<bundleReport> list2;
ArrayList<reportClass> list;
ArrayList<reportOf3> list3;
RecyclerView previous_report;
dateReportAdapter adapter;
HashMap<String,ArrayList<reportClass>> matcher;
Context context;
TextView nop;
static boolean adding=false;
DeleteClass dd=new DeleteClass("fbfd");
ProgressDialog progressDialog;
SwipeRefreshLayout refresh;
FirebaseAuth mauth=FirebaseAuth.getInstance();
Button add_report,remove_report,previous;
    SharedPreferences testCheck;
    SharedPreferences.Editor testMake;
ImageButton filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        back=(ImageButton)findViewById(R.id.back);
        context=ReportActivity.this;
        testCheck=getSharedPreferences("TestCheck",Context.MODE_PRIVATE);
        testMake=testCheck.edit();
        //previous button functionality
        previous=(Button)findViewById(R.id.previous);
        previous.setVisibility(View.INVISIBLE);
        previous.setEnabled(false);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getReports().execute();
                previous.setVisibility(View.INVISIBLE);
                previous.setEnabled(false);
            }
        });
        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                new getReports().execute();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        formList();
        filter=(ImageButton)findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShower();
            }
        });
        add_report=(Button)findViewById(R.id.add_report);
        remove_report=(Button)findViewById(R.id.remove_report);
        add_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adding=true;
                Intent intent=new Intent(ReportActivity.this, ReportAdd.class);
                startActivity(intent);
            }
        });
        remove_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dd.listD.size()>0) {
                    Dialog dialog = new Dialog(context, 0);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_delete);
                    Button yes = dialog.findViewById(R.id.yes);
                    Button no = dialog.findViewById(R.id.no);
                    ImageButton close_btn2 = dialog.findViewById(R.id.close_btn2);
                    close_btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            new deleteItems().execute();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else
                {
                    Toast.makeText(context,"No items selected to be deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
        context=ReportActivity.this;
        previous_report=(RecyclerView)findViewById(R.id.previous_reports);
        nop=(TextView)findViewById(R.id.nop);
        nop.setVisibility(View.INVISIBLE);
        new getReports().execute();
    }
    public void dialogShower()
    {
        final Dialog dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_filter);
        LinearLayout eye=(LinearLayout)dialog.findViewById(R.id.eye);
        LinearLayout ent=(LinearLayout)dialog.findViewById(R.id.ent);
        LinearLayout blood=(LinearLayout)dialog.findViewById(R.id.blood);
        LinearLayout dental =(LinearLayout)dialog.findViewById(R.id.dental);
        LinearLayout xray=(LinearLayout)dialog.findViewById(R.id.xray);
        LinearLayout all=(LinearLayout)dialog.findViewById(R.id.all);
        TextView latest=(TextView)dialog.findViewById(R.id.latest);
        TextView oldest=(TextView)dialog.findViewById(R.id.oldest);
        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getReports().execute();
                dialog.dismiss();
            }
        });
        oldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getReverseReports().execute();
                dialog.dismiss();
            }
        });
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformList("eye");
                dialog.dismiss();
            }
        });
        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformList("ent");
                dialog.dismiss();
            }
        });
        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformList("blood");
                dialog.dismiss();
            }
        });
        dental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformList("dental");
                dialog.dismiss();
            }
        });
        xray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformList("xray");
                dialog.dismiss();
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transformList("");
                dialog.dismiss();
            }
        });
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_dialog);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void formList()
    {

    }
    @Override
    protected void onResume() {
        if(adding)
        {
            adding=false;
        }
        new getReports().execute();
        super.onResume();
    }
    public class deleteItems extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteReport;
            ArrayList<String> arr= new ArrayList<>();
            ArrayList<String> arr2= new ArrayList<>();
            HashMap mapper=dd.listD;
            Iterator<Map.Entry<String,String>> it=mapper.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
                arr.add(pair.getKey());
                arr2.add(pair.getValue());
            }
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
            String json=new JsonParser().deleteBigItems(url,arr,arr2,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    String status = jsonObject.getString("status");
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dd=new DeleteClass("fdd");
                                    new getReports().execute();
                                }
                            });
                    builder.create();
                    builder.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
public void transformList(String str)
{
    list2=new ArrayList<>();
    for(Map.Entry<String,ArrayList<reportClass>> entry:matcher.entrySet())
    {
        String main=entry.getKey();
        ArrayList<reportClass> ll=entry.getValue();
        boolean dateC=false;
        list3=new ArrayList<>();
        for(int i=0;i<ll.size();i=i+3)
        {
            reportClass m1[] = new reportClass[3];
            int x=0;
            boolean ff=false;
            if(ll.get(i).getCats().contains(str))
            {
                m1[x]=ll.get(i);
                ff=true;
                x++;
            }

            reportClass  m3 = new reportClass("null","null","","","","","","");
            if(i+1<ll.size()&&ll.get(i+1).getCats().contains(str)) {
                m1[x] = ll.get(i + 1);
                ff=true;
                x++;
            }

            if(i+2<ll.size()&&ll.get(i).getCats().contains(str)) {
                m1[x] = ll.get(i + 1);
                ff=true;
                x++;
            }
            if(ff) {
                dateC=true;
                if(x==1) {
                    list3.add(new reportOf3(m1[0], m3, m3));
                }
                if(x==2) {
                    list3.add(new reportOf3(m1[0], m1[1], m3));
                }
                if(x==3)
                {
                    list3.add(new reportOf3(m1[0],m1[1],m1[2]));
                }
            }
        }
        if(dateC)
        list2.add(new bundleReport(main,list3));
    }
    previous_report.setLayoutManager(new LinearLayoutManager(context));
    previous_report.setHasFixedSize(true);
    adapter=new dateReportAdapter(list2,context);
    previous_report.setAdapter(adapter);
    if(list2.size()>0)
    {
        nop.setText("Searched for "+str);
        nop.setVisibility(View.INVISIBLE);
        previous.setVisibility(View.INVISIBLE);
    }
    else
    {

        nop.setText("Searched for "+str);
        nop.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        previous.setEnabled(true);
    }
}
public class getReverseReports extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Sending information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        new networkData();
        String base= networkData.url;
        String method=networkData.getReverseReports;
        String url=base+method;
        String logInfo=testCheck.getString("LogInfo","No");
        String number="";
        if(mauth.getCurrentUser()!=null) {
            number = mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
        }
        else if(logInfo.equals("Yes")) {
            number="1234567890";
        }
        String uploadId= UUID.randomUUID().toString();
        String json=new JsonParser().viewOffer(url,number);
        return json;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if(null!=result)
        {
            try
            {
                list=new ArrayList<>();
                JSONObject jsonObject = new JSONObject(result);
                matcher=new HashMap<>();
                final String responce = String.valueOf(jsonObject.get("status"));
                // final String responce2=String.valueOf(jsonObject.get("msg"));
                if(responce.equals("1"))
                {
                    JSONArray data=jsonObject.getJSONArray("data");
                    for(int i=0;i<data.length();i++)
                    {
                        JSONObject object = data.getJSONObject(i);
                        String title=object.getString("title");
                        String doctor=object.getString("observer");
                        String observation=object.getString("details");
                        String date[]=object.getString("date").split("T");
                        String id=object.getString("id");
                        String type = object.getString("type");
                        String image=object.getString("link");
                        String category=object.getString("category");
                        list.add(new reportClass(doctor,title,date[0],id,image,type,category,observation));
                        if(matcher.containsKey(date[0]))
                        {
                            ArrayList<reportClass> ll=matcher.get(date[0]);
                            ll.add(new reportClass(doctor,title,date[0],id,image,type,category,observation));
                        }
                        else
                        {
                            ArrayList<reportClass> ll=new ArrayList<>();
                            ll.add(new reportClass(doctor,title,date[0],id,image,type,category,observation));
                            matcher.put(date[0],ll);
                        }
                    }
                    list2=new ArrayList<>();
                    for(Map.Entry<String,ArrayList<reportClass>> entry:matcher.entrySet())
                    {
                        String main=entry.getKey();
                        ArrayList<reportClass> ll=entry.getValue();
                        list3=new ArrayList<>();
                        for(int i=0;i<ll.size();i=i+3)
                        {
                            reportClass m1=ll.get(i);
                            reportClass m2,m3;
                            if(i+1<ll.size()) {
                                m2 = ll.get(i + 1);
                            }
                            else
                            {
                                m2 = new reportClass("null","null","","","","","","");
                            }
                            if(i+2<ll.size()) {
                                m3 = ll.get(i + 1);
                            }
                            else
                            {
                                m3 = new reportClass("null","null","","","","","","");
                            }
                            list3.add(new reportOf3(m1,m2,m3));
                        }
                        list2.add(new bundleReport(main,list3));
                    }
                    Collections.sort(list2, new Comparator<bundleReport>() {
                        public int compare(bundleReport m1, bundleReport m2) {
                            return m1.getDate().compareTo(m2.getDate());
                        }
                    });
                    previous_report.setLayoutManager(new LinearLayoutManager(context));
                    previous_report.setHasFixedSize(true);
                    adapter=new dateReportAdapter(list2,context);
                    previous_report.setAdapter(adapter);
                    if(list2.size()>0)
                    {
                        nop.setVisibility(View.INVISIBLE);
                        previous.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        nop.setText("No reports are their");
                        nop.setVisibility(View.VISIBLE);
                        previous.setVisibility(View.VISIBLE);
                        previous.setEnabled(true);
                    }
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
            catch (JSONException e) {
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
                catch (Exception r)
                {

                }
            }
        }
        //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(context,"please check details and try again",Toast.LENGTH_SHORT).show();
        }

    }
 }

    public class getReports extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Sending information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            new networkData();
            String base= networkData.url;
            String method=networkData.getreport;
            String url=base+method;
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
            String uploadId= UUID.randomUUID().toString();
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if(null!=result)
            {
                try
                {
                    list=new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    matcher=new HashMap<>();
                    final String responce = String.valueOf(jsonObject.get("status"));
                    // final String responce2=String.valueOf(jsonObject.get("msg"));
                    if(responce.equals("1"))
                    {
                        JSONArray data=jsonObject.getJSONArray("data");
                        for(int i=0;i<data.length();i++)
                        {
                            JSONObject object = data.getJSONObject(i);
                            String title=object.getString("title");
                            String doctor=object.getString("observer");
                            String observation=object.getString("details");
                            String date[]=object.getString("date").split("T");
                            String id=object.getString("id");
                            String type = object.getString("type");
                            String image=object.getString("link");
                            String category=object.getString("category");
                            list.add(new reportClass(doctor,title,date[0],id,image,type,category,observation));
                            if(matcher.containsKey(date[0]))
                            {
                                ArrayList<reportClass> ll=matcher.get(date[0]);
                                ll.add(new reportClass(doctor,title,date[0],id,image,type,category,observation));
                            }
                            else
                            {
                                ArrayList<reportClass> ll=new ArrayList<>();
                                ll.add(new reportClass(doctor,title,date[0],id,image,type,category,observation));
                                matcher.put(date[0],ll);
                            }
                        }
                        list2=new ArrayList<>();
                        for(Map.Entry<String,ArrayList<reportClass>> entry:matcher.entrySet())
                       {
                        String main=entry.getKey();
                        ArrayList<reportClass> ll=entry.getValue();
                        list3=new ArrayList<>();
                        for(int i=0;i<ll.size();i=i+3)
                        {
                                reportClass m1=ll.get(i);
                                reportClass m2,m3;
                                if(i+1<ll.size()) {
                                    m2 = ll.get(i + 1);
                                }
                                else
                                {
                                m2 = new reportClass("null","null","","","","","","");
                                }
                            if(i+2<ll.size()) {
                                m3 = ll.get(i + 1);
                            }
                            else
                            {
                                m3 = new reportClass("null","null","","","","","","");
                            }
                            list3.add(new reportOf3(m1,m2,m3));
                        }
                        list2.add(new bundleReport(main,list3));
                       }
//                        for sorting the reports in ascending order
                        Collections.sort(list2, new Comparator<bundleReport>() {
                            public int compare(bundleReport m1, bundleReport m2) {
                                return m1.getDate().compareTo(m2.getDate());
                            }
                        });
                        ArrayList<bundleReport> list_s=new ArrayList<>();
                        for(int i=list2.size()-1;i>=0;i--){
                            list_s.add(list2.get(i));
                        }
//                        list2.sort(Collections.reverseOrder());
//                        for sorting them in lastest to oldest fashion
//                        Collections.sort(list2,Collections.reverseOrder());
                        previous_report.setLayoutManager(new LinearLayoutManager(context));
                        previous_report.setHasFixedSize(true);
                        adapter=new dateReportAdapter(list_s,context);
                        previous_report.setAdapter(adapter);
                        if(list2.size()>0)
                        {
                         nop.setVisibility(View.INVISIBLE);
                         previous.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                        nop.setText("No reports are there");
                        nop.setVisibility(View.VISIBLE);
                        previous.setVisibility(View.VISIBLE);
                        previous.setEnabled(true);
                        }
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
                catch (JSONException e) {
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
                    catch (Exception r)
                    {

                    }
                }
            }
            //Toast.makeText(signup_Activity.this, "something missing", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(context,"please check details and try again",Toast.LENGTH_SHORT).show();
            }

        }
    }
}