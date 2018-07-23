package ch.sheremet.katarina.cocktailspro.beveragedetails;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.di.BeverageDetailsFragmentComponent;
import ch.sheremet.katarina.cocktailspro.di.BeverageDetailsViewModelModule;
import ch.sheremet.katarina.cocktailspro.di.DaggerBeverageDetailsFragmentComponent;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeverageDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeverageDetailsFragment extends Fragment {

    private static final String TAG = BeverageDetailsFragment.class.getSimpleName();
    private static final String DRINK_ID_PARAM = "drink_id";

    @Inject
    BeverageDetailsViewModel mViewModel;

    public BeverageDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BeverageDetailsFragment.
     */
    public static BeverageDetailsFragment newInstance(@NonNull final String id) {
        BeverageDetailsFragment fragment = new BeverageDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DRINK_ID_PARAM, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BeverageDetailsFragmentComponent component = DaggerBeverageDetailsFragmentComponent.builder()
                .beverageDetailsViewModelModule
                        (new BeverageDetailsViewModelModule(getActivity())).build();
        component.injectBeverageDetailsFragment(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String id = bundle.getString(DRINK_ID_PARAM);
            mViewModel.fetchBeverageByID(id);
        } else {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beverage_details, container, false);


        mViewModel.getBeverageDetails().observe(this, new Observer<BeverageDetails>() {
            @Override
            public void onChanged(@Nullable BeverageDetails beverageDetails) {
                if (beverageDetails != null) {
                    Log.d(TAG, beverageDetails.toString());
                }
            }
        });


        return view;
    }

}
