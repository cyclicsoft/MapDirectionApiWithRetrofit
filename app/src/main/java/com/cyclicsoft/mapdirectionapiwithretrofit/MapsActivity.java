package com.cyclicsoft.mapdirectionapiwithretrofit;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.cyclicsoft.mapdirectionapiwithretrofit.listeners.MapApiService;
import com.cyclicsoft.mapdirectionapiwithretrofit.models.DirectionResponses;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng mFrom;
    private LatLng mTo;
    private static class RetrofitClient {
        static MapApiService apiServices(Context context) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(context.getResources().getString(R.string.base_url))
                    .build();

            return retrofit.create(MapApiService.class);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //two points for directions
        mFrom = new LatLng(23.80555,90.43586);
        mTo = new LatLng(23.789978,90.425066);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        MarkerOptions markerFkip = new MarkerOptions()
                .position(mFrom)
                .title("From");
        MarkerOptions markerMonas = new MarkerOptions()
                .position(mTo)
                .title("To");

        mMap.addMarker(markerFkip);
        mMap.addMarker(markerMonas);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mFrom, 11.6f));
        String fromLatLon = String.valueOf(mFrom.latitude) + "," + String.valueOf(mFrom.longitude);
        String toLatLon = String.valueOf(mTo.latitude) + "," + String.valueOf(mTo.longitude);

        MapApiService mapApiService = RetrofitClient.apiServices(this);
        mapApiService.getDirection(fromLatLon, toLatLon, getString(R.string.api_key))
                .enqueue(new Callback<DirectionResponses>() {
                    @Override
                    public void onResponse(@NonNull Call<DirectionResponses> call, @NonNull Response<DirectionResponses> response) {
                        drawPolyline(response);
                    }

                    @Override
                    public void onFailure(@NonNull Call<DirectionResponses> call, @NonNull Throwable t) {
                        Log.e("Api Error", t.getLocalizedMessage());
                    }
                });

    }

    private void drawPolyline(@NonNull Response<DirectionResponses> response) {
        if (response.body() != null) {
            String shape = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
            PolylineOptions polyline = new PolylineOptions()
                    .addAll(PolyUtil.decode(shape))
                    .width(8f)
                    .color(Color.RED);
            mMap.addPolyline(polyline);
        }
    }
}