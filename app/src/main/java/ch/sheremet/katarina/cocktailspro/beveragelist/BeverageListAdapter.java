package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment.OnBeverageSelected;
import ch.sheremet.katarina.cocktailspro.databinding.BeverageItemBinding;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class BeverageListAdapter extends RecyclerView.Adapter<BeverageListAdapter.ViewHolder> {

    private final BeverageListFragment.OnBeverageSelected mListener;
    private List<Beverage> mBeverages;

    public BeverageListAdapter(OnBeverageSelected listener) {
        mListener = listener;
    }

    public void setBeverages(List<Beverage> beverages) {
        mBeverages = beverages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BeverageItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.beverage_item,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(mBeverages.get(position));
    }

    @Override
    public int getItemCount() {
        if (mBeverages == null) {
            return 0;
        }
        return mBeverages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final BeverageItemBinding binding;

        ViewHolder(BeverageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Beverage beverage) {
            binding.setBeverage(beverage);
            binding.setEventHandler(mListener);
            Glide.with(binding.getRoot())
                    .load(beverage.getThumbnailUrl())
                    .error(R.drawable.def_cocktail_image)
                    .placeholder(R.drawable.def_cocktail_image)
                    .into(binding.beveragePosterIv);
            //This forces the bindings to run immediately instead of delaying them until the next frame.
            binding.executePendingBindings();
        }

    }
}
