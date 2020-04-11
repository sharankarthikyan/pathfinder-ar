package com.example.pathfinderar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText userName;
    private EditText password;
    private Button Login;
    private TextView userRegister;
    private FirebaseAuth firebaseAuth;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIViews();

        //toolbar.setTitle("Login");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, MapBoxActivity.class));
        }



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userName.getText().toString(), password.getText().toString());
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void setupUIViews()
    {
        //toolbar=findViewById(R.id.toolbarLogin);
        userName = (EditText)findViewById(R.id.etUserName);
        password = (EditText)findViewById(R.id.etUserPassword);
        Login = (Button)findViewById(R.id.BtnLogin);
        userRegister = (TextView)findViewById(R.id.tvRegister);
    }

    private void validate(String userName, String userPassword)
    {
        if(userName.isEmpty() || userPassword.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Please Fill the Details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, MapBoxActivity.class));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
