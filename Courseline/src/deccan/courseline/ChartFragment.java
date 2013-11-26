package deccan.courseline;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.*;
import org.achartengine.renderer.*;

import entities.Submission;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ChartFragment extends Fragment {

	GraphicalView chartView = null;
	XYMultipleSeriesRenderer render = new XYMultipleSeriesRenderer();
	XYMultipleSeriesDataset seriesV = new XYMultipleSeriesDataset();
	int[] color = new int[]{0xffff0000, 0xffff007f, 0xff0000ff, 0xff006633, 0xff660066};
	int count = 0;
	public boolean c1 = true, c2 = true, c3 = true, c4 = true, c5 = true;
	HashMap<LocationTuple, Submission> submMap = new HashMap<LocationTuple, Submission>(); 
	Submission sub = new Submission();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.deccan_courseline_activity_chart,
				container, false);
		
		return view;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().getIntent();
	}

	@Override
	public void onResume() {
		super.onResume();
		drawChart();
	}

	@SuppressWarnings("deprecation")
	public void drawChart() {
seriesV.clear();
render.removeAllRenderers();
		
		Log.d("TRACE", "drawChart() count: " + count);
		
		// Mock dates (will actually be read from db
		int i = 0;
		Date[] dt1 = new Date[5];
		GregorianCalendar gc;
		gc = new GregorianCalendar(2013, 8, 3);
		dt1[i++] = gc.getTime();
		gc = new GregorianCalendar(2013, 8, 17);
		dt1[i++] = gc.getTime();
		gc = new GregorianCalendar(2013, 8, 29);
		dt1[i++] = gc.getTime();
		gc = new GregorianCalendar(2013, 9, 12);
		dt1[i++] = gc.getTime();
		gc = new GregorianCalendar(2013, 9, 27);
		dt1[i++] = gc.getTime();
		
		Calendar c = Calendar.getInstance();
		double[] day = new double[5];
		for (i = 0; i < dt1.length; i++) {
			c.setTime(dt1[i]);
			day[i] = (double) c.get(Calendar.DAY_OF_YEAR);
		}
		
		// Values - XYValueSeries (Y-axis)
		i = 0;
		XYValueSeries c213 = new XYValueSeries("15-213");
		sub.setWeightPercent(5);
		LocationTuple loc = new LocationTuple(day[i], 2);
		Log.d("CHART", "Day#: " + day[i]);
		submMap.put(loc, sub);
		c213.add(day[i++], 2, 5);
		c213.add(day[i++], 2, 6);
		c213.add(day[i++], 2, 10);
		c213.add(day[i++], 2, 12);
		c213.add(day[i++], 2, 8);
		i = 0;
		c213.addAnnotation("Quiz-01\nSept-13", day[i++] + 0.2, 1.9);
		c213.addAnnotation("Test-01\nSept-17", day[i++] + 0.2, 1.9);
		c213.addAnnotation("Quiz-02\nSept-29", day[i++] + 0.2, 1.9);
		c213.addAnnotation("Test-02\nOct-12", day[i++] + 0.2, 1.9);
		c213.addAnnotation("Mid-Term Exam\nOct-27", day[i++] + 0.2, 1.9);
		if (c1 == true)
		seriesV.addSeries(c213);
		
		i = 0;
		XYValueSeries c648 = new XYValueSeries("18-648");
		c648.add(day[i++], 1, 10);
		c648.add(day[i++], 1, 15);
		c648.add(day[i++], 1, 10);
		c648.add(day[i++], 1, 20);
		c648.add(day[i++], 1, 25); 
		i = 0;
		c648.addAnnotation("Sept-03", day[i++], 1.4);
		c648.addAnnotation("Sept-17", day[i++], 1.4);
		c648.addAnnotation("Sept-29", day[i++], 1.4);
		c648.addAnnotation("Oct-12", day[i++], 1.4);
		c648.addAnnotation("Oct-27", day[i++], 1.4);
		if (c2 == true)
		seriesV.addSeries(c648);
		
		i = 0;
		XYValueSeries c2131 = new XYValueSeries("15-2131");
		c2131.add(day[i++], 3, 5);
		c2131.add(day[i++], 3, 6);
		c2131.add(day[i++], 3, 10);
		c2131.add(day[i++], 3, 12);
		c2131.add(day[i++], 3, 8);
		i = 0;
		c2131.addAnnotation("Quiz-01\nSept-03", day[i++], 3.4);
		c2131.addAnnotation("Test-01\nSept-17", day[i++], 3.4);
		c2131.addAnnotation("Quiz-02\nSept-29", day[i++], 3.4);
		c2131.addAnnotation("Test-02\nOct-12", day[i++], 3.4);
		c2131.addAnnotation("Mid-Term Exam\nOct-27", day[i++], 3.4);
		if (c3 == true)
		seriesV.addSeries(c2131);

		Align align = Align.LEFT;
		XYSeriesRenderer r213 = new XYSeriesRenderer();
		r213.setColor(color[0]);
		r213.setAnnotationsTextSize(20);
		r213.setAnnotationsColor(color[0]);		
		r213.setAnnotationsTextAlign(align);
		if (c1 == true)
		render.addSeriesRenderer(r213);

		XYSeriesRenderer r648 = new XYSeriesRenderer();
		r648.setColor(color[1]);
		r648.setAnnotationsTextSize(20);
		r648.setAnnotationsColor(color[1]);
		r648.setAnnotationsTextAlign(align);
		if (c2 == true)
		render.addSeriesRenderer(r648);
		
		XYSeriesRenderer r2131 = new XYSeriesRenderer();
		r2131.setColor(color[2]);
		r2131.setAnnotationsTextSize(20);
		r2131.setAnnotationsColor(color[2]);
		r2131.setAnnotationsTextAlign(align);
		if (c3 == true)
		render.addSeriesRenderer(r2131);
		
		// Get today and set ranges starting from today
		Date date = new Date();
		c.setTime(date);
		double today = c.get(Calendar.DAY_OF_YEAR);
		if (count == 0) {
		render.setRange(new double[] {today, today+31, -10, 10});
		render.setYLabels(7);
		render.setYAxisMin(-5);
		} else {
			render.setRange(new double[] {render.getXAxisMin(), render.getXAxisMax(), 
					render.getYAxisMin(), render.getYAxisMax()});
		}
	    //render.setXAxisMin(today);
	    //render.setXAxisMax(today+31);
	    //render.setYAxisMin(-10);
	    //render.setYAxisMax(10);

		//render.setXLabels(50);
		//render.setPanLimits(new double[] { 1, 500, -10, 10 });
		//render.setZoomLimits(new double[] { 1, 500, -10, 10 });
		render.setShowGrid(true);
		render.setGridColor(Color.GRAY);
		render.setAxisTitleTextSize(16);
		render.setChartTitleTextSize(20);
		render.setLabelsTextSize(20);
		render.setLegendTextSize(20);
		render.setLegendHeight(55);
		render.setLabelsColor(Color.BLACK);
		render.setMargins(new int[] { 0, 0, 0, 0 });
		
		// Print Month on X-axis
		int day1;
		int year = c.get(Calendar.YEAR);
		for (int month=0; month<12; month++) {
			gc = new GregorianCalendar(year, month, 1);
			date = gc.getTime();
			c.setTime(date);
			day1 = c.get(Calendar.DAY_OF_YEAR);
			//System.out.println("Day#: " + day1 + " Month: " + c.getDisplayName(Calendar.MONTH, 
				//	Calendar.SHORT, Locale.getDefault()));
			render.addTextLabel(day1, c.getDisplayName(Calendar.MONTH, 
					Calendar.SHORT, Locale.getDefault()) + " 1");
			render.addTextLabel(day1+15, c.getDisplayName(Calendar.MONTH, 
					Calendar.SHORT, Locale.getDefault()) + " 15");
		}
		render.addTextLabel(today, "Today");
	    render.setXLabelsAlign(Align.CENTER);
	    render.setXLabelsPadding(15);
	    render.setXLabels(0);
	    
	    if (count == 1) {
	    	chartView.repaint();
	    	return;
	    }
	    count = 1;
/*
	    XYValueSeries cLine = new XYValueSeries("Line");
	    cLine.add(today, -4);
	    cLine.add(today, 8);
	    XYSeriesRenderer rLine = new XYSeriesRenderer();
		rLine.setColor(Color.BLACK);
		//rLine.setLineWidth(20);
		render.addSeriesRenderer(rLine);
		
	    
	    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	    dataset.addSeries(c213);
	    dataset.addSeries(c648);
	    dataset.addSeries(c2131);
	    dataset.addSeries(cLine);
	*/    
	    // Prepare Layout and View
		LinearLayout layout = (LinearLayout) getActivity().findViewById(
				R.id.chartLayout);

//	    String[] types = new String[] { BubbleChart.TYPE, BubbleChart.TYPE, 
	//    		BubbleChart.TYPE, LineChart.TYPE};
		
		chartView = ChartFactory.getBubbleChartView(getActivity(), seriesV,	render);
	    //chartView = ChartFactory.getCombinedXYChartView(getActivity(), dataset, render, types);
/*
		// Zoom Listener
		chartView.addZoomListener(new ZoomListener() {
            @Override
            public void zoomReset() {}

            @Override
            public void zoomApplied(ZoomEvent e) {
            	zoomLevel *= e.getZoomRate();
                Log.d("ZOOM", "zoom: "+String.valueOf(e.getZoomRate()+", zoomLevel: "+zoomLevel));
                if ((zoomLevel > 1.5) && (detailsFlag == false)) {
                	detailsFlag = true;
                	drawChart();
                } else if ((zoomLevel <= 1.5) && (detailsFlag == true)) {
                	detailsFlag = false;
                	drawChart();
                }
            }
        }, true, true);
*/

		// Clickable bubbles
		render.setClickEnabled(true);
		render.setSelectableBuffer(10);
		chartView.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	          // handle the click event on the chart
	          SeriesSelection seriesSelection = chartView.getCurrentSeriesAndPoint();
	          if (seriesSelection == null) {
	            System.out.println("No chart element");
	          } else {
	            // display information of the clicked point
	            	System.out.println("Chart element in series index " + seriesSelection.getSeriesIndex()
		                    + " data point index " + seriesSelection.getPointIndex() + " was clicked"
		                    + " closest point value X=" + seriesSelection.getXValue() + ", Y="
		                    + seriesSelection.getValue());
	            	
	        		Intent chart2sub = new Intent(getActivity(), SubmissionActivity.class);
	        		LocationTuple curLoc = new LocationTuple(seriesSelection.getXValue(), seriesSelection.getValue());
	        		Iterator<Entry<LocationTuple, Submission>> it = submMap.entrySet().iterator();
	        		Submission subm = null;
	        		while (it.hasNext()) {
	        			Map.Entry<LocationTuple, Submission> entry = it.next();
	        			LocationTuple loc = (LocationTuple) entry.getKey();
	        			if ((curLoc.x == loc.x) && (curLoc.y == loc.y)) {
	        				subm = entry.getValue();
	        				break;
	        			}
	        		}
	        		
	        		if (subm != null) {
	        			chart2sub.putExtra("subm", subm);
	        			startActivity(chart2sub);
	        		}
	          }
	        }
	      });	
				
		layout.addView(chartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
}
