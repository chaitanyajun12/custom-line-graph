package org.custom.graph;

import java.io.IOException;
import java.util.ArrayList;

import org.custom.graph.line.LineGraph;
import org.custom.graph.line.Point;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private LineGraph lineGraph;
	private ArrayList<Point> points;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lineGraph = (LineGraph) findViewById(R.id.lineGraph);

		if (savedInstanceState != null) {

			points = (ArrayList<Point>) savedInstanceState
					.getSerializable("points");
			lineGraph.setPoints(points);

		} else {

			Thread pointThread = new Thread(renderer);
			pointThread.start();
		}
	}

	Runnable renderer = new Runnable() {

		@Override
		public void run() {

			try {

				Log.d("WOW", "Runnable called!");
				PointManager pManager = new PointManager(MainActivity.this,
						lineGraph.getXMax(), lineGraph.getYMax());
				points = pManager.getPoints();

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						lineGraph.setPoints(points);
					}
				});

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("points", points);
	}

}
