package co.poopscape.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import co.poopscape.android.R;
import co.poopscape.android.network.PoopscapeAPI;
import co.poopscape.android.network.models.Location;
import co.poopscape.android.network.models.LocationDistResponse;

public class ApiTestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mResponseView;
    String mTestLocId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResponseView = (TextView) findViewById(R.id.results);
        mResponseView.setMovementMethod(new ScrollingMovementMethod());

        Button btnTestNewLocation = (Button) findViewById(R.id.testNewLocation);
        Button btnTestGetLocNearPoint = (Button) findViewById(R.id.testGetLocNearPoint);
        Button btnTestGetLocById = (Button) findViewById(R.id.testGetLocById);

        btnTestNewLocation.setOnClickListener(this);
        btnTestGetLocNearPoint.setOnClickListener(this);
        btnTestGetLocById.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.testNewLocation:
                postNewLocation();
                break;
            case R.id.testGetLocNearPoint:
                getLocNearPoint();
                break;
            case R.id.testGetLocById:
                getLocById();
                break;
        }

    }

    private void postNewLocation() {
        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        String name = "ApiTest Location";
        String street = "ApiTest Street Address";
        String city = "ApiTest City Address";
        String state = "NY";
        float lat = 12.3456f;
        float lng = 45.6789f;
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                Location loc = (Location) o;
                mTestLocId = loc.getId();

                mResponseView.setText(loc.toString());
            }
        };

        api.postNewLocation(name, street, city, state, lat, lng, listener);
    }

    private void getLocNearPoint() {
        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                LocationDistResponse[] locs = (LocationDistResponse[])o;

                String results = "";
                for (int i = 0; i < locs.length; i++) {
                    if (i == 0) {
                        mTestLocId = locs[i].getLocation().getId();
                    }

                    results += locs[i].toString() + "\n---\n";
                }

                mResponseView.setText(results);
            }
        };

        float lat = 12.3456f;
        float lng = 45.6789f;
        api.getLocationNearPoint(lat, lng, listener);
    }

    private void getLocById() {
        if (mTestLocId.isEmpty()) {
            Toast.makeText(this, "Post a new location or get locations near a point first", Toast.LENGTH_SHORT).show();
            return;
        }
        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                Location loc = (Location)o;

                mResponseView.setText(loc.toString());
            }
        };

        api.getLocationById(mTestLocId, listener);
    }

}
