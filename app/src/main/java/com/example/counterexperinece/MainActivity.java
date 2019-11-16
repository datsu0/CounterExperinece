package com.example.counterexperinece;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    final static String FILENAME = "data.xml";
    //private RecyclerView recyclerView;
    int listCounter=0;
    String unit="回";
    private Animation rotateForward,rotateBackward;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        //ListViewオブジェクトの取得
        final ListView listView=(ListView)findViewById(R.id.list_view);

        SharedPreferences sp = this.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        listCounter = sp.getInt("listNum",0);
//        SharedPreferences.Editor e = sp.edit();
//        e.clear().commit();

        final RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

//        final List<String> saveList = new ArrayList<String>();
//        final List<String> unitList = new ArrayList<String>();

        final List<DataModel> dataList = new ArrayList<DataModel>();

        for(int i=0;i<listCounter+1;i++){
            String str = sp.getString("key"+i,null);
            String unitStr = sp.getString("unit"+str,null);
            DataModel data = new DataModel();
            if(str!=null){
//                saveList.add(str);
//                unitList.add(unitStr);
                data.data=str;
                data.unit=unitStr;
                data.num=10;

                dataList.add(data);

//                Toast toast = Toast.makeText(this, String.format(str+" ： %d",listCounter), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0, 150);
//                toast.show();
            }
        }

        final RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(dataList);
        recyclerView.setAdapter(rAdapter);
        rAdapter.notifyDataSetChanged();


        final FloatingActionButton fab = findViewById(R.id.fabMain);
        final FloatingActionButton fab1 = findViewById(R.id.fab1);
        final FloatingActionButton fab2 = findViewById(R.id.fab2);
        final FloatingActionButton fabBack = findViewById(R.id.fabBack);
        fab.show();
        fab1.hide();
        fab2.hide();
        fabBack.hide();

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fab.hide();
                fab1.show();
                fab2.show();
                fabBack.show();
                fab1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                        // ダイアログの設定
                        alertDialog.setTitle("追加");          //タイトル
                        alertDialog.setMessage("何をしたか入力");      //内容
                        final EditText editText = new EditText(MainActivity.this);
                        alertDialog.setView(editText);
                        alertDialog.setPositiveButton("追加", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().equals("")==true){
                                    Toast toast = Toast.makeText(MainActivity.this, String.format("fill out blank"), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP, 0, 150);
                                    toast.show();
                                    return;
                                }
                                //unit = "時間";
                                String returnValue = editText.getText().toString();
//                                saveList.add(returnValue);
//                                unitList.add(unit);

                                DataModel data = new DataModel();
                                data.data = returnValue;
                                data.unit = unit;
                                dataList.add(data);

                                SharedPreferences sp = getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = sp.edit();
                                e.putString("unit"+returnValue,unit);

//                                final RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(saveList,unit);
                                final RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(dataList);
                                recyclerView.setAdapter(rAdapter);
                                rAdapter.notifyDataSetChanged();
//                                Toast toast = Toast.makeText(MainActivity.this, String.format(returnValue+" ： %d",listCounter), Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.TOP, 0, 150);
//                                toast.show();
                                addStringData(data);
                                fab.show();
                                fab1.hide();
                                fab2.hide();
                                fabBack.hide();

                            }
                        });

                        String[] items = {"時間","分","回"};
                        AlertDialog.Builder unitDialog = new AlertDialog.Builder(MainActivity.this);
                        unitDialog.setTitle("単位を選んで")
                                .setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0){
                                            unit = "時間";
                                        }else if(which==1){
                                            unit = "分";
                                        }else {
                                            unit = "回";
                                        }
                                    }
                                });

                        // ダイアログの作成と表示
                        alertDialog.create().show();
                        unitDialog.create().show();

                    }
                });


                fab2.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                        // ダイアログの設定
                        alertDialog.setTitle("追加");          //タイトル
                        alertDialog.setMessage("何にお金をかけたか入力");      //内容
                        final EditText editText = new EditText(MainActivity.this);
                        alertDialog.setView(editText);
                        alertDialog.setPositiveButton("追加", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().equals("")==true){
                                    Toast toast = Toast.makeText(MainActivity.this, String.format("fill out blank"), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP, 0, 150);
                                    toast.show();
                                    return;
                                }
                                unit = "円";
                                rAdapter.notifyDataSetChanged();
                                String returnValue = editText.getText().toString();
