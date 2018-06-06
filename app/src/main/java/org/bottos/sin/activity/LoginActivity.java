package org.bottos.sin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 星空之钥丶 on 2018/6/5.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etUsername.addTextChangedListener(new JumpTextWatcher());
    }

    private class JumpTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str=s.toString();
            if (str.indexOf("\r")>=0 || str.indexOf("\n")>=0){//发现输入回车符或换行符
                etUsername.setText(str.replace("\r","").replace("\n",""));//去掉回车符和换行符
                etPassword.requestFocus();//让editText2获取焦点
                etPassword.setSelection(etPassword.getText().length());//若editText2有内容就将光标移动到文本末尾
            }

        }
    }

    public void close(View view) {
        finish();
    }
}
