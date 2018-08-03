package zizkuz_menu.hassebnafssak;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<LineGraphSeries>  mDataset;
    private List<LineGraphSeries>  mDataset2;
    private LayoutInflater inflater;
    private List<String> iibadates;
    private  Context contextGlobal;
    private int dateDebut;
    private int dateFin;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder    {
        // each data item is just a string in this case

        public GraphView mGraphView;
        public TextView iibada;


        public ViewHolder(View v) {

            super(v);

             mGraphView=(GraphView) v.findViewById(R.id.graph2);
            iibada=(TextView) v.findViewById(R.id.iibada);
          //  mTextView = v;
        }


    }

    // constructeur
    public RecyclerAdapter (Context context, List<LineGraphSeries> mGraph, List<LineGraphSeries> mGraph2,List<String> iibadate, int DateDebut, int DateFin) {
       inflater=LayoutInflater.from(context);
        mDataset = mGraph;
        mDataset2= mGraph2;
        iibadates=iibadate;
        contextGlobal= context;
        dateDebut=DateDebut;
        dateFin= DateFin;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layoutrecyclerview, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mGraphView.mDataset.get(position);
        holder.mGraphView.removeAllSeries();
        holder.mGraphView.addSeries(mDataset.get(position));

        holder.mGraphView.addSeries(mDataset2.get(position));
        holder.iibada.setText(iibadates.get(position));

        // set date label formatter
    // holder.mGraphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(contextGlobal));

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setMinimumIntegerDigits(1);

        holder.mGraphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));

       holder.mGraphView.getGridLabelRenderer().setNumHorizontalLabels(6); // only 4 because of the space

// set manual x bounds to have nice steps
   // holder.mGraphView.getViewport().setMinX(dateDebut.getTime());
   //  holder.mGraphView.getViewport().setMaxX(dateFin.getTime());
        holder.mGraphView.getViewport().setMinX(1);
        holder.mGraphView.getViewport().setMaxX(31);
        holder.mGraphView.getViewport().setXAxisBoundsManual(true);
       // holder.mGraphView.getViewport().set

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}