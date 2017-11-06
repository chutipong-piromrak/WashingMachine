package com.example.leomossi.washingmachine;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.link_signup) TextView signLink;

    private String email;
    private String password;
    private SpotsDialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.btn_login)
    void onClickLogin() {
        Log.d("testLog", "loginSuccess");
        login();
    }

    @OnClick(R.id.link_signup)
    void onClickSignup() {
        Log.d("testLog", "signupSuccess");
    }

    public void login() {

        if(!validate()) {
            return;
        }

        dialog = new SpotsDialog(this, "กำลังเข้าสู่ระบบ");
        dialog.show();

        signInWithEmailAndPassword();


    }

    public boolean validate() {
        boolean valid = true;

        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if((email.isEmpty()) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("อีเมลไม่ถูกต้อง");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 16) {
            passwordText.setError("รหัสควรมีความยาว 6 - 16 ตัวอักษร");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public void signInWithEmailAndPassword() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            dialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "เข้าสู่ระบบไม่สำเร็จ"+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


}
