package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.utils.GlideApp;

public class BeverageDetailsActivity extends AppCompatActivity implements BeverageDetailsFragment.OnDataInteraction {

    private static final String BEVERAGE_PARAM = "beverage";

    private Beverage mBeverage;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.error_message)
    TextView mErrorMessage;
    @BindView(R.id.beverage_details_scrollview)
    NestedScrollView mDataNestedScrollView;

    public static void startActivity(Context context, Beverage beverage) {
        Intent intent = new Intent(context, BeverageDetailsActivity.class);
        intent.putExtra(BEVERAGE_PARAM, beverage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverage_details);
        if (!getIntent().hasExtra(BEVERAGE_PARAM)) {
            finish();
        }

        mBeverage = getIntent().getParcelableExtra(BEVERAGE_PARAM);
        setToolbar();

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            BeverageDetailsFragment mDetailsFragment = BeverageDetailsFragment.newInstance(mBeverage);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.beverage_details_fragment, mDetailsFragment)
                    .commit();
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mBeverage.getName());
        setTitle(mBeverage.getName());
        ImageView appBarIV = findViewById(R.id.app_bar_image);
        GlideApp.with(this)
                .load(mBeverage.getThumbnailUrl())
                .error(R.drawable.def_cocktail_image)
                .placeholder(R.drawable.def_cocktail_image)
                .into(appBarIV);
        appBarIV.setContentDescription(mBeverage.getName());
    }

    @Override
    public void showData() {
        mDataNestedScrollView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        mDataNestedScrollView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        if (!error.isEmpty()) {
            mErrorMessage.setText(error);
        }
    }

    @Override
    public void showProgressBar() {
        mDataNestedScrollView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
