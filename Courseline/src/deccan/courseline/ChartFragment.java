package deccan.courseline;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import local.DBUtil;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.*;
import org.achartengine.renderer.*;

import entities.Course;
import entities.SubType;
import entities.Submission;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

@SuppressLint({ "UseSparseArrays", "SimpleDateFormat" })
public class ChartFragment extends Fragment {

	// Charting variables 
	GraphicalView chartView = null;
	XYMultipleSeriesRenderer render = new XYMultipleSeriesRenderer();
	XYMultipleSeriesDataset seriesV = new XYMultipleSeriesDataset();
	
	// Colors for course lines
	int[] color = new int[] { 0xff660066, 0xffff0000, 0xff006633, 0xffff007f, 0xff0000ff};
	
	// flags for communication between parent activity and fragment
	boolean[] crs = new boolean[5];
	boolean[] subs = new boolean[SubType.values().length];
	
	// maintain mapping between submissions and their location on the chart
	HashMap<LocationTuple, Submission> submMap = new HashMap<LocationTuple, Submission>();
	HashMap<Integer, String> courseMap = new HashMap<Integer, String>();
	
	// db variables
	DBUtil mdb;
	Cursor mCursor;
	
	public String userID = null;
	int count = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.deccan_courseline_activity_chart,
				container, false);

		// Initialize flags
		for (int i = 0; i < 5; i++) {
			crs[i] = true;
		}

		for (SubType s : SubType.values()) {
			subs[SubType.valueOf(s.toString()).ordinal()] = true;
		}

		return view;
	}

	/**
	 * 
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().getIntent();
	}

	@Override
	public void onResume() {
		super.onResume();
		
		// update chart on resume
		drawChart();
	}

	@SuppressWarnings("deprecation")
	public void drawChart() {
		// Clear dataset and renderers
		seriesV.clear();
		render.removeAllRenderers();

		mdb = new DBUtil(getActivity());
		mCursor = mdb.selectUser(userID);
		// get row from user table
		if (mCursor.getCount() > 0) {
			Log.d("CHART", "got mCursor");
			mCursor.moveToFirst();
			int count = mCursor.getInt(8);
			int i = 0;
			submMap.clear();
			// for all courses
			while (i != count) {
				// add values and renderer to multi Renderer only if checkbox
				// checked
				if (crs[i] == true) {
					String c_id = mCursor.getString(2 + i);
					Log.d("CHART", "-" + c_id);
					Course course = mdb.selectCourse(c_id);
					courseMap.put((10*i), c_id);
					
					// for all submissions in a course
					Iterator<Submission> it = course.submissions.iterator();
					XYValueSeries values = new XYValueSeries(c_id);
					while (it.hasNext()) {
						Submission sub = it.next();
						
						// if submission flag set, render it on chart
						if (subs[SubType.valueOf(sub.getSubType().toString())
								.ordinal()] == true) {
							Calendar c = Calendar.getInstance();
							c.setTime(sub.getDueDate());
							double day_yr = (double) c
									.get(Calendar.DAY_OF_YEAR);
							LocationTuple loc = new LocationTuple(day_yr, (10*i));
							submMap.put(loc, sub);
							Log.d("CHART",
									"--" + sub.getSubName() + " (" + day_yr
											+ ", " + i + ") = "
											+ sub.getWeightPercent());
							
							// add value & annotation for each submission
							values.add(day_yr, 10*i, sub.getWeightPercent()*3);
							String annot = sub.getSubName()
									+ "\n"
									+ new SimpleDateFormat("MMM").format(c
											.getTime()) + " "
									+ c.get(Calendar.DATE);
							values.addAnnotation(annot, day_yr,
									(10*i - 2));
						}
					}
					// initialize renderer
					Align align = Align.LEFT;
					XYSeriesRenderer render1 = new XYSeriesRenderer();
					render1.setColor(color[i]);
					render1.setAnnotationsTextSize(20);
					render1.setAnnotationsColor(color[i]);
					render1.setAnnotationsTextAlign(align);

					// add course's dataset and renderer
					seriesV.addSeries(values);
					render.addSeriesRenderer(render1);
				}

				i++;
			}
		}

		// Configure Multiple Renderer
		Log.d("CHART", "setting view window");
		// Get today and set ranges starting from today
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		double today = c.get(Calendar.DAY_OF_YEAR);
		// if started first time, show 1 month timeline from today
		if (count == 0) {
			render.setRange(new double[] { today, today + 31, -10, 10 });
			render.setYLabels(7);
			render.setYAxisMin(-5);
		} else {
			render.setRange(new double[] { render.getXAxisMin(),
					render.getXAxisMax(), render.getYAxisMin(),
					render.getYAxisMax() });
		}
		
		render.setShowGrid(true);
		render.setGridColor(Color.GRAY);
		render.setAxisTitleTextSize(16);
		render.setChartTitleTextSize(20);
		render.setLabelsTextSize(20);
		render.setLegendTextSize(20);
		render.setLegendHeight(55);
		render.setLabelsColor(Color.BLACK);
		render.setMargins(new int[] { 0, 0, 0, 0 });

		Log.d("CHART", "displaying dates on X-axis");
		// Print Month on X-axis
		int day1;
		int year = c.get(Calendar.YEAR);
		GregorianCalendar gc = null;
		for (int month = 0; month < 12; month++) {
			gc = new GregorianCalendar(year, month, 1);
			date = gc.getTime();
			c.setTime(date);
			day1 = c.get(Calendar.DAY_OF_YEAR);
			render.addTextLabel(
					day1,
					c.getDisplayName(Calendar.MONTH, Calendar.SHORT,
							Locale.getDefault())
							+ " 1");
			render.addTextLabel(
					day1 + 15,
					c.getDisplayName(Calendar.MONTH, Calendar.SHORT,
							Locale.getDefault())
							+ " 15");
		}
		render.addTextLabel(today, "Today");
		render.setXLabelsAlign(Align.CENTER);
		render.setXLabelsPadding(15);
		render.setXLabels(0);

		// if not here from onCreate(), just repaint and return
		if (count == 1) {
			chartView.repaint();
			return;
		}
		count = 1;

		// Prepare Layout and View
		LinearLayout layout = (LinearLayout) getActivity().findViewById(
				R.id.chartLayout);

		chartView = ChartFactory.getBubbleChartView(getActivity(), seriesV,
				render);
		
		// Clickable bubbles
		render.setClickEnabled(true);
		render.setSelectableBuffer(10);
		chartView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// handle the click event on the chart
				SeriesSelection seriesSelection = chartView
						.getCurrentSeriesAndPoint();
				if (seriesSelection == null) {
					System.out.println("No chart element");
				} else {
					// display information of the clicked point
					Log.d("CHART",
							"Chart element in series index "
									+ seriesSelection.getSeriesIndex()
									+ " data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked" + " closest point: X="
									+ seriesSelection.getXValue() + ", Y="
									+ seriesSelection.getValue());

					// Find appropriate submission tapped on and pass intent to Submission activity
					Intent chart2sub = new Intent(getActivity(),
							SubmissionActivity.class);
					LocationTuple curLoc = new LocationTuple(seriesSelection
							.getXValue(), seriesSelection.getValue());
					Iterator<Entry<LocationTuple, Submission>> it = submMap
							.entrySet().iterator();
					Submission subm = null;
					while (it.hasNext()) {
						Map.Entry<LocationTuple, Submission> entry = it.next();
						LocationTuple loc = (LocationTuple) entry.getKey();
						if ((curLoc.x == loc.x) && (curLoc.y == loc.y)) {
							subm = entry.getValue();
							break;
						}
					}
					String crsID = courseMap.get((int) curLoc.y);
					Log.d("CHART", "Y: " + curLoc.y + " crsID:" + crsID);

					if (subm != null) {
						chart2sub.putExtra("userID", userID);
						chart2sub.putExtra("courseID", crsID);
						chart2sub.putExtra("subm", subm);
						startActivity(chart2sub);
					}
				}
			}
		});

		layout.addView(chartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mdb.close();
	}
}
