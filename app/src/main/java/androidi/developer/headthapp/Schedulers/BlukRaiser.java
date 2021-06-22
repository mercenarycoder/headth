package androidi.developer.headthapp.Schedulers;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidi.developer.headthapp.ApiMethods.JsonParser;
import androidi.developer.headthapp.ApiMethods.networkData;

import com.google.firebase.auth.FirebaseAuth;

public class BlukRaiser  extends JobService {
    public static final String TAG="BulkRaiser";
    private boolean jobCancelled=false;
    private String callers[];
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    private int status=0;
    JobParameters params;
    Context context;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        context=getApplicationContext();
        callers=jobParameters.getExtras().getString("caller").split(";");

        params=jobParameters;
        new raiseBulk().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: Job is cancelled before completion");
        jobCancelled=true;
        new raiseBulk().cancel(true);
        return true;
    }
    private class raiseBulk extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=new networkData().url+new networkData().raiseAlarm;
            JsonParser jsonParser=new JsonParser();
            String number=mauth.getCurrentUser().getPhoneNumber();
            number=number.substring(3,number.length());
            String data=null;
            for(int i=0;i<callers.length;i++) {
                data=new JsonParser().raiseAlarm(url,number,callers[i]);
            }
            Log.d(TAG, "doInBackground: "+data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                jobFinished(params,false);
                Toast.makeText(context,"Bulk request raised",Toast.LENGTH_SHORT).show();
            }
            else{
                jobFinished(params,false);
                Toast.makeText(context,"Bulk request failed",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
