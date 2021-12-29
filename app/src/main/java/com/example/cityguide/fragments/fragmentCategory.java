package com.example.cityguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.cityguide.R;
import com.example.cityguide.activity.categoryActivity;

public class fragmentCategory extends Fragment implements View.OnClickListener{


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragement_category, container, false);

        CardView policeStation=view.findViewById(R.id.cardview);
        CardView hospital=view.findViewById(R.id.hospital);
        CardView medicalstore=view.findViewById(R.id.medicalstore);
        CardView bank=view.findViewById(R.id.bank);
        CardView atm=view.findViewById(R.id.atm);
        CardView hotel=view.findViewById(R.id.hotel);
        CardView library=view.findViewById(R.id.library);
        CardView garden=view.findViewById(R.id.garden);
        CardView railwaystation=view.findViewById(R.id.railwaystation);
        CardView BusStation=view.findViewById(R.id.BusStation);
        CardView FireStation=view.findViewById(R.id.FireStation);
        CardView CoffeeShop=view.findViewById(R.id.CoffeeShop);
        CardView PetrolPump=view.findViewById(R.id.PetrolPump);
        CardView gym=view.findViewById(R.id.gym);
        CardView postoffice=view.findViewById(R.id.postoffice);
        CardView hindutemple=view.findViewById(R.id.hindutemple);
        CardView mosque=view.findViewById(R.id.mosque);
        CardView church=view.findViewById(R.id.church);
        CardView shops=view.findViewById(R.id.shops);
        CardView movie=view.findViewById(R.id.movie);
        CardView jewelry=view.findViewById(R.id.jewelry);
        CardView grociers=view.findViewById(R.id.grociers);
        CardView bakery=view.findViewById(R.id.bakery);
        CardView bookstore=view.findViewById(R.id.bookstore);
        CardView spa=view.findViewById(R.id.spa);
        CardView School=view.findViewById(R.id.School);
        CardView animalCare=view.findViewById(R.id.animalCare);

        policeStation.setOnClickListener(fragmentCategory.this);
        hospital.setOnClickListener(fragmentCategory.this);
        medicalstore.setOnClickListener(fragmentCategory.this);
        bank.setOnClickListener(fragmentCategory.this);
        atm.setOnClickListener(fragmentCategory.this);
        hotel.setOnClickListener(fragmentCategory.this);
        library.setOnClickListener(fragmentCategory.this);
        garden.setOnClickListener(fragmentCategory.this);
        railwaystation.setOnClickListener(fragmentCategory.this);
        BusStation.setOnClickListener(fragmentCategory.this);
        FireStation.setOnClickListener(fragmentCategory.this);
        CoffeeShop.setOnClickListener(fragmentCategory.this);
        PetrolPump.setOnClickListener(fragmentCategory.this);
        gym.setOnClickListener(fragmentCategory.this);
        postoffice.setOnClickListener(fragmentCategory.this);
        hindutemple.setOnClickListener(fragmentCategory.this);
        mosque.setOnClickListener(fragmentCategory.this);
        church.setOnClickListener(fragmentCategory.this);
        animalCare.setOnClickListener(fragmentCategory.this);
        School.setOnClickListener(fragmentCategory.this);
        spa.setOnClickListener(fragmentCategory.this);
        bookstore.setOnClickListener(fragmentCategory.this);
        bakery.setOnClickListener(fragmentCategory.this);
        grociers.setOnClickListener(fragmentCategory.this);
        jewelry.setOnClickListener(fragmentCategory.this);
        animalCare.setOnClickListener(fragmentCategory.this);
        movie.setOnClickListener(fragmentCategory.this);
        shops.setOnClickListener(fragmentCategory.this);


        return view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview:
                Intent i=new Intent(getContext(), categoryActivity.class);
                i.putExtra("query","police station");
                startActivity(i);
                break;
            case R.id.hospital:

                Intent i1=new Intent(getContext(), categoryActivity.class);
                i1.putExtra("query","hospital");
                startActivity(i1);
                break;
            case R.id.medicalstore:

                Intent i2=new Intent(getContext(), categoryActivity.class);
                i2.putExtra("query","medical store");
                startActivity(i2);
                break;

            case R.id.bank:

                Intent i3=new Intent(getContext(), categoryActivity.class);
                i3.putExtra("query","bank");
                startActivity(i3);
                break;

            case R.id.atm:
                Intent i4=new Intent(getContext(), categoryActivity.class);
                i4.putExtra("query","atm");
                startActivity(i4);
                break;

            case R.id.hotel:
                Intent i5=new Intent(getContext(), categoryActivity.class);
                i5.putExtra("query","hotel");
                startActivity(i5);

                break;

            case R.id.library:
                Intent i6=new Intent(getContext(), categoryActivity.class);
                i6.putExtra("query","library");
                startActivity(i6);
                break;

            case R.id.garden:
                Intent i7=new Intent(getContext(), categoryActivity.class);
                i7.putExtra("query","garden");
                startActivity(i7);
                break;

            case R.id.railwaystation:
                Intent i8=new Intent(getContext(), categoryActivity.class);
                i8.putExtra("query","railway station");
                startActivity(i8);
                break;

            case R.id.BusStation:
                Intent i9=new Intent(getContext(), categoryActivity.class);
                i9.putExtra("query","bus stand");
                startActivity(i9);
                break;

            case R.id.FireStation:
                Intent i10=new Intent(getContext(), categoryActivity.class);
                i10.putExtra("query","Fire Station");
                startActivity(i10);
                break;

            case R.id.CoffeeShop:

                Intent i11=new Intent(getContext(), categoryActivity.class);
                i11.putExtra("query","Coffee");
                startActivity(i11);
                break;

            case R.id.PetrolPump:

                Intent i12=new Intent(getContext(), categoryActivity.class);
                i12.putExtra("query","petrol pump");
                startActivity(i12);
                break;

            case R.id.gym:

                Intent i13=new Intent(getContext(), categoryActivity.class);
                i13.putExtra("query","gym");
                startActivity(i13);
                break;

            case R.id.postoffice:

                Intent i14=new Intent(getContext(), categoryActivity.class);
                i14.putExtra("query","post office");
                startActivity(i14);
                break;

            case R.id.hindutemple:

                Intent i15=new Intent(getContext(), categoryActivity.class);
                i15.putExtra("query","temple");
                startActivity(i15);
                break;

            case R.id.mosque:

                Intent i16=new Intent(getContext(), categoryActivity.class);
                i16.putExtra("query","mosque");
                startActivity(i16);
                break;

            case R.id.church:

                Intent i17=new Intent(getContext(), categoryActivity.class);
                i17.putExtra("query","church");
                startActivity(i17);
                break;

            case R.id.shops:

                Intent i18=new Intent(getContext(), categoryActivity.class);
                i18.putExtra("query","malls");
                startActivity(i18);
                break;

            case R.id.movie:

                Intent i19=new Intent(getContext(), categoryActivity.class);
                i19.putExtra("query","movie");
                startActivity(i19);
                break;

            case R.id.jewelry:

                Intent i20=new Intent(getContext(), categoryActivity.class);
                i20.putExtra("query","jewelry");
                startActivity(i20);
                break;

            case R.id.grociers:

                Intent i21=new Intent(getContext(), categoryActivity.class);
                i21.putExtra("query","grociers");
                startActivity(i21);
                break;

            case R.id.bakery:

                Intent i22=new Intent(getContext(), categoryActivity.class);
                i22.putExtra("query","bakery");
                startActivity(i22);
                break;

            case R.id.bookstore:

                Intent i23=new Intent(getContext(), categoryActivity.class);
                i23.putExtra("query","book store");
                startActivity(i23);
                break;

            case R.id.spa:

                Intent i24=new Intent(getContext(), categoryActivity.class);
                i24.putExtra("query","spa");
                startActivity(i24);
                break;

            case R.id.School:
                Intent i25=new Intent(getContext(), categoryActivity.class);
                i25.putExtra("query","school");
                startActivity(i25);
                break;

            case R.id.animalCare:
                Intent i26=new Intent(getContext(), categoryActivity.class);
                i26.putExtra("query","animal care");
                startActivity(i26);
                break;






        }
}



}

