package ir.technopedia.wordpressjsonclient.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ir.technopedia.wordpressjsonclient.R;
import ir.technopedia.wordpressjsonclient.util.Util;

/**
 * Created by user1 on 10/18/2016.
 */

public class AboutFragment extends Fragment {

    ImageView imgSocial1, imgSocial2, imgSocial3, imgSocial4;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        imgSocial1 = (ImageView) rootView.findViewById(R.id.social_1);
        imgSocial2 = (ImageView) rootView.findViewById(R.id.social_2);
        imgSocial3 = (ImageView) rootView.findViewById(R.id.social_3);
        imgSocial4 = (ImageView) rootView.findViewById(R.id.social_4);

        imgSocial1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openAdress(getActivity(), getString(R.string.social_1_adress));
            }
        });

        imgSocial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openAdress(getActivity(), getString(R.string.social_2_adress));
            }
        });

        imgSocial3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openAdress(getActivity(), getString(R.string.social_3_adress));
            }
        });

        imgSocial4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openAdress(getActivity(), getString(R.string.social_4_adress));
            }
        });


        return rootView;
    }
}