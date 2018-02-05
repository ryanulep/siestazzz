package com.ucr.buzuka.siestazzz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Rick Boshae on 1/23/2018.
 */

public class ViewPagerFragment_Trends extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_trends,container, false);
        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.setTitle("February 4, 2018");
        graph.setTitleColor(Color.BLACK);
        graph.getViewport().setBackgroundColor(Color.DKGRAY);
        graph.addSeries(series);

        GraphView graph2 = (GraphView) rootView.findViewById(R.id.graph2);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {

                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(5, 58),
                new DataPoint(23, 74),

                new DataPoint(100, 1),

        });
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(125);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(100);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setXAxisBoundsManual(true);

        graph2.setTitle("February 3, 2018");
        graph2.setTitleColor(Color.WHITE);
        graph2.getViewport().setBackgroundColor(Color.WHITE);
        graph2.addSeries(series2);
        return rootView; // inflate is a function that converts a layout to a view.
    }
}
