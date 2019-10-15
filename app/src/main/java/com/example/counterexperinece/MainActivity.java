package com.example.counterexperinece;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import static android.nfc.NfcAdapter.EXTRA_DATA;

public class MainActivity extends AppCompatActivity  {
    ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //ListViewオブジェクトの取得
        ListView listView=(ListView)findViewById(R.id.list_view);
        //ArrayAdapterオブジェクト生成
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
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
        //EditText(テキスト)を取得し、アダプタに追加
        adapter.add(edit.getText().toString());
    }

    //ボタンが押された時の処理
    public void onClick(View view){

    }
}


