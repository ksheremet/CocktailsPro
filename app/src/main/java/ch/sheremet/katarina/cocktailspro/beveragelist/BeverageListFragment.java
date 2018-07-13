package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnBeverageSelected}
 * interface.
 */
public class BeverageListFragment extends Fragment {

    private static final String TAG = BeverageListFragment.class.getSimpleName();

    // TODO: Customize parameters
    private OnBeverageSelected mListener;
    private BeverageListAdapter mBeveragesAdapter;
    private GridLayoutManager mLayoutManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BeverageListFragment() {
    }

    public void setBeverageList(List<Beverage> mBeverageList) {
        mBeveragesAdapter.setBeverages(mBeverageList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beverage_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        mLayoutManager = new GridLayoutManager(view.getContext(),
                getResources().getInteger(R.integer.grid_columns));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        mBeveragesAdapter = new BeverageListAdapter(mListener);
        recyclerView.setAdapter(mBeveragesAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBeverageSelected) {
            mListener = (OnBeverageSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBeverageSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnBeverageSelected {
        void onBeverageClicked(Beverage beverage);
    }
}
