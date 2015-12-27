package co.poopscape.android.fragment;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.poopscape.android.R;

/**
 * Created by jon on 11/16/15.
 */
public class MainMapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final String TAG = "MainMap";

    private static final int MAP_ZOOM = 17;

    // Map things
    private MapView mMapView;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private MutableLatLng mCurrentLoc;

    public static MainMapFragment newInstance() {
        return new MainMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentLoc = new MutableLatLng();

        setupGoogleApiClient();
        createLocationRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_map, container, false);

        mMapView = (MapView)rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        mMapView.onLowMemory();
    }

    protected synchronized void setupGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (loc != null) {
            mCurrentLoc.latitude = loc.getLatitude();
            mCurrentLoc.longitude = loc.getLongitude();
            Log.v("MAP", loc.getLatitude() + ", " + loc.getLongitude());
        }

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("MAP", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("MAP", "Connection failed");
    }

    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
            Log.v("MAP UPDATE", loc.getLatitude() + ", " + loc.getLongitude());
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Zoom map to current location
        if (mCurrentLoc.latitude == 0 && mCurrentLoc.longitude == 0) {
            LocationManager locMgr = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location loc = locMgr.getLastKnownLocation(locMgr.getBestProvider(criteria, false));
            if (loc != null) {
                mCurrentLoc.latitude = loc.getLatitude();
                mCurrentLoc.longitude = loc.getLongitude();
            }
        }

        if (mCurrentLoc.latitude != 0 && mCurrentLoc.longitude != 0) {
            LatLng latLng = new LatLng(mCurrentLoc.latitude, mCurrentLoc.longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM));
        }
    }

    /**
     * Basic data structure for storing lat,lng coordinates.
     */
    private class MutableLatLng {
        public double latitude;
        public double longitude;

        public MutableLatLng() {
            latitude = 0;
            longitude = 0;
        }
    }
}
