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

public class MedicineFragment extends Fragment {
    Context context;
    RecyclerView meds;
    ArrayList<typeClass> list;
    SwipeRefreshLayout refresh_meds;
    dialogRecyler adapter;
    DeleteClass dd=new DeleteClass("fbf");
    Button add,remove;
    ImageButton close_btn;
    ProgressBar progress;
    TextView sabchanga;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    String nameF,purposeF,dosageF,durationF;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
       // formList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medicine_fragment, container, false);
        meds=(RecyclerView)view.findViewById(R.id.meds);
        refresh_meds=(SwipeRefreshLayout)view.findViewById(R.id.refresh_meds);
        remove=(Button)view.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new deleteItems().execute();
            }
        });
        add=(Button)view.findViewById(R.id.add);
        progress=(ProgressBar)view.findViewById(R.id.progress);
        sabchanga=(TextView)view.findViewById(R.id.sabchanga);
        remove=(Button)view.findViewById(R.id.remove);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialogShower();
            }
        });

        refresh_meds.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_meds.setRefreshing(false);
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
        dialog.setContentView(R.layout.dialog_medicine);
        final EditText name=(EditText)dialog.findViewById(R.id.name);
        final EditText purpose=(EditText)dialog.findViewById(R.id.purpose);
        final EditText dosage=(EditText)dialog.findViewById(R.id.dosage);
        final EditText duration=(EditText)dialog.findViewById(R.id.duration);
        Button add=(Button)dialog.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              nameF=name.getText().toString();
              purposeF=purpose.getText().toString();
              dosageF=dosage.getText().toString();
              durationF=duration.getText().toString();
              if(nameF.isEmpty())
              {
                  Toast.makeText(context,"Please fill the name",Toast.LENGTH_SHORT).show();
                  return;
              }
              else if(purposeF.isEmpty())
              {
                  Toast.makeText(context,"Please fill the purpose",Toast.LENGTH_SHORT).show();
                  return;
              }
              else if(dosageF.isEmpty())
              {
                  Toast.makeText(context,"Please fill the dosage",Toast.LENGTH_SHORT).show();
                  return;
              }
              else if(durationF.isEmpty())
              {
                  Toast.makeText(context,"Please fill the duration",Toast.LENGTH_SHORT).show();
                  return;
              }
              else
              {
                  new addAllergy().execute();
              }
            }
        });
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
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
            String url=new networkData().url+new networkData().deleteMedicine;
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
            String url=new networkData().url+new networkData().medicineAdd;
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String json=new JsonParser().addMedicine(url,number,nameF,purposeF,durationF,dosageF);
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
                                    nameF="";
                                    purposeF="";
                                    dosageF="";
                                    durationF="";
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
            String url=new networkData().url+new networkData().getMedicine;
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
                                String name = object1.getString("name");
                                String id = object1.getString("id");
                                String purpose=object1.getString("purpose");
                                String duration = object1.getString("duration");
                                String dosage = object1.getString("dosage");
                                list.add(new typeClass(name, "medicine", purpose, "Duration in months "+duration,
                                        "Dosage of pills a day "+dosage,id));
                            }
                            adapter = new dialogRecyler(list, context);
                            meds.setLayoutManager(new LinearLayoutManager(context));
                            meds.setHasFixedSize(true);
                            meds.setAdapter(adapter);
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

}
