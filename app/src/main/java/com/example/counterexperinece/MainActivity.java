package com.example.counterexperinece;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    ArrayAdapter<String> adapter;
    String[] listName = new String[100];
    final static String FILENAME = "data.xml";
    //private RecyclerView recyclerView;
    int listCounter=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //ListViewオブジェクトの取得
        final ListView listView=(ListView)findViewById(R.id.list_view);

        SharedPreferences sp = this.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        listCounter = sp.getInt("listNum",1);
//        SharedPreferences.Editor e = sp.edit();


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
            //CustomAdapter adapter = new CustomAdapter(getApplicationContext(),R.layout.row_item,saveList.toArray(new String[saveList.size()]));
        }else {
            //ArrayAdapterオブジェクト生成
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,saveList);
            //CustomAdapter adapter = new CustomAdapter(getApplicationContext(),R.layout.row_item,saveList.toArray(new String[saveList.size()]));
        }

        //Buttonオブジェクト取得
<<<<<<< Updated upstream
        Button btn=(Button)findViewById(R.id.btn);
        //クリックイベントの通知先指定
        btn.setOnClickListener(new OnClickListener() {
            //クリックイベント
=======
//        Button btn=(Button)findViewById(R.id.btn);
//        クリックイベントの通知先指定
//        btn.setOnClickListener(new OnClickListener() {
//
//            //クリックイベント
//            @Override
//            public void onClick(View v) {
//                //要素追加
//                addStringData();
//                //DialogFragment newFragment = new TestDialogFragment();
//                //newFragment.show(getFragmentManager(), "test");
//                EditText edit=(EditText)findViewById(R.id.edit_text);
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("title")
//                        .setMessage("message")
//                        .setView(edit)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                println("path the upper source¥n");
//            }
//        });


        Button btnDaiglog = (Button)findViewById(R.id.btnDialog);
        btnDaiglog.setOnClickListener(new OnClickListener() {
>>>>>>> Stashed changes
            @Override
            public void onClick(View v) {
                //要素追加
                addStringData();
            }
        });
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
<<<<<<< Updated upstream
=======

    public static class TestDialogFragment extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText editText = new EditText(getActivity());

            builder.setMessage("ダイアログ")

                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            String returnValue = editText.getText().toString();
                            // MainActivityのインスタンスを取得
                            
                        }
                    })
                    .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    public void setTextView(String value){
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(value);
    }
>>>>>>> Stashed changes
}


