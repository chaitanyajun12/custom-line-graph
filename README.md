Custom Line Graph
=================

This is an example which creates a custom line graph. The coordinates are retrieved from a text file in the assets folder.
 
There are custom attributes provided for the custom line graph.
  - The attributes `x_max` and `y_max` are mandatory, which denote the length of x-axis and y-axis. Otherwise, `GraphException` will be thrown. 
  - Other attributes are not mandatory, but it would be good if we can provide stuff like `x_unit_spacing` and `y_unit_spacing`. All custom attributes can be found at `res/values/attrs.xml`
  - By default, all sides padding for the graph is zero.
  - The project shows an activity which retrieves all the points using `PointManager` and then passes them to the 
    `LineGraph`.
  - Graph can be created both dynamically and using Android XML.
  - Problems due to screen rotations is also handled.

Example
-------

Creating a custom line graph,

1. Using Android XML,

```
      <org.custom.graph.line.LineGraph
        xmlns:custom="http://schemas.android.com/apk/res/org.custom.graph"
        android:id="@+id/lineGraph"
        android:layout_width="300dp"
        android:layout_height="100dp"
        android:paddingLeft="10dp"
        custom:label_color="#ff0000"
        custom:x_label="runs"
        custom:x_max="100"
        custom:x_unit_spacing="10"
        custom:y_max="400"
        custom:y_unit_spacing="10" />
```

2. Dynamically,

```
      LineGraph lineGraph = new LineGraph(this);
      lineGraph.setXMax(300);
      lineGraph.setYMax(100);
      lineGraph.setXUnitSpacing(10);
      lineGraph.setYUnitSpacing(10);
      lineGraph2.setLineColor(Color.RED);
      
      layout.addView(lineGraph);
```

Yet to come
-----------

1. <del>Dynamic creation of Custom Line Graph.</del> - **Done!**
2. Providing more options for customization.
3. Asynchronous loading of points within the graph.
