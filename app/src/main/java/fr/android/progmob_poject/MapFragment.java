package fr.android.progmob_poject;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapFragment extends Fragment {

    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //Initialize map fragment
        SupportMapFragment supportmapfragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        mainActivity = (MainActivity) getActivity();

        //Assync map
        supportmapfragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //When clicked on map
                        //Initialize marker point
                        MarkerOptions markerOptions = new MarkerOptions();
                        //Set position of marker
                        markerOptions.position(latLng);
                        //Set title of marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        //Remove all marker
                        googleMap.clear();
                        //Animating to zoom marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        //Add marker on map
                        googleMap.addMarker(markerOptions);
                        String address = getAddressFromLocation(latLng.latitude, latLng.longitude);
                        if(address != null)
                            Toast.makeText(mainActivity, address, Toast.LENGTH_LONG).show();

                        CachePot.getInstance().push("latLng", latLng);
                        if(address != null)
                            CachePot.getInstance().push("address", address);
                    }
                });
            }


        });

        //Return view
        return view;
    }

    private String getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(mainActivity, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0).getLocality();
        }
        catch (IOException e) {
            e.printStackTrace();
            printToast("Could not get address..!");
        }
    return null;
    }

    private void printToast(String s) {
    }
}




