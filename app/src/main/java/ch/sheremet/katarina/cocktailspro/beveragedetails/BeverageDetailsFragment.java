package ch.sheremet.katarina.cocktailspro.beveragedetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.sheremet.katarina.cocktailspro.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeverageDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeverageDetailsFragment extends Fragment {

    public BeverageDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BeverageDetailsFragment.
     */
    public static BeverageDetailsFragment newInstance() {
        BeverageDetailsFragment fragment = new BeverageDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beverage_details, container, false);
    }

}
