package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;


import java.util.ArrayList;

public class KnjigaFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void izabranaKnjiga(String knjiga);
    }


    public static KnjigaFragment newInstance() {
        KnjigaFragment fragment = new KnjigaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = (View) inflater.inflate(R.layout.activity_knjiga_fragment, container, false);
        API.getJSON("http://10.0.2.2:5000/knjige", new ReadDataHandler(){
            @Override
            public void handleMessage(Message msg) {
                String odgovor = getJson();

                try {
                    JSONArray array = new JSONArray(odgovor);
                    //odgovor spakujemo u JSONArray, i onda parsiramo da bismo dobili linked list
                    ArrayList<Knjiga> knjige = Knjiga.parseJSONArray(array);
                    //postavljamo dobijeni povratni tekst kao tekst labele (ručno formatirano)

                    LinearLayout listaKnjiga = (LinearLayout) v.findViewById(R.id.listaKnjiga);

                    for (final Knjiga k : knjige) {

                        LinearLayout item = (LinearLayout) inflater.inflate(R.layout.knjiga_short, null);

                        TextView title = (TextView) item.findViewById(R.id.displayTitleShort);
                        title.setKeyListener(null);
                        title.setText(k.getTitle());

                        TextView availability = (TextView) item.findViewById(R.id.displayAvailabilityShort);
                        availability.setKeyListener(null);
                        availability.setText(k.getAvailability());

                        item.setTag(k.getId());

                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mListener.izabranaKnjiga(k.getId());
                            }
                        });

                        listaKnjiga.addView(item);
                    }

                } catch (Exception e) {

                }
            }
        });

        return v;
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


    @Override
    public void onClick(View view) {
        String knjiga = (String) view.getTag();

        if(mListener != null){
            mListener.izabranaKnjiga(knjiga);
        }
    }


}
