package vayu.tech.equilibrium;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private LineData lineData;
    private LineChart chart;

    private ArrayList<Integer> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        lineData = new LineData();
        chart = (LineChart) findViewById(R.id.GraphActivity_chart);
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.GRAY);
        colors.add(Color.YELLOW);

        String path = getIntent().getExtras().getString(Keys.EXTRA_FILE_PATH);
        downloadJSON(path);

        // in this example, a LineChart is initialized from xml

    }

    private void downloadJSON(String path) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(FileUtils.readFile(getAssets(), path));
            parseJSON(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(obj.toString());
    }

    private void parseJSON(JSONObject obj) {
        if (obj.has("right") && (obj.has("left"))) {
            JSONObject right = null;
            JSONObject left = null;
            try {
                right = obj.getJSONObject("right");
                left = obj.getJSONObject("left");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (right.has("x") && right.has("y") && right.has("z") && left.has("x") && left.has("y") && left.has("z")) {
                addSideToGraph(right, "Right");
                addSideToGraph(left, "Left");
                finishGraphSetup();
            } else {
                System.out.println("Ending here");
                addGraph(right, "Right");
                addGraph(left, "Left");
                finishGraphSetup();
            }
        } else if (obj.has("x") && obj.has("y") && obj.has("z")) {
            JSONObject x = null;
            JSONObject y = null;
            JSONObject z = null;
            try {
                x = obj.getJSONObject("x");
                y = obj.getJSONObject("y");
                z = obj.getJSONObject("z");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addGraph(x, "X");
            addGraph(y, "Y");
            addGraph(z, "Z");
            finishGraphSetup();
        } else {
            Iterator it = obj.keys();
            while (it.hasNext()) {
                if (it.next() instanceof JSONArray) {
                    // grf values, handle specially
                } else {
                    addGraph(obj, "Graph");
                    finishGraphSetup();
                    break;
                }
            }
        }
    }

    private void addSideToGraph(JSONObject side, String sideName) {
        Iterator<String> it = side.keys();
        while (it.hasNext()) {
            String key = it.next();
            try {
                addGraph(side.getJSONObject(key), sideName + " " + key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addGraph(JSONObject jsonObject, String name) {
        // adds a graph using the low level JSON object
        System.out.println("ADD THIS GRAPH: " + name);
        Iterator<String> it = jsonObject.keys();
        List<Entry> entries = new ArrayList<Entry>();
        while (it.hasNext()) {
            String xValue = it.next();
            Double yValue = -1.0;
            try {
                yValue = jsonObject.getDouble(xValue);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            entries.add(new Entry(Float.parseFloat(xValue), Double.valueOf(yValue).floatValue()));
        }
        LineDataSet dataSet = new LineDataSet(entries, name); // add entries to dataset
        Integer color = colors.remove(0);
        dataSet.setColor(color);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setCircleColor(color);
        dataSet.setLineWidth((float) 2.0);
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(color);
        dataSet.setHighLightColor(color);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(true);
        lineData.addDataSet(dataSet);
    }

    private void finishGraphSetup() {
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }
}
