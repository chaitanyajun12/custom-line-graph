package org.custom.graph;

import java.io.IOException;
import java.util.ArrayList;

import org.custom.graph.line.LineGraph;
import org.custom.graph.line.Point;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private LineGraph lineGraph1, lineGraph2;
	private LinearLayout layout;
	private ArrayList<Point> points1, points2;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lineGraph1 = (LineGraph) findViewById(R.id.lineGraph);
		layout = (LinearLayout) findViewById(R.id.layout);
		
		lineGraph2 = new LineGraph(this);
		lineGraph2.setXMax(500);
		lineGraph2.setYMax(300);
		lineGraph2.setXunitSpacing(10);
		lineGraph2.setYunitSpacing(10);
		lineGraph2.setLineColor(Color.RED);
		
		layout.addView(lineGraph2);
		
		if (savedInstanceState != null) {

			points1 = (ArrayList<Point>) savedInstanceState.getSerializable("points1");
			points2 = (ArrayList<Point>) savedInstanceState.getSerializable("points2");
			lineGraph1.setPoints(points1);
			lineGraph2.setPoints(points2);

		} else {

			Thread pointThread = new Thread(renderer);
			pointThread.start();
			
		}
	}

	Runnable renderer = new Runnable() {

		@Override
		public void run() {

			try {

				PointManager pManager = new PointManager(MainActivity.this, lineGraph1.getXMax(), lineGraph1.getYMax());
				points1 = pManager.getPoints();

				pManager = new PointManager(MainActivity.this, lineGraph2.getXMax(), lineGraph2.getYMax());
				points2 = pManager.getPoints();
				
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						lineGraph1.setPoints(points1);
						lineGraph2.setPoints(points2);
					}
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
		outState.putSerializable("points1", points1);
		outState.putSerializable("points2", points2);
	}

}
