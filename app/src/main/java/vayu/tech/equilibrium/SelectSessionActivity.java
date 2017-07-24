package vayu.tech.equilibrium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectSessionActivity extends AppCompatActivity {

    private SimpleAthlete athlete;
    private View progressOverlay;

    // The announcements ListView
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);
        mListView = (ListView) findViewById(R.id.SelectSessionActivity_listView);
        progressOverlay = findViewById(R.id.progress_overlay);
        athlete = (SimpleAthlete) this.getIntent().getExtras().get(Keys.EXTRA_SIMPLE_ATHLETE);
        // startDownloadProcess(); MODIFIED FOR DEMO
        // setupDemo();

        // ADDED FOR DEMO
        // START
        fillListView();
        setupListListener();
        // END
    }


    private void startDownloadProcess() {
        progressOverlay.bringToFront();
        // Show progress overlay (with animation):
        GlobalUtils.animateView(progressOverlay, View.VISIBLE, 0.4f, 200);
        startDownload();
    }

    private void startDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFiles();
            }
        }).start();
    }

    private void downloadFiles() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("user", athlete.getUserId());
        String link = LinkUtils.buildURL("trials", parameters);
        String file = StringUtils.getUrlContents(link);
        receivedFile(file);
    }

    private void receivedFile(String file) {
        System.out.println(file);
        parseJSON(file);
    }

    private void parseJSON(String message) {
        try {
            JSONArray arr = new JSONArray(message.trim());
            ArrayList<Trial> trials = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String trialIdString = obj.getString(Keys.LICENSE_ID);
                int trialId = -1;
                if (!trialIdString.equals("null")) {
                    trialId = Integer.parseInt(trialIdString);
                }
                Trial trial = new Trial(Integer.parseInt(obj.getString(Keys.TRIAL_ID)),
                        obj.getString(Keys.NAME),
                        obj.getString(Keys.TRIAL_TYPE),
                        GlobalUtils.boolFrom(obj.getString(Keys.IS_ARCHIVED)),
                        Integer.parseInt(obj.getString(Keys.USER_ID)),
                        trialId,
                        obj.getString(Keys.TIMESTAMP));
                trials.add(trial);
            }
            athlete.setTrials(trials);
            finishDownload();
        } catch (JSONException e) {
            System.out.println("Parsing JSON Failed!");
            e.printStackTrace();
        }
    }

    private void finishDownload() {
        runOnUiThread(new Runnable() {
            public void run() {
                GlobalUtils.animateView(progressOverlay, View.GONE, 0, 200);
                fillListView();
                setupListListener();
            }
        });
    }

    private void fillListView() {
        // create an array of list items (just the date)
        String[] listItems = new String[athlete.getTrials().size()];
        for (int i = 0; i < athlete.getTrials().size(); i++) {
            Trial trial = athlete.getTrials().get(i);
            listItems[i] = trial.getName();
        }

        // make the list view show that array
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }

    private void setupListListener() {
        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tappedCell(position, context);
            }
        });
    }

    // called when the user taps on a item in the list
    private void tappedCell(int position, Context context) {
        Intent intent = new Intent(context, SessionMainActivity.class);
        intent.putExtra(Keys.EXTRA_ATHLETE_ID, athlete.getUserId());
        intent.putExtra(Keys.EXTRA_TRIAL, athlete.getTrials().get(position));
        startActivity(intent);
    }

}
