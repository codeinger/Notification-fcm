package com.codeinger.notification_fcm.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codeinger.notification_fcm.R;
import com.codeinger.notification_fcm.model.NotificationReq;
import com.codeinger.notification_fcm.model.NotificationResponce;
import com.codeinger.notification_fcm.network.NotificationRequest;
import com.codeinger.notification_fcm.network.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codeinger.notification_fcm.network.Constants.BASE_URL;

public class SendNotificationActivity extends AppCompatActivity {

    private EditText title,description;
    private Button send;
    private DialogPlus dialogPlus;
    private DatabaseReference reference;
    private String id;
    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        id = getIntent().getStringExtra("id");

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        send = findViewById(R.id.send);
        progress_bar = findViewById(R.id.progress_bar);
        reference = FirebaseDatabase.getInstance().getReference().child("Notification");

        dialogPlus = DialogPlus.newDialog(this)
                .setMargin(50,0,50,0)
                .setContentHolder(new ViewHolder(R.layout.dialog_notification_type))
                .setGravity(Gravity.CENTER)
                .setExpanded(false)
                .create();

        LinearLayout layout = (LinearLayout)dialogPlus.getHolderView();
        Button restApi = layout.findViewById(R.id.rest_api);
        Button cloudFunction = layout.findViewById(R.id.cloud_funcation);

        restApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
                progress_bar.setVisibility(View.VISIBLE);

                Map<String,Object> map = new HashMap<>();
                map.put("title",title.getText().toString());
                map.put("description",description.getText().toString());
                map.put("id",id);


                reference.child(FirebaseAuth.getInstance().getUid())
                        .child("RestApi")
                        .push().setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                sentByRest();



                            }
                        });
            }
        });


        cloudFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
                progress_bar.setVisibility(View.VISIBLE);

                Map<String,Object> map = new HashMap<>();
                map.put("title",title.getText().toString());
                map.put("description",description.getText().toString());
                map.put("id",id);

                reference.child(FirebaseAuth.getInstance().getUid())
                        .child("CloudFuncation")
                        .push().setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progress_bar.setVisibility(View.GONE);
                            }
                        });
            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().length() == 0) {
                    Toast.makeText(SendNotificationActivity.this, "Enter Title ...", Toast.LENGTH_SHORT).show();
                } else if (description.getText().toString().length() == 0) {
                    Toast.makeText(SendNotificationActivity.this, "Enter Description ...", Toast.LENGTH_SHORT).show();
                } else {
                    dialogPlus.show();
                }
            }
        });

    }

    private void sentByRest() {

        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        NotificationReq req = new NotificationReq(
                                dataSnapshot.child("token").getValue().toString(),
                                new NotificationReq.Notification(title.getText().toString(),
                                        description.getText().toString())
                        );

                        RetrofitClient.getRetrofit(BASE_URL)
                                .create(NotificationRequest.class)
                                .sent(req)
                                .enqueue(new Callback<NotificationResponce>() {
                                    @Override
                                    public void onResponse(Call<NotificationResponce> call, Response<NotificationResponce> response) {
                                        if(response.code()==200){
                                            Toast.makeText(SendNotificationActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                                        }
                                        progress_bar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<NotificationResponce> call, Throwable t) {
                                        progress_bar.setVisibility(View.GONE);
                                        Log.i("hfbvjes", "onFailure: "+t.toString());
                                        Toast.makeText(SendNotificationActivity.this, "Filed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progress_bar.setVisibility(View.GONE);
                    }
                });


    }


//    https://fcm.googleapis.com/fcm/send
//    https://fcm.googleapis.com/fcm/send
}
