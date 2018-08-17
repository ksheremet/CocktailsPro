package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private static final String RECYCLER_VIEW_STATE = "recycler_view_state";
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private OnBeverageSelected mListener;
    private BeverageListAdapter mBeveragesAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BeverageListFragment() {
    }

    public void setBeverageList(List<Beverage> beverageList) {
        if (beverageList == null) {
            mListener.showError(getString(R.string.error_user_message));
            Log.d(TAG, "List of beverages is null");
        } else if (beverageList.size() == 0) {
            mListener.showError(getString(R.string.favourite_list_is_empty));
        } else {
            mListener.showData();
            mBeveragesAdapter.setBeverages(beverageList);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beverage_list, container, false);

        ButterKnife.bind(this, view);

        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(),
                getResources().getInteger(R.integer.grid_columns));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mBeveragesAdapter = new BeverageListAdapter(mListener);
        mRecyclerView.setAdapter(mBeveragesAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(final @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(RECYCLER_VIEW_STATE)) {
            final Parcelable savedRecyclerLayoutState =
                    savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
            // Use delayed runnable.
            // https://discussions.udacity.com/t/trouble-maintaining-recyclerview-position-upon-orientation-change/608216/12
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
                }
            }, 600);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_VIEW_STATE, mRecyclerView
                .getLayoutManager().onSaveInstanceState());
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

        void showData();

        void showError(String error);
    }
}
