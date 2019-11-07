package com.example.counterexperinece;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    String[] listName = new String[100];
    final static String FILENAME = "data.xml";
    //private RecyclerView recyclerView;
    int listCounter=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStringData();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //ListViewオブジェクトの取得
        final ListView listView=(ListView)findViewById(R.id.list_view);

        SharedPreferences sp = this.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        listCounter = sp.getInt("listNum",1);
//        SharedPreferences.Editor e = sp.edit();

        final RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        final List<String> saveList = new ArrayList<String>();

        for(int i=0;i<listCounter+1;i++){
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
            //adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,saveList);
            RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(saveList);
            recyclerView.setAdapter(rAdapter);

        }

        //Buttonオブジェクト取得
//        Button btn=(Button)findViewById(R.id.btn);
//        クリックイベントの通知先指定
//        btn.setOnClickListener(new OnClickListener() {
//            //クリックイベント
//            @Override
//            public void onClick(View v) {
//                //要素追加
//                addStringData();
//            }
//        });
        //Adapterのセット
        listView.setAdapter(adapter);

        // リスト項目がクリックされたときのイベント
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String msg = position + "番目のアイテムがクリックされました";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SubActivity.class);  //インテントの作成
                intent.putExtra("key",position);
                SharedPreferences sp = MainActivity.this.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
                String sendData = sp.getString("key"+position,null);
//                Toast.makeText(getApplicationContext(), sendData, Toast.LENGTH_LONG).show();
                intent.putExtra("name",sendData);
                startActivity(intent);
            }
        });
        //        リスト項目が長押しされた時のイベントを追加
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
//                String msg = position + "番目のアイテムが長押しされました";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to delete it ?")
                        .setTitle("delete")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveList.remove(position);
                                SharedPreferences sp = getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = sp.edit();
                                e.remove("key"+position);
                                e.commit();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();



                return false;
            }
        });



        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                ((RecyclerViewAdapter) recyclerView.getAdapter()).remove(viewHolder.getAdapterPosition());
            }


        };

        (new ItemTouchHelper(callback)).attachToRecyclerView(recyclerView);

        // idがdialogButtonのButtonを取得
        Button dialogBtn = (Button) findViewById(R.id.dialogButton);
        // clickイベント追加
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // クリックしたらダイアログを表示する処理
            public void onClick(View v) {
                // ダイアログクラスをインスタンス化
                CustomDialogFlagment dialog = new CustomDialogFlagment();
                // 表示  getFagmentManager()は固定、sampleは識別タグ
                dialog.show(getFragmentManager(), "sample");
            }
        });

    }

    public void setTextView(String value){
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // メニューアイテム選択イベント
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case R.id.menu1:
                // メニュー１選択時の処理
                builder.setMessage("自分がかけた時間やお金を記録していこう！！");
                builder.show();
                break;
            case R.id.menu2:
                builder.setMessage("長押しで消去可能");
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        listCounter=adapter.getCount();
        e.putString("key"+listCounter,enterName);
        e.putInt("listNum",listCounter);
        e.commit();

        //e.clear().commit();
        //EditText(テキスト)を取得し、アダプタに追加
        adapter.add(edit.getText().toString());
        listName[listCounter]=edit.getText().toString();
    }


}


