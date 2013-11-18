package deccan.courseline;

import java.util.HashMap;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ChartFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.deccan_courseline_activity_chart,
				container, false);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Intent intent = getActivity().getIntent();
	}

	@Override
	public void onResume() {
		super.onResume();
		drawChart();
	}

	private void drawChart() {
		XYMultipleSeriesDataset series = new XYMultipleSeriesDataset();
		XYValueSeries newTicketSeries = new XYValueSeries("New Tickets");
		//newTicketSeries.add(0, 0, 0);
		newTicketSeries.add(1f, 2, 14);
		newTicketSeries.add(2f, 2, 12);
		newTicketSeries.add(3f, 2, 18);
		newTicketSeries.add(4f, 2, 5);
		newTicketSeries.add(5f, 2, 1);
		series.addSeries(newTicketSeries);
		XYValueSeries fixedTicketSeries = new XYValueSeries("Fixed Tickets");
		//fixedTicketSeries.add(0, 0, 0);
		fixedTicketSeries.add(1f, 1, 7);
		fixedTicketSeries.add(2f, 1, 4);
		fixedTicketSeries.add(3f, 1, 18);
		fixedTicketSeries.add(4f, 1, 3);
		fixedTicketSeries.add(5f, 1, 1);
		series.addSeries(fixedTicketSeries);
/*
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setRange(new double[] { 0, 6, 0, 6 });

		// renderer.setMargins(new int[] { 20, 30, 15, 0 });
		XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
		newTicketRenderer.setColor(Color.BLUE);
		renderer.addSeriesRenderer(newTicketRenderer);
		XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
		fixedTicketRenderer.setColor(Color.GREEN);
		renderer.addSeriesRenderer(fixedTicketRenderer);

		renderer.setXLabels(0);
		renderer.setYLabels(0);
		renderer.setDisplayChartValues(false);
		renderer.setShowGrid(false);
		renderer.setShowLegend(false);
		renderer.setShowLabels(true);
*/		
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] { 0, 0, 0, 0 });
	    XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
	    newTicketRenderer.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(newTicketRenderer);
	    XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
	    fixedTicketRenderer.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(fixedTicketRenderer);

	    renderer.setXLabels(7);
	    renderer.setYLabels(0);
	    renderer.setShowGrid(false);

		// renderer.setZoomEnabled(false, false);

		GraphicalView chartView;
		/*
		 * if (chartView != null) { chartView.repaint(); } else {
		 */
		chartView = ChartFactory.getBubbleChartView(getActivity(), series,
				renderer);
		// }
		chartView.repaint();

		LinearLayout layout = (LinearLayout) getActivity().findViewById(
				R.id.chartLayout);
		// layout.removeAllViews();
		layout.addView(chartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

	}
}
