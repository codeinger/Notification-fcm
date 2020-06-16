package com.codeinger.notification_fcm.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.codeinger.notification_fcm.R;
import com.codeinger.notification_fcm.ui.fragment.AllUserFragment;
import com.codeinger.notification_fcm.ui.fragment.NotificationFragment;
import com.codeinger.notification_fcm.ui.fragment.ProfileFragment;
import com.codeinger.notification_fcm.ui.fragment.TopicFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class DashboaredActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");


        replace(new AllUserFragment());


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.all_user:
                        replace(new AllUserFragment());
                        break;
                    case R.id.topic:
                        replace(new TopicFragment());
                        break;
                    case R.id.profile:
                        replace(new ProfileFragment());
                        break;
                    case R.id.notification:
                        replace(new NotificationFragment());
                        break;


                }

                return true;
            }
        });
    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}
