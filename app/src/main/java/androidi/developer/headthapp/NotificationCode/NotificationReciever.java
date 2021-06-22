package androidi.developer.headthapp.NotificationCode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidi.developer.headthapp.Covid.CovidCaller;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context, CovidCaller.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent1);
        Toast.makeText(context,"Please Help if you can",Toast.LENGTH_SHORT).show();
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.cancelAll();
    }
}
