package com.example.counterexperinece;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;



public class SubActivity extends AppCompatActivity implements Serializable {
    public int time=0;
    public String name;
    private static final String TAG = "SubActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        TextView textView = findViewById(R.id.text_view);
        Button btn = (Button)findViewById(R.id.btn);
        Button btnClac = (Button)findViewById(R.id.btnCalc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
        Button loadButton = (Button)findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                loadButtonClick();
            }
        });
        btnClac.setOnClickListener(new View.OnClickListener() {
            //クリックイベント
            @Override
            public void onClick(View v) {
                //要素追加
                calcNum();
            }
        });

        Intent intent = getIntent();
        int position  = intent.getIntExtra("key",0);
        name = intent.getStringExtra("name");
        name = name + ": ";
        textView.setText(name);
//        Toast toast = Toast.makeText(this, String.format(name+" ：%d", position), Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.TOP, 0, 150);
//        toast.show();
    }

    public void calcNum() {
        EditText editText1 = findViewById(R.id.edit_text);
        String str1 = editText1.getText().toString();
        if(str1.length() == 0){
        Toast toast = Toast.makeText(this, String.format("fill out blank"), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.show();
            return;
        }
        int num1 = Integer.parseInt(str1);

//        Toast toast = Toast.makeText(this, String.format("%d", time), Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.TOP, 0, 150);
//        toast.show();
        TextView textView1 = findViewById(R.id.text_view_clac);
        textView1.getText().toString();
        time = num1 + time;
        String str3 = String.valueOf(time);
        textView1.setText(str3);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putString(name, textView1.getText().toString()).commit();
    }

    private void loadButtonClick(){
        TextView textView = findViewById(R.id.text_view_clac);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        textView.setText(sp.getString(name, null), TextView.BufferType.NORMAL);
        time = Integer.valueOf(textView.getText().toString());
    }
}
