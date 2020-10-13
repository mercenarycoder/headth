package com.developer.headthapp.FragmentMains;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.HealthCart;
import com.developer.headthapp.R;
import com.developer.headthapp.typeClass;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FragmentAllergies extends Fragment
{
    Context context;
    RecyclerView alles;
    SwipeRefreshLayout refresh_alles;
    ArrayList<typeClass> list;
    dialogRecyler adapter;
    DeleteClass dd=new DeleteClass("fbf");
    Button add,remove;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    ImageButton close_btn;
    ProgressBar progress;
    TextView sabchanga;
    String allergiesF,triggersF;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    context=getContext();
    //formList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allergies_fragment, container, false);
        alles=(RecyclerView)view.findViewById(R.id.alles);
        refresh_alles=(SwipeRefreshLayout)view.findViewById(R.id.refresh_alles);
        add=(Button)view.findViewById(R.id.add);
        remove=(Button)view.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new deleteItems().execute();
            }
        });
        progress=(ProgressBar)view.findViewById(R.id.progress);
        sabchanga=(TextView)view.findViewById(R.id.sabchanga);
        remove=(Button)view.findViewById(R.id.remove);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShower();
            }
        });
        refresh_alles.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_alles.setRefreshing(false);
                new getDieseas().execute();
            }
        });
        close_btn=(ImageButton)view.findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HealthCart();
                HealthCart.changeVisiblity();
            }
        });
        new getDieseas().execute();
        return view;
    }
    Dialog dialog;
    public void dialogShower()
    {
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_allergies);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        final EditText allergies=(EditText)dialog.findViewById(R.id.allergy);
        final EditText triggers=(EditText)dialog.findViewById(R.id.triggers);
        Button add=(Button)dialog.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allergiesF=allergies.getText().toString();
                triggersF=triggers.getText().toString();
                if(!allergiesF.isEmpty()&&!triggersF.isEmpty()) {
                    new addAllergy().execute();
                    //dialog.dismiss();
                }
                else
                {
               Toast.makeText(context,"Please fill in the details correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public class addAllergy extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().allergyAdd;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().addAllergy(url,number,allergiesF,triggersF);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result!=null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    final String responce2=String.valueOf(jsonObject.get("msg"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                   allergiesF="";
                                   triggersF="";
                                    dialog.dismiss();
                                    new getDieseas().execute();
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
    public class getDieseas extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getAllergy;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().viewOffer(url,number);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.INVISIBLE);
            if(s!=null)
            {
                try{
                    JSONObject object = new JSONObject(s);
                    String status=object.getString("status");
                    if(status.equals("1"))
                    {
                        list=new ArrayList<>();
                        JSONArray array=object.getJSONArray("data");
                        if(array.length()>0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);
                                String name = object1.getString("allergy");
                                String id = object1.getString("id");
                                String details = object1.getString("triggers");
                                list.add(new typeClass(name, "allergies", details, "", "",id));
                            }
                            adapter = new dialogRecyler(list, context);
                            alles.setLayoutManager(new LinearLayoutManager(context));
                            alles.setHasFixedSize(true);
                            alles.setAdapter(adapter);
                        }
                        else
                        {
                            sabchanga.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        Toast.makeText(context,"Some error in request",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    sabchanga.setVisibility(View.VISIBLE);
                }
            }
            else
            {

            }
        }
    }
    public class deleteItems extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteAllergy;
            ArrayList<String> arr= dd.listD2;
            String json=new JsonParser().deleteItems(url,arr);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.INVISIBLE);
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
                                    new getDieseas().execute();
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
}
