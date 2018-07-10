package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment.OnBeverageSelected;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

import java.util.List;

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
                .inflate(R.layout.fragment_beverage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mBeverages.get(position);
        holder.mIdView.setText(mBeverages.get(position).getThumbnailUrl());
        holder.mContentView.setText(mBeverages.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onBeverageClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mBeverages == null) return 0;
        return mBeverages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Beverage mItem;

        // TODO: Use butterknife
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
