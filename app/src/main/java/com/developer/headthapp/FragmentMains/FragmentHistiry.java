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

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.HealthCart;
import com.developer.headthapp.Prescription.Prescriptions;
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

public class FragmentHistiry extends Fragment{
Context context;
RecyclerView history;
SwipeRefreshLayout refresh_history;
ArrayList<typeClass> list;
dialogRecyler adapter;
DeleteClass dd=new DeleteClass("fbf");
Button add,remove;
FirebaseAuth mauth=FirebaseAuth.getInstance();
String titleF,descriptionF;
ImageButton close_btn;
ProgressBar progress;
public static boolean check=false;
TextView sabchanga;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
       // formList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        history=(RecyclerView)view.findViewById(R.id.history);
        progress=(ProgressBar)view.findViewById(R.id.progress);
        sabchanga=(TextView)view.findViewById(R.id.sabchanga);
        add=(Button)view.findViewById(R.id.add);
        remove=(Button)view.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dd.listD2.size()>0) {
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
        refresh_history=(SwipeRefreshLayout)view.findViewById(R.id.refresh_history);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShower();
            }
        });

        refresh_history.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_history.setRefreshing(false);
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

    @Override
    public void onResume() {
        super.onResume();
        if(check) {
            Toast.makeText(context,String.valueOf(check),Toast.LENGTH_SHORT).show();
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            new getDieseas().execute();
        }
    }

    Dialog dialog;
    public void dialogShower()
    {
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_history);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        final EditText title=(EditText)dialog.findViewById(R.id.title);
        final EditText description=(EditText)dialog.findViewById(R.id.description);
        Button add=(Button)dialog.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleF=title.getText().toString();
                descriptionF=description.getText().toString();
                if(titleF.isEmpty()||descriptionF.isEmpty())
                {
                    Toast.makeText(context,"Please fill all the details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new addAllergy().execute();
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

    public class deleteItems extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().deleteHistory;
            ArrayList<String> arr= dd.listD2;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().deleteItems(url,arr,number);
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
    public class addAllergy extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().historyAdd;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().addHistory(url,number,titleF,descriptionF);
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
                    builder.setTitle("History")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    titleF="";
                                    descriptionF="";
                                    new getDieseas().execute();
                                    dialog.dismiss();
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
            String url=new networkData().url+new networkData().getHistory;
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
                                String name = object1.getString("title");
                                String id = object1.getString("id");
                                String details = object1.getString("description");
                                list.add(new typeClass(name, "history", details, "", "",id));
                            }
                            adapter = new dialogRecyler(list, context);
                            history.setLayoutManager(new LinearLayoutManager(context));
                            history.setHasFixedSize(true);
                            history.setAdapter(adapter);
                            history.setVisibility(View.VISIBLE);
                            sabchanga.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            history.setVisibility(View.INVISIBLE);
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

}
