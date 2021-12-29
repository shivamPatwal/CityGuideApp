package com.example.cityguide.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cityguide.activity.MainActivity;
import com.example.cityguide.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class fragmentLocation extends Fragment {
TextView textView1,textView2,textView3;
   public Location currentLocation;

    private static final int REQUEST_CODE = 101;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_location, container, false);

        textView1=view.findViewById(R.id.text1);
        textView2=view.findViewById(R.id.text2);
        textView3=view.findViewById(R.id.text3);


        fetchLocation();

    return  view;
    }
    private void fetchLocation() {
        MainActivity a=new MainActivity();
                    currentLocation = a.getLoction();
                    Log.d("solve", String.valueOf(currentLocation.getLatitude()));
                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses = null;


                        addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                        if (addresses != null && addresses.size() > 0) {
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();

                            String pincode = addresses.get(0).getPostalCode();
                            String localAdrress1 = addresses.get(0).getLocality();

                            String city = addresses.get(0).getSubAdminArea();
                            String localAddres2 = addresses.get(0).getSubLocality();
                            String add=addresses.get(0).getAddressLine(0);

                            Log.d("a\n",add);
                            String[] arrOfStr = add.split(",");

                  textView1.setText(arrOfStr[1]+","+arrOfStr[2]);
                  textView2.setText(city+","+pincode);
                  textView3.setText(state+","+country);

                       }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
}


