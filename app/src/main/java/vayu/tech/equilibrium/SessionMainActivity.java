package vayu.tech.equilibrium;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SessionMainActivity extends AppCompatActivity {

    private String athleteId;
    private Trial trial;
    private ListView mListView;

    private ArrayList<String> mList;

    private String parentDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_main);

        mListView = (ListView) findViewById(R.id.SessionMainActivity_listView);

        athleteId = (String) getIntent().getExtras().get(Keys.EXTRA_ATHLETE_ID);
        trial = (Trial) getIntent().getExtras().get(Keys.EXTRA_TRIAL);
        parentDirectory = "demo_data/" + athleteId + "/" + trial.getTrialId();

        System.out.println("Athlete: " + athleteId);
        System.out.println("Trial:" + trial);
        
        readFiles();
    }

    private void readFiles() {
        System.out.println(parentDirectory);
        ArrayList<String> folders = null;
        try {
            folders = new ArrayList<>(Arrays.asList(getAssets().list(parentDirectory)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(folders);
        mList = new ArrayList<>();
        for (String file: folders) {
            System.out.println(file);
        }
        if (folders.contains("acceleration")) {
            mList.add("Acceleration");
        }
        if (folders.contains("velocity")) {
            mList.add("Velocity");
        }
        if (folders.contains("orientation")) {
            mList.add("Orientation");
        }
        if (folders.contains("stats")) {
            mList.add("Stats");
        }
        if (folders.contains("grf")) {
            mList.add("GRF");
        }
        fillListView();
        setupListListener();
    }

    private void fillListView() {
        // create an array of list items (just the date)
        String[] listItems = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            listItems[i] = mList.get(i);
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

        String tapped = mList.get(position);
        if (tapped.equals("Acceleration")) {
            accelerationTapped();
        } else if (tapped.equals("Velocity")) {
            velocityTapped();
        } else if (tapped.equals("Orientation")) {
            orientationTapped();
        } else if (tapped.equals("Stats")) {
            statsTapped();
        } else if (tapped.equals("GRF")) {
            grfTapped();
        }

        /*
        Intent intent = new Intent(context, SessionMainActivity.class);
        intent.putExtra(Keys.EXTRA_ATHLETE_ID, athlete.getUserId());
        intent.putExtra(Keys.EXTRA_TRIAL, athlete.getTrials().get(position));
        startActivity(intent);
        */
    }

    private void grfTapped() {
        // go to grF graph
        goToGraph("/grf/grf_values.json");
    }

    private void statsTapped() {
        // go to stats
    }

    private void velocityTapped() {
        final String choices[] = new String[] {"Foot", "Speed"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToGraph("/velocity/" + choices[which].toLowerCase() + ".json");
            }
        });
        builder.show();
    }

    private void accelerationTapped() {
        final String choices[] = new String[] {"Body", "Foot", "Forearm", "Shank", "Thigh", "Trunk", "Upperarm", "Wrist"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToGraph("/acceleration/" + choices[which].toLowerCase() + ".json");
            }
        });
        builder.show();
    }

    private void orientationTapped() {
        final String choices[] = new String[] {"Frontal", "Sagittal", "Transverse"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                orientationDetailTapped(choices[which]);
            }
        });
        builder.show();
    }

    private void orientationDetailTapped(final String type) {
        final String choices[] = new String[] {"Absolute", "Relative"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choices[which].equals("Absolute")) absoluteTapped(type, choices[which]);
                if (choices[which].equals("Relative")) relativeTapped(type, choices[which]);
            }
        });
        builder.show();
    }

    private void relativeTapped(final String type1, final String type2) {
        final String choices[] = new String[] {"Ankle", "Elbow", "Hip", "Knee", "Shoulder", "Wrist"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choices[which] == null) System.out.println("CHOICES NULL");
                goToGraph("/orientation/" + type1.toLowerCase() + "/" + type2.toLowerCase() + "/" + choices[which].toLowerCase() + ".json");
            }
        });
        builder.show();
    }

    private void absoluteTapped(final String type1, final String type2) {
        final String choices[] = new String[] {"Foot", "Forearm", "Hand", "Shank", "Thigh", "Trunk", "Upperarm"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choices[which] == null) System.out.println("CHOICES NULL");
                goToGraph("/orientation/" + type1.toLowerCase() + "/" + type2.toLowerCase() + "/" + choices[which].toLowerCase() + ".json");
            }
        });
        builder.show();
    }

    private void goToGraph(String path) {
        System.out.println("Path: " + parentDirectory + path);
        Intent intent = new Intent(SessionMainActivity.this, GraphActivity.class);
        intent.putExtra(Keys.EXTRA_FILE_PATH, parentDirectory + path);
        startActivity(intent);



        // System.out.println(FileUtils.readFile(getAssets(), parentDirectory + path));
    }


}
