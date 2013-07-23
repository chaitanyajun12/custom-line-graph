package org.custom.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.custom.graph.line.Point;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class PointManager {

	private String FILENAME = "points.txt";
	private ArrayList<Point> points;

	public PointManager(Context context, int xMax, int yMax) throws IOException {

		points = new ArrayList<Point>();
		AssetManager assets = context.getAssets();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				assets.open(FILENAME)));

		String line = br.readLine();
		while (line != null) {

			String[] coords = line.split(",");
			Integer x = Integer.parseInt(coords[0]);
			Integer y = Integer.parseInt(coords[1]);

			if (x <= xMax && y <= yMax) {

				Point point = new Point();
				point.setX(x);
				point.setY(y);
				points.add(point);
			}

			line = br.readLine();
		}

		Log.d("WOW", "No. of points: " + points.size());
		Collections.sort(points);
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

/*	private void print() {

		for (Point point : points) {
			Log.d("WOW", "X: " + point.getX() + ", Y: " + point.getY());
		}
	} */
}