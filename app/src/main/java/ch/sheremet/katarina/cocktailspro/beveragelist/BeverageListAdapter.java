package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment.OnBeverageSelected;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class BeverageListAdapter extends RecyclerView.Adapter<BeverageListAdapter.ViewHolder> {

    private List<Beverage> mBeverages;
    private final BeverageListFragment.OnBeverageSelected mListener;

    public BeverageListAdapter(OnBeverageSelected listener) {
        mListener = listener;
    }

    public void setBeverages(List<Beverage> beverages) {
        mBeverages = beverages;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beverage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBeverage = mBeverages.get(position);
        Picasso.get()
                .load(mBeverages.get(position).getThumbnailUrl())
                .error(R.drawable.def_cocktail_image)
                .placeholder(R.drawable.def_cocktail_image)
                .into(holder.mThumbnail);
        holder.mThumbnail.setContentDescription(mBeverages.get(position).getName());
        holder.mBeverageName.setText(mBeverages.get(position).getName());
        holder.mBeverageView.setContentDescription(mBeverages.get(position).getName());

        holder.mBeverageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onBeverageClicked(holder.mBeverage);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBeverages == null) {
            return 0;
        }
        return mBeverages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mBeverageView;
        @BindView(R.id.beverage_poster_iv)
        ImageView mThumbnail;
        @BindView(R.id.beverage_name_tv)
        TextView mBeverageName;
        Beverage mBeverage;

        ViewHolder(View view) {
            super(view);
            mBeverageView = view;
            ButterKnife.bind(this, view);
        }
    }
}
