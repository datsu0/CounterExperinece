package com.example.counterexperinece;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<String> dataset = new ArrayList<String>();
    List<DataModel> dataModels = new ArrayList<DataModel>();
    String unit = "回";
    int value = 0;
    //private View.OnClickListener listener;
    private onItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout linearLayout;
        // each data item is just a string in this case
        TextView mTextView;
        TextView rTextView;
        TextView nTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.text_view);
            rTextView = (TextView)v.findViewById(R.id.text_view_unit);
            //nTextView = (TextView)v.findViewById(R.id.text_view_num);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_layout);
        }


    }

    public interface onItemClickListener{
        void onClick(View view,DataModel data,int positon);
        //void onClick(View view);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    RecyclerViewAdapter(List<String> myDataset,String Myunit) {
//        dataset = myDataset;
//        unit = Myunit;
//    }

    RecyclerViewAdapter(List<DataModel>myDataset) {
        dataModels = myDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        return new ViewHolder(view);

    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(dataModels.get(position).data);
        holder.rTextView.setText(dataModels.get(position).unit);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onClick(v, dataModels.get(position).data,dataModels.get(position).unit);
                listener.onClick(v, dataModels.get(position),position);
            }
        });
        //holder.nTextView.setText(String.valueOf(value));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataModels.size();
    }


    public void remove(List<DataModel> myDataset,int position){
        dataModels=myDataset;
        notifyItemChanged(position);
        notifyDataSetChanged();
    }

}