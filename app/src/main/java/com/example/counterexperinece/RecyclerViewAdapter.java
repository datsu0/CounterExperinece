package com.example.counterexperinece;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<String> dataset = new ArrayList<String>();
    String unit ;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        TextView mTextView;
        TextView rTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.text_view);
            rTextView = (TextView)v.findViewById(R.id.text_view_unit);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    RecyclerViewAdapter(List<String> myDataset,String Myunit) {
        dataset = myDataset;
        unit = Myunit;
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(dataset.get(position));
        holder.rTextView.setText(unit);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public  void remove(int position){
        dataset.remove(position);
        notifyItemChanged(position);
    }


}