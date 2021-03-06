package androidi.developer.headthapp.FragmentMains;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.networkData;
import androidi.developer.headthapp.DeleteClass;
import androidi.developer.headthapp.HealthCart;
//import com.developer.headthapp.R;
import androidi.developer.headthapp.R;
import androidi.developer.headthapp.typeClass;
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

public class DiseaseFragment extends Fragment {
ArrayList<typeClass> dis;
Context context;
RecyclerView dis2;
SwipeRefreshLayout refresh_dis;
dialogRecyler adapter;
Button add,remove;
DeleteClass dd=new DeleteClass("fbf");
FirebaseAuth mauth;
ProgressBar progress;
TextView sabchanga;
String nameF="",detailF="";
SharedPreferences testCheck;
SharedPreferences.Editor editor,testMake;
ImageButton close_btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        //formList();

        mauth=FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.disease_fragment, container, false);
      add=(Button)view.findViewById(R.id.add);
      remove=(Button)view.findViewById(R.id.remove);
        testCheck=context.getSharedPreferences("TestCheck",Context.MODE_PRIVATE);
        testMake=testCheck.edit();
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
      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dialogShower();
          }
      });
      dis2=(RecyclerView)view.findViewById(R.id.dis);
      progress=(ProgressBar)view.findViewById(R.id.progress);
      sabchanga=(TextView)view.findViewById(R.id.sabchanga);
      refresh_dis=(SwipeRefreshLayout)view.findViewById(R.id.refresh_dis);
      new getDieseas().execute();
      refresh_dis.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
              refresh_dis.setRefreshing(false);
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
      return view;
    }
    Dialog dialog;
    public void dialogShower()
    {
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_deiseas);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        final EditText name=(EditText)dialog.findViewById(R.id.name);
        final EditText detail=(EditText)dialog.findViewById(R.id.detail);
        Button add_d=(Button)dialog.findViewById(R.id.add_d);
        add_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameF=name.getText().toString();
                detailF=detail.getText().toString();
                if(!nameF.isEmpty()&&!detailF.isEmpty())
                {
                    new addAllergy().execute();
                }
                else
                {
                    Toast.makeText(context,"Please Fill in the details properly",Toast.LENGTH_SHORT).show();
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


    public class getDieseas extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().getDieseas;
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
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
                     dis=new ArrayList<>();
                     JSONArray array=object.getJSONArray("data");
                     if(array.length()>0) {
                         for (int i = 0; i < array.length(); i++) {
                             JSONObject object1 = array.getJSONObject(i);
                             String name = object1.getString("name");
                             String id = object1.getString("id");
                             String details = object1.getString("details");
                             dis.add(new typeClass(name, "disease", details, "", "",id));
                         }
                         adapter = new dialogRecyler(dis, context);
                         dis2.setLayoutManager(new LinearLayoutManager(context));
                         dis2.setHasFixedSize(true);
                         dis2.setAdapter(adapter);
                         dis2.setVisibility(View.VISIBLE);
                         sabchanga.setVisibility(View.INVISIBLE);
                     }
                     else
                     {

                         dis2.setVisibility(View.INVISIBLE);
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
            String url=new networkData().url+new networkData().deleteDieseas;
            ArrayList<String> arr= dd.listD2;
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            System.out.println("arr-------------------------"+arr.toString());
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
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
            String url=new networkData().url+new networkData().dieseasAdd;
            String logInfo=testCheck.getString("LogInfo","No");
            String number="";
            if(mauth.getCurrentUser()!=null) {
                number = mauth.getCurrentUser().getPhoneNumber();
                number=number.substring(3,number.length());
            }
            else if(logInfo.equals("Yes")) {
                number="1234567890";
            }
            String json=new JsonParser().addDieseas(url,number,nameF,detailF);
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
                    builder.setTitle("Dieseas")
                            .setMessage(responce2)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                          nameF="";
                          detailF="";
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
}
