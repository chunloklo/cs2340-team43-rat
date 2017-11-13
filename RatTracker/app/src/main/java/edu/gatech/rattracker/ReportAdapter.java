package edu.gatech.rattracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Report Adapter for reports
 * Created by Chunlok Lo on 10/8/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Report report;
    private final String logTag = "reportAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView header;
        public Sighting sighting;

        public void setSighting(Sighting sighting) {
            this.sighting = sighting;
        }

        public ViewHolder(View v) {
            super(v);
            header = itemView.findViewById(R.id.header);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(logTag, sighting.toString());
                    Context context = v.getContext();
                    Intent reportDetail = new Intent(context, IndividualReportActivity.class);
                    reportDetail.putExtra("sighting", sighting);
                    context.startActivity(reportDetail);
                }
            });
        }
    }

    public ReportAdapter(Report report) {
        this.report = report;
    }

    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_sighting, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((ViewHolder)holder).header.setText(report.getSighting(position).toString());
        ((ViewHolder)holder).setSighting(report.getSighting(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return report.size();
    }
}
