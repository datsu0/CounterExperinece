package com.example.counterexperinece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.nfc.NfcAdapter.EXTRA_DATA;

public class MainActivity extends AppCompatActivity  {
    ArrayAdapter<String> adapter;
    String[] listName = new String[100];
    final static String FILENAME = "data.xml";
    int listCounter=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //ListViewオブジェクトの取得
        ListView listView=(ListView)findViewById(R.id.list_view);

        SharedPreferences sp = this.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        listCounter = sp.getInt("listNum",0);

        List<String> saveList = new ArrayList<String>();

        for(int i=0;i<listCounter;i++){
            String str = sp.getString("key"+i,null);
            if(str!=null){
                saveList.add(str);
//                Toast toast = Toast.makeText(this, String.format(str+" ： %d",listCounter), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0, 150);
//                toast.show();
            }
        }
        if(saveList==null || saveList.size()==0){
            //ArrayAdapterオブジェクト生成
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }else {
            //ArrayAdapterオブジェクト生成
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,saveList);
        }

        //Buttonオブジェクト取得
        Button btn=(Button)findViewById(R.id.btn);
        //クリックイベントの通知先指定
        btn.setOnClickListener(new OnClickListener() {
            //クリックイベント
            @Override
            public void onClick(View v) {
                //要素追加
                addStringData();
            }
        });
        //Adapterのセット
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = position + "番目のアイテムがクリックされました";
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SubActivity.class);  //インテントの作成
                intent.putExtra("key",position);
                SharedPreferences sp = MainActivity.this.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
                String sendData = sp.getString("key"+position,null);
                intent.putExtra("name",sendData);
                startActivity(intent);
            }
        });

        //リスト項目が長押しされた時のイベントを追加
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                String msg = position + "番目のアイテムが長押しされました";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });
    }

    //要素追加処理
    private void addStringData(){
        //EditTextオブジェクト取得
        EditText edit=(EditText)findViewById(R.id.edit_text);
        if(edit.getText().toString().equals("")==true){
            Toast toast = Toast.makeText(this, String.format("fill out blank"), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 150);
            toast.show();
            return;
        }
        SpannableStringBuilder sb = (SpannableStringBuilder)edit.getText();
        String enterName = sb.toString();

        SharedPreferences sp = getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("key"+listCounter,enterName);
        e.putInt("listNum",listCounter);
        e.commit();

        //e.clear().commit();
        //EditText(テキスト)を取得し、アダプタに追加
        adapter.add(edit.getText().toString());
        listName[listCounter]=edit.getText().toString();
        listCounter++;
    }

    //ボタンが押された時の処理
    public void onClick(View view){

    }
}


