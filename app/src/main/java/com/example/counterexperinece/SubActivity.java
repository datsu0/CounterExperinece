package com.example.counterexperinece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {
    public int time=0;
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
        String name = intent.getStringExtra("name");
        name = name + ": ";
        textView.setText(name);
//        Toast toast = Toast.makeText(this, String.format(name+" ：%d", position), Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.TOP, 0, 150);
//        toast.show();
    }

    public void calcNum(){
        EditText editText1 = findViewById(R.id.edit_text);
        String str1 = editText1.getText().toString();
        int num1 = Integer.parseInt(str1);
        time = num1 + time;
        Toast toast = Toast.makeText(this, String.format("%d", time), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.show();
        TextView textView1 = findViewById(R.id.text_view_clac);
        String str3 = String.valueOf(time);
        textView1.setText(str3);
    }
}
