package edu.gatech.rattracker;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Chunlok Lo on 10/8/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private String[] mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView header;

        public ViewHolder(View v) {
            super(v);
            header = itemView.findViewById(R.id.header);
        }
    }

    public ReportAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sighting, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((ViewHolder)holder).header.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
