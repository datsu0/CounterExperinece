package com.example.counterexperinece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        setTitle("画面１");

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

        Intent intent = getIntent();
        int position  = intent.getIntExtra("key",0);
        String name = intent.getStringExtra("name");
        Toast toast = Toast.makeText(this, String.format(name+" ：%d", position), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 150);
        toast.show();
    }
}