//                                saveList.add(returnValue);
//                                unitList.add(unit);

                                DataModel data = new DataModel();
                                data.data = returnValue;
                                data.unit = unit;
                                dataList.add(data);

                                SharedPreferences sp = getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = sp.edit();
                                e.putString("unit"+returnValue,unit);

//                                final RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(saveList,unit);
                                final RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(dataList);
                                recyclerView.setAdapter(rAdapter);
//                                Toast toast = Toast.makeText(MainActivity.this, String.format(returnValue+" ： %d",listCounter), Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.TOP, 0, 150);
//                                toast.show();

                                addStringData(data);
                                fab.show();
                                fab1.hide();
                                fab2.hide();
                                fabBack.hide();
                            }
                        });

                        // ダイアログの作成と表示
                        alertDialog.create().show();
                    }
                });

                fabBack.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        fab.show();
                        fab1.hide();
                        fab2.hide();
                        fabBack.hide();
                    }
                });
            }
        });


//        if(saveList==null || saveList.size()==0){
//            //ArrayAdapterオブジェクト生成
//            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        }else {
//            //ArrayAdapterオブジェクト生成
//            //adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,saveList);
//            RecyclerView.Adapter rAdapter = new RecyclerViewAdapter(saveList,unit);
//            recyclerView.setAdapter(rAdapter);
//            rAdapter.notifyDataSetChanged();
//        }



        //Adapterのセット
//        listView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {

                // 横にスワイプされたら要素を消す
                int swipedPosition = viewHolder.getAdapterPosition();
                RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerView.getAdapter();
                listCounter--;
//                saveList.remove(swipedPosition);
                SharedPreferences sp = getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.putInt("listNum",listCounter);


                //String str = saveList.get(swipedPosition);
                dataList.remove(swipedPosition);
                adapter.remove(dataList,swipedPosition);

                for(int z=0;z<listCounter;z++){
                    String str = dataList.get(z).data;
                    String unit = dataList.get(z).unit;
                    if(str!=null){
                        e.putString("key"+z,str);
                        e.putString("unit"+str,unit);
//                Toast toast = Toast.makeText(this, String.format(str+" ： %d",listCounter), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0, 150);
//                toast.show();
                    }
                }

                e.commit();
//                Toast toast = Toast.makeText(MainActivity.this, String.format(str+" ： %d",swipedPosition), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0, 150);
//                toast.show();
            }

            final Drawable deleteIcon = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_delete_black_24dp);
            @Override
            public void onChildDraw (@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;

                // キャンセルされた時
                if (dX == 0f && !isCurrentlyActive) {
                    clearCanvas(c, itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false);
                    return;
                }

                ColorDrawable background = new ColorDrawable();
                background .setColor(Color.parseColor("#f44336"));
                background.setBounds(itemView.getRight() + (int)dX, itemView.getTop(),  itemView.getRight(), itemView.getBottom());
                background.draw(c);

                int deleteIconTop = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                int deleteIconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - deleteIcon.getIntrinsicWidth();
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                int deleteIconBottom = deleteIconTop +  deleteIcon.getIntrinsicHeight();

                deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                deleteIcon.draw(c);
            }
        };
        (new ItemTouchHelper(callback)).attachToRecyclerView(recyclerView);
    }

    private void clearCanvas(Canvas c, int left, int top, int right, int bottom) {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        c.drawRect(left, top, right, bottom, paint);
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
    private void addStringData(DataModel data){
        //EditTextオブジェクト取得
        listCounter++;
        SharedPreferences sp = getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("key"+listCounter,data.data);
        e.putString("unit"+data.data,data.unit);
        e.putInt("listNum",listCounter);
        e.commit();

    }

}


