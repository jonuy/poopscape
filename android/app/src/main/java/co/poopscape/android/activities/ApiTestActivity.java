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
import co.poopscape.android.network.models.Review;
import co.poopscape.android.network.models.User;

public class ApiTestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mResponseView;
    String mTestLocId = "";
    String mTestUserId = "";

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
        Button btnTestNewReview = (Button) findViewById(R.id.testNewReview);
        Button btnTestNewUser = (Button) findViewById(R.id.testNewUser);
        Button btnTestGetUserByEmail = (Button) findViewById(R.id.testGetUserByEmail);
        Button btnTestGetUserById = (Button) findViewById(R.id.testGetUserById);
        Button btnTestUpdateUser = (Button) findViewById(R.id.testUpdateUser);

        btnTestNewLocation.setOnClickListener(this);
        btnTestGetLocNearPoint.setOnClickListener(this);
        btnTestGetLocById.setOnClickListener(this);
        btnTestNewReview.setOnClickListener(this);
        btnTestNewUser.setOnClickListener(this);
        btnTestGetUserByEmail.setOnClickListener(this);
        btnTestGetUserById.setOnClickListener(this);
        btnTestUpdateUser.setOnClickListener(this);

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
            case R.id.testNewReview:
                postNewReview();
                break;
            case R.id.testNewUser:
                postNewUser();
                break;
            case R.id.testGetUserByEmail:
                getUserByEmail();
                break;
            case R.id.testGetUserById:
                getUserById();
                break;
            case R.id.testUpdateUser:
                updateUser();
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

                toastOnSuccess();
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

                toastOnSuccess();
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

                toastOnSuccess();
            }
        };

        api.getLocationById(mTestLocId, listener);
    }

    private void postNewReview() {
        if (mTestLocId.isEmpty()) {
            Toast.makeText(this, "Post a new location or get locations near a point first", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mTestUserId.isEmpty()) {
            Toast.makeText(this, "Post a new user or get user first", Toast.LENGTH_SHORT).show();
            return;
        }

        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                Review review = (Review)o;

                mResponseView.setText(review.toString());

                toastOnSuccess();
            }
        };

        int rating = 5;
        String review = "Lorem ipsum dolor sit test review amet.";
        api.postNewReview(mTestLocId, mTestUserId, rating, review, listener);
    }

    private void postNewUser() {
        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                User user = (User)o;

                mTestUserId = user.getId();

                mResponseView.setText(user.toString());

                toastOnSuccess();
            }
        };

        String firstName = "TestFName";
        String lastInit = "L";
        String email = "testemail@example.com";
        api.postNewUser(firstName, lastInit, email, listener);
    }

    private void getUserByEmail() {
        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener listener = new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                mTestUserId = user.getId();
                mResponseView.setText(user.toString());

                toastOnSuccess();
            }
        };

        String email = "testemail@example.com";
        api.getUserByEmail(email, listener);
    }

    private void getUserById() {
        if (mTestUserId.isEmpty()) {
            Toast.makeText(this, "Post a new user or get user first", Toast.LENGTH_SHORT).show();
            return;
        }

        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener<User> listener = new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                mResponseView.setText(user.toString());

                toastOnSuccess();
            }
        };

        api.getUserById(mTestUserId, listener);
    }

    private void updateUser() {
        PoopscapeAPI api = PoopscapeAPI.getInstance(this);

        Response.Listener<User> listener = new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                mResponseView.setText(user.toString());

                toastOnSuccess();
            }
        };

        User updatedUser = new User.UserBuilder()
                .fname("UpdatedFName")
                .linit("Z")
                .email("updatedEmail@example.com")
                .build();
        api.updateUser(mTestUserId, updatedUser, listener);
    }

    private void toastOnSuccess() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }
}
