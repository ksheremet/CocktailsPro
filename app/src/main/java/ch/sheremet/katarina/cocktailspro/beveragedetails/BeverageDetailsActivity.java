package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class BeverageDetailsActivity extends AppCompatActivity {

    private static final String BEVERAGE_PARAM = "beverage";
    private static final String FRAGMENT_STATE = "beverage_details_state";

    private Beverage mBeverage;
    private BeverageDetailsFragment mDetailsFragment;

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

        if (savedInstanceState != null) {
            mDetailsFragment = (BeverageDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STATE);
        } else {
            mDetailsFragment = BeverageDetailsFragment.newInstance(mBeverage);
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
        Picasso.get()
                .load(mBeverage.getThumbnailUrl())
                .error(R.drawable.vuquyv1468876052)
                .placeholder(R.drawable.vuquyv1468876052)
                .into((ImageView) findViewById(R.id.app_bar_image));
    }
}
