package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.arch.lifecycle.Observer;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.di.BeverageListFragmentComponent;
import ch.sheremet.katarina.cocktailspro.di.BeverageListViewModelModule;
import ch.sheremet.katarina.cocktailspro.di.DaggerBeverageListFragmentComponent;
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
    private OnBeverageSelected mListener;
    private BeverageListAdapter mBeveragesAdapter;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @Inject
    BeverageListViewModel mViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BeverageListFragment() {
    }

    public void setBeverageList(List<Beverage> mBeverageList) {
        mListener.showData();
        mBeveragesAdapter.setBeverages(mBeverageList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        //setRetainInstance(true);
        BeverageListFragmentComponent component = DaggerBeverageListFragmentComponent.builder()
                .beverageListViewModelModule
                        (new BeverageListViewModelModule(getActivity())).build();
        component.injectBeverageListFragment(this);
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

        mViewModel.getBeverageList().observe(this, new Observer<List<Beverage>>() {
            @Override
            public void onChanged(@Nullable List<Beverage> beverages) {
                Log.d(TAG, "New list of beverages arrived");
                setBeverageList(beverages);
            }
        });
        return view;
    }

    //TODO: save state with rotation
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
    public void onSaveInstanceState(Bundle outState) {
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
