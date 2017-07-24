package vayu.tech.equilibrium;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectAthleteActivity extends AppCompatActivity {

    private SimpleCoach coach;
    private View progressOverlay;

    // The announcements ListView
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_athlete);
        mListView = (ListView) findViewById(R.id.SelectAthleteActivity_listView);
        progressOverlay = findViewById(R.id.progress_overlay);
        coach = (SimpleCoach) this.getIntent().getExtras().get(Keys.EXTRA_SIMPLE_COACH);
        // startDownloadProcess(); MODIFIED FOR DEMO

        // ADDED FOR DEMO
        setupDemo();
        // START
        fillListView();
        setupListListener();
        // END
    }

    private void setupDemo() {
        ArrayList<HashMap<String, String>> athletes = new ArrayList<>();

        HashMap<String, String> athlete1 = new HashMap<>();
        athlete1.put("userId", "0");
        athlete1.put("name", "Dhiraj");
        athletes.add(athlete1);

        HashMap<String, String> athlete2 = new HashMap<>();
        athlete2.put("userId", "1");
        athlete2.put("name", "Karan");
        athletes.add(athlete2);

        coach.setAthletes(athletes);
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
        parameters.put("user", Integer.toString(coach.getUser().getUserId()));
        parameters.put("org", Integer.toString(coach.getUser().getOrgId()));
        String link = LinkUtils.buildURL("athletes", parameters);
        String file = StringUtils.getUrlContents(link);
        receivedFile(file);
    }

    private void receivedFile(String file) {
        System.out.println(file);
        parseJSON(file);
        /*
        if (message.contains("You don't have the permission to log in or your account is no longer active")) {
            loginFailed(message.replace("\"", ""));
        } else {
            // message must be a JSON file
            parseJSON(message);
        }*/
    }

    private void parseJSON(String message) {
        try {
            JSONArray arr = new JSONArray(message.trim());
            ArrayList<HashMap<String, String>> athletes = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                HashMap<String, String> athlete = new HashMap<>();
                athlete.put("userId", obj.get(Keys.USER_ID).toString());
                athlete.put("name", obj.get(Keys.NAME).toString());
                athletes.add(athlete);
            }
            coach.setAthletes(athletes);
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
        String[] listItems = new String[coach.getAthletes().size()];
        for (int i = 0; i < coach.getAthletes().size(); i++) {
            HashMap<String, String> athleteData = coach.getAthletes().get(i);
            listItems[i] = athleteData.get(Keys.NAME);
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
        HashMap<String, String> athleteData = coach.getAthletes().get(position);
        String name = athleteData.get(Keys.NAME);
        String userId = athleteData.get(Keys.USER_ID);

        // pass an empty list
        ArrayList<Trial> list = new ArrayList<>();
        SimpleAthlete athlete = new SimpleAthlete(name, userId, list);


        // Added for demo
        // ***** START ******
        ArrayList<Trial> trials = new ArrayList<>();
        if (athlete.getUserId().equals("0")) {
            // Dhiraj
            String[] trialNames = new String[] {"Abs - 1",
                    "Jog - 1", "Jog - 2",
                    "Pull Ups - 1", "Pull Ups - 2",
                    "Range of Motion - 2",
                    "Run - 1", "Run - 2",
                    "Squat - 1", "Squat - 2",
                    "Stand-Sit-Stand - 1", "Stand-Sit-Stand - 2",
                    "Toe Touch - 1",
                    "Walk - 1", "Walk - 2"};

            for (int i = 0; i < trialNames.length; i++) {
                Trial trial = new Trial(i + 1, trialNames[i]);
                trials.add(trial);
            }
            athlete.setTrials(trials);
        } else if (athlete.getUserId().equals("1")) {
            // Karan
            String[] trialNames = new String[] {"Back Kick - 1", "Back Kick - 2",
            "Bounding Drill - 1", "Bounding Drill - 2", "Fast Leg Drill - 1",
            "Fast Leg Drill - 2", "Hurdles - 1", "Hurdles - 2", "Run - 1",
            "Straight Leg Drill - 1", "Straight Leg Drill - 2"};

            for (int i = 0; i < trialNames.length; i++) {
                Trial trial = new Trial(i + 1, trialNames[i]);
                trials.add(trial);
            }
            athlete.setTrials(trials);
        }
        // ***** END ******


        Intent intent = new Intent(context, SelectSessionActivity.class);
        intent.putExtra(Keys.EXTRA_SIMPLE_ATHLETE, athlete);
        startActivity(intent);
    }
}
