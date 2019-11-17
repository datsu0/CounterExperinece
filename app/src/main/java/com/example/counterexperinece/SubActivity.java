package com.example.counterexperinece;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.Serializable;

import static com.example.counterexperinece.MainActivity.FILENAME;


public class SubActivity extends AppCompatActivity {
    private String[] figures = new String[5];
    NumberPicker numPicker;
    NumberPicker numPicker1;
    NumberPicker numPicker2;
    NumberPicker numPicker3;
    NumberPicker numPicker4;
    Button button1;
    FloatingActionButton fab;
    TextView textView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        findViews();
        initViews();
    }

    private void findViews(){
        numPicker = (NumberPicker)findViewById(R.id.numPicker0);
        numPicker1 = (NumberPicker)findViewById(R.id.numPicker1);
        numPicker2 = (NumberPicker)findViewById(R.id.numPicker2);
        numPicker3 = (NumberPicker)findViewById(R.id.numPicker3);
        numPicker4 = (NumberPicker)findViewById(R.id.numPicker4);

        button1 = (Button)findViewById(R.id.button1);
        fab = findViewById(R.id.fabMain);

    }

    private void initViews(){
        numPicker.setMaxValue(9);
        numPicker.setMinValue(0);
        numPicker1.setMaxValue(9);
        numPicker1.setMinValue(0);
        numPicker2.setMaxValue(9);
        numPicker2.setMinValue(0);
        numPicker3.setMaxValue(9);
        numPicker3.setMinValue(0);
        numPicker4.setMaxValue(9);
        numPicker4.setMinValue(0);


        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //textView1.setText(numPicker.getValue() + "");
//                figures[0] = String.valueOf(numPicker.getValue());
//                figures[1] = String.valueOf(numPicker1.getValue());
//                figures[2] = String.valueOf(numPicker2.getValue());
//                figures[3] = String.valueOf(numPicker3.getValue());
//                figures[4] = String.valueOf(numPicker4.getValue());
//                String str = String.format("%s%s%s.%s%s",
//                        figures[0], figures[1], figures[2], figures[3], figures[4]);

                int getNum = numPicker.getValue()+numPicker1.getValue()*10+numPicker2.getValue()*100+
                        numPicker3.getValue()*1000+numPicker4.getValue()*10000;

                Intent intent = getIntent();
                int num = intent.getIntExtra("num",0);
                String data = intent.getStringExtra("data");
                //Toast.makeText(SubActivity.this,data,Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.putInt("num"+data,num+getNum);
                e.commit();
                Intent intent1 = new Intent(SubActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
