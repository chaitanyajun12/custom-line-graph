package org.custom.graph.line;

import java.util.ArrayList;

import org.custom.graph.R;
import org.custom.graph.defaults.Defaults;
import org.custom.graph.exception.GraphException;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineGraph extends View {

	// Device and layout specific: Values will be in pixels.
	private int layoutWidth;
	private int layoutHeight;
	private float xRatio;
	private float yRatio;
	private float xSpacingPx;
	private float ySpacingPx;
	private float graphWidthPx;
	private float graphHeightPx;

	// User specific: Values will be in integers.
	private int xMax;
	private int yMax;
	private int xUnitSpacing;
	private int yUnitSpacing;
	private int axisColor;
	private int pointColor;
	private int lineColor;
	private int labelColor;
	private int axisPointColor;
	private String xLabel;
	private String yLabel;
	
	private Paint paint;
	private ArrayList<Point> points;
	
	public LineGraph(Context context) {
		
		super(context);
		paint = new Paint();
		setDefaults();
	}

	public LineGraph(Context context, AttributeSet attrs) throws GraphException {
		super(context, attrs);

		paint = new Paint();
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LineGraph, 0, 0);
		
		setXMax(a.getInteger(R.styleable.LineGraph_x_max, Defaults.X_MAX));
		setYMax(a.getInteger(R.styleable.LineGraph_y_max, Defaults.Y_MAX));
		setXunitSpacing(a.getInteger(R.styleable.LineGraph_x_unit_spacing, Defaults.X_UNIT_SPACING));
		setYunitSpacing(a.getInteger(R.styleable.LineGraph_y_unit_spacing, Defaults.Y_UNIT_SPACING));
		setAxisColor(a.getColor(R.styleable.LineGraph_axis_color, Color.BLACK));
		setPointColor(a.getInteger(R.styleable.LineGraph_point_color, Defaults.POINT_COLOR));
		setLineColor(a.getInteger(R.styleable.LineGraph_line_color, Defaults.LINE_COLOR));
		setLabelColor(a.getInteger(R.styleable.LineGraph_label_color, Defaults.LABEL_COLOR));
		setAxisPointColor(a.getColor(R.styleable.LineGraph_axis_point_color, Defaults.AXIS_POINT_COLOR));
		
		if(a.getString(R.styleable.LineGraph_x_label) == null)
			setxLabel("");
		else 
			setxLabel(a.getString(R.styleable.LineGraph_x_label));
		
		if(a.getString(R.styleable.LineGraph_x_label) == null)
			setyLabel("");
		else 
			setyLabel(a.getString(R.styleable.LineGraph_y_label));
		
		if(getXMax() == Defaults.X_MAX || getYMax() == Defaults.Y_MAX)
			throw new GraphException("Error: Compulsory axis size attribute missing!");
		
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		paint.setColor(axisColor);
		paint.setStrokeWidth(2);

		float originX = getPaddingLeft() + Defaults.AXIS_POINTER_SIZE;
		float originY = getPaddingTop() + graphHeightPx;
		
		canvas.drawLine(getPaddingLeft() + Defaults.AXIS_POINTER_SIZE, getPaddingTop(), getPaddingLeft() + Defaults.AXIS_POINTER_SIZE, getPaddingTop() + graphHeightPx, paint);
		canvas.drawLine(getPaddingLeft() + Defaults.AXIS_POINTER_SIZE, getPaddingTop() + graphHeightPx, getPaddingLeft() + graphWidthPx + Defaults.AXIS_POINTER_SIZE, getPaddingTop() + graphHeightPx, paint);
		
		for(int i=0; points != null && i<points.size(); i++) {
			
			if(i == (points.size()-1))
				break;
			
			float x1 = originX + (points.get(i).getX() * xRatio);
			float y1 = originY - (points.get(i).getY() * yRatio);
			
			float x2 = originX + (points.get(i+1).getX() * xRatio);
			float y2 = originY - (points.get(i+1).getY() * yRatio);

			paint.setColor(pointColor);
			canvas.drawPoint(x1, y1, paint);
			canvas.drawPoint(x2, y2, paint);
			
			paint.setColor(lineColor);
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
		
		paint.setColor(axisPointColor);
		for(int i=0; i <= (graphWidthPx/xSpacingPx); i++) {
			
			float offsetX = originX + (i * xSpacingPx);
			canvas.drawLine(offsetX, originY - Defaults.AXIS_POINTER_SIZE, offsetX, originY + Defaults.AXIS_POINTER_SIZE, paint);
		}
		
		for(int i=0; i <= (graphHeightPx/ySpacingPx); i++) {

			float offsetY = originY - (i * ySpacingPx);
			canvas.drawLine(originX - Defaults.AXIS_POINTER_SIZE, offsetY, originX + Defaults.AXIS_POINTER_SIZE, offsetY, paint);
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		setLayoutWidth(w);
		setLayoutHeight(h);
		
		graphWidthPx = getLayoutWidth() - getPaddingLeft() - getPaddingRight() - Defaults.AXIS_POINTER_SIZE;
		graphHeightPx = getLayoutHeight() - getPaddingTop() - getPaddingBottom() - Defaults.AXIS_POINTER_SIZE;
		
		xRatio = graphWidthPx/getXMax();
		yRatio = graphHeightPx/getYMax();
		
		xSpacingPx = xRatio * getXunitSpacing();
		ySpacingPx = yRatio * getYunitSpacing();

	}
	
	private void setDefaults() {
		
		xMax = Defaults.X_MAX;
		yMax = Defaults.Y_MAX;
		xUnitSpacing = Defaults.X_UNIT_SPACING;
		yUnitSpacing = Defaults.Y_UNIT_SPACING;
		
		axisColor = Defaults.AXIS_COLOR;
		pointColor = Defaults.POINT_COLOR;
		labelColor = Defaults.LABEL_COLOR;
		lineColor = Defaults.LINE_COLOR;
		axisPointColor = Defaults.AXIS_POINT_COLOR;
		
		xLabel = Defaults.X_LABEL;
		yLabel = Defaults.Y_LABEL;

	}

	public int getAxisColor() {
		return axisColor;
	}

	public void setAxisColor(int axisColor) {
		this.axisColor = axisColor;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public int getLineColor() {
		return lineColor;
	}

	public void setLineColor(int lineColor) {
		this.lineColor = lineColor;
	}

	public int getPointColor() {
		return pointColor;
	}

	public void setPointColor(int pointColor) {
		this.pointColor = pointColor;
	}

	public int getXunitSpacing() {
		return xUnitSpacing;
	}

	public void setXunitSpacing(int xUnitSpacing) {
		this.xUnitSpacing = xUnitSpacing;
	}
	
	public int getYunitSpacing() {
		return yUnitSpacing;
	}

	public void setYunitSpacing(int yUnitSpacing) {
		this.yUnitSpacing = yUnitSpacing;
	}

	public int getYMax() {
		return yMax;
	}

	public void setYMax(int yMax) {
		this.yMax = yMax;
	}

	public int getXMax() {
		return xMax;
	}

	public void setXMax(int xMax) {
		this.xMax = xMax;
	}

	public int getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(int labelColor) {
		this.labelColor = labelColor;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public int getLayoutWidth() {
		return layoutWidth;
	}

	public void setLayoutWidth(int layoutWidth) {
		this.layoutWidth = layoutWidth;
	}

	public int getLayoutHeight() {
		return layoutHeight;
	}

	public void setLayoutHeight(int layoutHeight) {
		this.layoutHeight = layoutHeight;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
		reDraw();
	}
	
	public void reDraw() {
		this.invalidate();
		requestLayout();
	}
	
	public void addPoint(Point point) {
		
		if(points == null)
			points = new ArrayList<Point>();
		points.add(point);
	}

	public int getAxisPointColor() {
		return axisPointColor;
	}

	public void setAxisPointColor(int axisPointColor) {
		this.axisPointColor = axisPointColor;
	}

}