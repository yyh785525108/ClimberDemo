package com.prudential.climberdemo;

import android.content.Intent;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameEt,passWordEt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    /**
     * init all the view
     */
    private void initView(){
        userNameEt=findViewById(R.id.username_et);
        passWordEt=findViewById(R.id.password_et);
        loginBtn=findViewById(R.id.login_btn);

        //set linsener
        loginBtn.setOnClickListener(this);
        userNameEt.addTextChangedListener(new MyTextWatcher(userNameEt));
        passWordEt.addTextChangedListener(new MyTextWatcher(passWordEt));


    }

    @Override
    public void onClick(View view) {
        if (view==loginBtn){
            login();
        }
    }

    /**
     *
     */
    private void login(){
        String userName=userNameEt.getText().toString();
        String passWord=passWordEt.getText().toString();
        //dosomething to test if userName and passWord is correct,here we assume that it's all right
        if (true){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }else {
            showMessage("userName or passWord is incorrect");
        }
    }


    //implements listener
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isNameValid()&&isPassWordValid()){
                loginBtn.setEnabled(true);
            }else {
                loginBtn.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * check if name  is null
     * @return
     */
    public boolean isNameValid() {
        String userName=userNameEt.getText().toString();
        if (TextUtils.isEmpty(userName)){
            return false;
        }
        return true;


    }
    /**
     * check if passWord  is null
     * @return
     */
    public boolean isPassWordValid() {
        String passWord=passWordEt.getText().toString();
        if (TextUtils.isEmpty(passWord)){
            return false;
        }
        return true;


    }


    private void showMessage(String message){
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
    }



}
