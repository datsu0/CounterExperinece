package com.example.counterexperinece;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

import java.io.Serializable;



public class SubActivity extends AppCompatActivity implements Serializable {
    public int time=0;
    public String name;
    private static final String TAG = "SubActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        final TextView textView = findViewById(R.id.text_view);
        final TextView textViewUnit = findViewById(R.id.text_unit);

        Intent intent = getIntent();
        int position  = intent.getIntExtra("key",0);
        name = intent.getStringExtra("name");
        final String unit = "unit"+name;

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
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Button minButton = (Button)findViewById(R.id.minButton);
        minButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                textViewUnit.setText("分");
                sp.edit().putString(unit, textViewUnit.getText().toString()).commit();
            }
        });
        Button hourButton = (Button)findViewById(R.id.hourButton);
        hourButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                textViewUnit.setText("時間");
                sp.edit().putString(unit, textViewUnit.getText().toString()).commit();
            }
        });
        Button countButton = (Button)findViewById(R.id.countButton);
        countButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                textViewUnit.setText("回");
                sp.edit().putString(unit, textViewUnit.getText().toString()).commit();
            }
        });
        Button moneyButton = (Button)findViewById(R.id.moneyButton);
        moneyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                textViewUnit.setText("円");
                sp.edit().putString(unit, textViewUnit.getText().toString()).commit();
            }
        });
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubActivity.this);
                builder.setMessage("Are you sure you want to delete it ?")
                        .setTitle("delete")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SubActivity.this);
                                sp.edit().remove(name).commit();
                                time = 0;
                                String str = String.valueOf(time);
                                TextView textView1 = findViewById(R.id.text_view_clac);
                                textView1.setText(str);
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
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


        textViewUnit.setText(sp.getString(unit,null),TextView.BufferType.NORMAL);
        name = name + ": ";
        textView.setText(name);
        TextView textViewNum = findViewById(R.id.text_view_clac);
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        textViewNum.setText(sp.getString(name, null), TextView.BufferType.NORMAL);
        if(textViewNum.getText().toString().length()!=0){
            time = Integer.valueOf(textViewNum.getText().toString());
        }
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

//    private void loadButtonClick(){
//        TextView textView = findViewById(R.id.text_view_clac);
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        textView.setText(sp.getString(name, null), TextView.BufferType.NORMAL);
//        if(textView.getText().toString().length()!=0){
//            time = Integer.valueOf(textView.getText().toString());
//        }
//    }
}
