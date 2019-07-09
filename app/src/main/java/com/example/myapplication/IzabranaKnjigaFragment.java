package com.example.myapplication;


import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;




public class IzabranaKnjigaFragment extends Fragment {

    private static final String ARG_KNJIGA = "knjiga";

    private String knjiga;

    private IzabranaKnjigaFragment.OnFragmentInteractionListener mListener;


    public IzabranaKnjigaFragment() {
        // Required empty public constructor
    }


    public static IzabranaKnjigaFragment newInstance(String knjiga) {
        IzabranaKnjigaFragment fragment = new IzabranaKnjigaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KNJIGA, knjiga);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            knjiga = getArguments().getString(ARG_KNJIGA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_izabrana_knjiga_fragment, container, false);

        LinearLayout izabranaKnjiga = v.findViewById(R.id.izabranaKnjiga);

        popunjavanjeKnjige(knjiga, izabranaKnjiga);

        return v;
    }

    public void zamenaKnjige(String knjiga){

        this.knjiga = knjiga;
        LinearLayout izabranaKnjiga = getView().findViewById(R.id.izabranaKnjiga);

        popunjavanjeKnjige(knjiga, izabranaKnjiga);

    }


    private void popunjavanjeKnjige(String fakultet, final LinearLayout linearLayout){
        //uklonimo prethodno postojece view-ove
        linearLayout.removeAllViews();


        if(fakultet != null){

            API.getJSON("http://10.0.2.2:5000/knjige/" + knjiga, new ReadDataHandler(){
                @Override
                public void handleMessage (Message msg){
                    String odgovor =getJson();

                    final LinearLayout item = (LinearLayout) getLayoutInflater().inflate(R.layout.knjiga, null);


                    try {
                        JSONObject objekat = new JSONObject(odgovor);

                        Knjiga izaKnjiga = Knjiga.parseJSON(objekat);

                        ImageView icon = item.findViewById(R.id.icon);
                        if (izaKnjiga.getGenre().equals("fantasy"))
                            icon.setImageResource(R.drawable.crown);
                        else if(izaKnjiga.getGenre().equals("sci-fi"))
                            icon.setImageResource(R.drawable.ufo);
                        else if(izaKnjiga.getGenre().equals("horror"))
                            icon.setImageResource(R.drawable.skeleton);

                        TextView author = (TextView) item.findViewById(R.id.displayAuthor);
                        author.setKeyListener(null);
                        author.setText("Author : " +izaKnjiga.getAuthor());

                        TextView title = (TextView) item.findViewById(R.id.displayTitle);
                        title.setKeyListener(null);
                        title.setText("Title : " +izaKnjiga.getTitle());


                        TextView avail = (TextView) item.findViewById(R.id.displayAvailability);
                        avail.setKeyListener(null);
                        avail.setText("Availability : "+izaKnjiga.getAvailability());


                        TextView genre = (TextView) item.findViewById(R.id.displayGenre);
                        genre.setKeyListener(null);
                        genre.setText("Genre : "+izaKnjiga.getGenre());


                        linearLayout.addView(item);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        }


//        if(fakultet != null) {
//            TextView p1 = new TextView(getContext());
//            p1.setText("Prvi predmet na fakultetu: " + fakultet);
//            p1.setPadding(10, 20, 10, 20);
//            p1.setTextSize(12);
//
//            TextView p2 = new TextView(getContext());
//            p2.setText("Drugi predmet na fakultetu: " + fakultet);
//            p2.setPadding(10, 20, 10, 20);
//            p2.setTextSize(12);
//
//            TextView p3 = new TextView(getContext());
//            p3.setText("Treci predmet na fakultetu: " + fakultet);
//            p3.setPadding(10, 20, 10, 20);
//            p3.setTextSize(12);
//
//            linearLayout.addView(p1);
//            linearLayout.addView(p2);
//            linearLayout.addView(p3);
//        }else{
//            TextView msg = new TextView(getContext());
//            msg.setText("Molimo odaberite jedan fakultet");
//            msg.setTextSize(14);
//            linearLayout.addView(msg);
//
//        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
