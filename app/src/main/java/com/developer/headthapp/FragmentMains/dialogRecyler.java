package com.developer.headthapp.FragmentMains;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.headthapp.ApiMethods.JsonParser;
import com.developer.headthapp.ApiMethods.networkData;
import com.developer.headthapp.DeleteClass;
import com.developer.headthapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.developer.headthapp.typeClass;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class dialogRecyler extends RecyclerView.Adapter<dialogRecyler.viewholder1>{
    ArrayList<typeClass> list;
    Context context;
    int i=0;
    DeleteClass dd=new DeleteClass();
    String allergiesF,triggersF,idF,titleF,descriptionF,nameF,detailF,purposeF,dosageF,durationF;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    public dialogRecyler(ArrayList<typeClass> list, Context context)
    {
        this.list=list;
        this.context=context;

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.dialog_item_recycler, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final typeClass adapter=list.get(position);
        if(adapter.getType().equals("disease"))
        {
         if(adapter.getTitle().length()<=14) {
             holder.name.setText(adapter.getTitle());
         }
         else{
             holder.name.setText(adapter.getTitle().substring(0,14));
         }
         holder.thing1.setText(adapter.getThing1());
            holder.thing2.setHeight(0);
            holder.thing3.setHeight(0);
         holder.thing2.setText(adapter.getThing2());
         holder.thing3.setText(adapter.getThing3());
         holder.edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 i=3;
                 dialogShower3(adapter.getTitle(),adapter.getThing1(),adapter.getId());
             }
         });
        }
        if(adapter.getType().equals("medicine"))
        {
            if(adapter.getTitle().length()<=14) {
                holder.name.setText(adapter.getTitle());
            }
            else{
                holder.name.setText(adapter.getTitle().substring(0,14));
            }
            holder.thing1.setText(adapter.getThing1());
            holder.thing2.setText(adapter.getThing2());
            holder.thing3.setText(adapter.getThing3());
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(context,"medicine",Toast.LENGTH_SHORT).show();
                i=4;
    dialogShower4(adapter.getTitle(),adapter.getThing1(),adapter.getThing2(),adapter.getThing3(),adapter.getId());
                }
            });
        }
        if(adapter.getType().equals("allergies"))
        {

            if(adapter.getTitle().length()<=14) {
                holder.name.setText(adapter.getTitle());
            }
            else{
                holder.name.setText(adapter.getTitle().substring(0,14));
            }
            holder.thing1.setText(adapter.getThing1());
            holder.thing2.setText("");
            holder.thing2.setHeight(0);
            holder.thing3.setHeight(0);
            holder.thing3.setText("");
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    i=1;
                   dialogShower(adapter.getTitle(),adapter.getThing1(),adapter.getId());
                }
            });
        }
        if(adapter.getType().equals("history"))
        {
            if(adapter.getTitle().length()<=14) {
                holder.name.setText(adapter.getTitle());
            }
            else{
                holder.name.setText(adapter.getTitle().substring(0,14));
            }
            holder.thing1.setText(adapter.getThing1());
            holder.thing2.setText(adapter.getThing2());
            holder.thing3.setText(adapter.getThing3());
            holder.thing2.setHeight(0);
            holder.thing3.setHeight(0);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context,"history",Toast.LENGTH_SHORT).show();
                    i=2;
                    dialogShower2(adapter.getTitle(),adapter.getThing1(),adapter.getId());
                }
            });
        }
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.check.setVisibility(View.VISIBLE);
                holder.visible.setVisibility(View.VISIBLE);
                holder.check.setChecked(true);
                dd.listD2.add(adapter.getId());
                return false;
            }
        });
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    holder.check.setVisibility(View.VISIBLE);
                    holder.check.setChecked(true);
                    holder.visible.setVisibility(View.VISIBLE);
                    dd.listD2.add(adapter.getId());

                }
                else
                {
                    holder.check.setVisibility(View.INVISIBLE);
                    holder.visible.setVisibility(View.INVISIBLE);
                    for(int i=0;i<dd.listD2.size();i++)
                    {
                        String str=dd.listD2.get(i);
                        if(str.equals(adapter.getId()))
                        {
                            dd.listD2.remove(i);
                        }
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView name,thing1,thing2,thing3;
        Button edit;
        CheckBox check;
        RelativeLayout layout;
        TextView visible;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            edit=(Button)itemView.findViewById(R.id.edit);
            thing1=(TextView)itemView.findViewById(R.id.thing1);
            thing2=(TextView)itemView.findViewById(R.id.thing2);
            thing3=(TextView)itemView.findViewById(R.id.thing3);
            check=(CheckBox)itemView.findViewById(R.id.check);
            visible=(TextView)itemView.findViewById(R.id.visibile);
            layout=(RelativeLayout)itemView.findViewById(R.id.layout);
        }
    }
    Dialog dialog;
    public void dialogShower(String allergies2,String triggers2,String id)
    {
        idF=id;
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_allergies);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        final EditText allergies=(EditText)dialog.findViewById(R.id.allergy);
        allergies.setText(allergies2);
        final EditText triggers=(EditText)dialog.findViewById(R.id.triggers);
        triggers.setText(triggers2);
        Button add=(Button)dialog.findViewById(R.id.add);
        add.setText("Update");
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
    public void dialogShower2(String title2,String description2,String id) {
        dialog = new Dialog(context, 0);
        idF=id;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_history);
        ImageButton close_btn2 = (ImageButton) dialog.findViewById(R.id.close_btn2);
        close_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final EditText title = (EditText) dialog.findViewById(R.id.title);
        title.setText(title2);
        final EditText description = (EditText) dialog.findViewById(R.id.description);
        description.setText(description2);
        Button add = (Button) dialog.findViewById(R.id.add);
        add.setText("Update");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleF = title.getText().toString();
                descriptionF = description.getText().toString();
                if (titleF.isEmpty() || descriptionF.isEmpty()) {
                    Toast.makeText(context, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(context,String.valueOf(FragmentHistiry.check),Toast.LENGTH_SHORT).show();
                    new addAllergy().execute();
                }
            }
        });
        dialog.show();
    }
    public void dialogShower3(String name2,String detail2,String id)
    {
        idF=id;
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_deiseas);
        ImageButton close_btn2=(ImageButton)dialog.findViewById(R.id.close_btn2);
        final EditText name=(EditText)dialog.findViewById(R.id.name);
        name.setText(name2);
        final EditText detail=(EditText)dialog.findViewById(R.id.detail);
        detail.setText(detail2);
        Button add_d=(Button)dialog.findViewById(R.id.add_d);
        add_d.setText("Update");
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
    public void dialogShower4(String name2,String purpose2,String dosage2,String duration2,String id)
    {
        idF=id;
        dialog=new Dialog(context, 0);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_medicine);
        final EditText name=(EditText)dialog.findViewById(R.id.name);
        name.setText(name2);
        final EditText purpose=(EditText)dialog.findViewById(R.id.purpose);
        purpose.setText(purpose2);
        final EditText dosage=(EditText)dialog.findViewById(R.id.dosage);
        String dos[]=dosage2.split(" ");
        dosage.setText(dos[dos.length-1]);
        final EditText duration=(EditText)dialog.findViewById(R.id.duration);
        String dus[]=duration2.split(" ");
        duration.setText(dus[dus.length-1]);
        Button add=(Button)dialog.findViewById(R.id.add);
        add.setText("Update");
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
    public class addAllergy extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url,json = null;
            switch (i)
            {
                case 1:
                {
                    url=new networkData().url+new networkData().updateAllergy;
                    String number=mauth.getCurrentUser().getPhoneNumber();
                    number=number.substring(3,number.length());
                    json=new JsonParser().updateAllergy(url,idF,allergiesF,triggersF,number);
                    break;
                }
                case 2:
                {
                    url=new networkData().url+new networkData().updateHistory;
                    String number=mauth.getCurrentUser().getPhoneNumber();
                    number=number.substring(3,number.length());
                    json=new JsonParser().updateHistory(url,idF,titleF,descriptionF,number);
                    break;
                }
                case 3:
                {
                    url=new networkData().url+new networkData().updateDieseas;
                    String number=mauth.getCurrentUser().getPhoneNumber();
                    number=number.substring(3,number.length());
                    json=new JsonParser().updateDieseas(url,idF,nameF,detailF,number);
                    break;
                }
                case 4:
                {
                    url=new networkData().url+new networkData().updateMedicine;
                    String number=mauth.getCurrentUser().getPhoneNumber();
                    number=number.substring(3,number.length());
                    json=new JsonParser().updateMedicine(url,idF,nameF,purposeF,durationF,dosageF,number);
                    break;
                }
                default:
                {
                    Toast.makeText(context,"Empty Request",Toast.LENGTH_SHORT).show();
                }
            }
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
                                    new FragmentHistiry();
                                    FragmentHistiry.check =true;

//                                    FragmentHistiry ff= new FragmentHistiry("dfdfd");
//                                    FragmentHistiry.getDieseas.execute();
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
    public void setProgress()
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    ProgressDialog progressDialog;
}

