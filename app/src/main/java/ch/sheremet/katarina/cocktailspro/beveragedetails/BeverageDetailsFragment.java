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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.di.BeverageDetailsFragmentComponent;
import ch.sheremet.katarina.cocktailspro.di.BeverageDetailsViewModelModule;
import ch.sheremet.katarina.cocktailspro.di.DaggerBeverageDetailsFragmentComponent;
import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.Ingredients;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeverageDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeverageDetailsFragment extends Fragment {

    private static final String TAG = BeverageDetailsFragment.class.getSimpleName();
    private static final String BEVERAGE_PARAM = "drink_id";

    @BindView(R.id.detail_beverage_name_tv)
    TextView mName;
    @BindView(R.id.add_to_favourite_iv)
    ImageView mAddToFavourite;
    @BindView(R.id.details_ingredients_tv)
    TextView mIngredients;
    @BindView(R.id.detail_instructions_tv)
    TextView mInstructions;
    @BindView(R.id.detail_glass_tv)
    TextView mGlassType;
    @BindView(R.id.detail_beverage_thumbnail_iv)
    ImageView mThumbnail;
    @BindView(R.id.detail_category_tv)
    TextView mCagegory;
    @BindView(R.id.detail_iba_tv)
    TextView mIba;

    private boolean mIsFavourite = false;
    private Beverage mBeverage;

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
    public static BeverageDetailsFragment newInstance(@NonNull final Beverage beverage) {
        BeverageDetailsFragment fragment = new BeverageDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BEVERAGE_PARAM, beverage);
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
            mBeverage = bundle.getParcelable(BEVERAGE_PARAM);
            mViewModel.fetchBeverageByID(mBeverage.getId());
        } else {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beverage_details, container, false);
        ButterKnife.bind(this, view);
        mViewModel.getBeverageDetails().observe(this, new Observer<BeverageDetails>() {
            @Override
            public void onChanged(@Nullable BeverageDetails beverageDetails) {
                if (beverageDetails != null) {
                    Log.d(TAG, beverageDetails.toString());
                    updateUI(beverageDetails);
                }
            }
        });

        mIsFavourite = mViewModel.isBeverageFavourite(mBeverage);
        setFavouriteButtonBackground(mIsFavourite);

        return view;
    }

    private void updateUI(BeverageDetails beverageDetails) {
        mName.setText(beverageDetails.getName());
        mGlassType.setText(beverageDetails.getGlassType());
        mInstructions.setText(beverageDetails.getInstructions());
        mIba.setText(beverageDetails.getIBA());
        mCagegory.setText(beverageDetails.getCategory());

        StringBuilder builder = new StringBuilder();
        for (Ingredients ingredients : beverageDetails.getIngredients()) {
            builder.append(ingredients.getIngredient())
                    .append(": ")
                    .append(ingredients.getMeasure())
                    .append("\n");

        }
        if (!builder.toString().isEmpty()) {
            mIngredients.setText(builder.toString());
        }

        Picasso.get().load(beverageDetails.getThumbnailUrl()).into(mThumbnail);
    }

    @OnClick(R.id.add_to_favourite_iv)
    protected void onFavouriteClick() {
        if (mIsFavourite) {
            mIsFavourite = false;
            mViewModel.deleteBeverage(mBeverage);
            Log.d(TAG, "Remove movie from favourites");
        } else {
            mIsFavourite = true;
            mViewModel.saveBeverage(mBeverage);
            Log.d(TAG, "Add movie to favourites");
        }
        setFavouriteButtonBackground(mIsFavourite);
    }

    private void setFavouriteButtonBackground(boolean isFavourite) {
        if (isFavourite) {
            mAddToFavourite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            mAddToFavourite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
