package com.waspy.sayekti.waspy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.waspy.sayekti.waspy.R;
import com.waspy.sayekti.waspy.db.Doo;
import com.waspy.sayekti.waspy.service.NotificationService;

import java.util.UUID;

import io.realm.Realm;

/**
 * inspirated from :
 * https://www.learn2crack.com/2014/11/reading-notification-using-notificationlistenerservice.html
 */


public class MainActivity extends AppCompatActivity {

    private Button btnMain, btnShowLog, btnSetting;
    private boolean isRunning = false;
    private Realm realm;

    private DialogInterface.OnClickListener dlgStartServiceListener =
            new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        isLoginValid();

        btnMain = (Button) findViewById(R.id.btnMain);
        btnSetting = (Button) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });

        btnShowLog = (Button) findViewById(R.id.btnShowLog);
        btnShowLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogActivity.class));
            }
        });

        String label = (isRunning)?  "STOP" : "START";
        btnMain.setText(label);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    if (NotificationService.isNotificationAccessEnabled()) {
                        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(onNotice, new IntentFilter("Msg"));
                        btnMain.setText("STOP");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("You must allow this app on Notification Access before start service.");
                        builder.setPositiveButton("Yes", dlgStartServiceListener);
                        builder.setNegativeButton("No", dlgStartServiceListener);
                        builder.show();
                    }
                } else {
                    LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(onNotice);
                    isRunning = false;
                    btnMain.setText("START");
                }
            }
        });
    }

    private void isLoginValid(){
        final boolean[] isValid = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your Password");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString().trim().toLowerCase();
                isValid[0] = m_Text.equalsIgnoreCase("rahasia");
                if (isValid[0] == true){
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    isLoginValid();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isRunning) isRunning = true;
            final String pack = intent.getStringExtra("package");
            final String title = intent.getStringExtra("title");
            final String ticker = intent.getStringExtra("ticker");
            final String text = intent.getStringExtra("text");

            if (pack.equalsIgnoreCase("com.whatsapp")) {

                /**
                 * This is just an example, incoming messages are stored in the realm database.
                 * You can also experiment for example incoming messages are sent to your email as a spy.
                 */

                realm.beginTransaction();
                Doo doo = realm.createObject(Doo.class, UUID.randomUUID().toString());
                doo.setPackageName(pack);
                doo.setTitle(title);
                doo.setTicker(ticker);
                doo.setText(text);
                realm.commitTransaction();

            }
        }
    };
}
