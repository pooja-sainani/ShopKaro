package cfh.com.shopkaro;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import cfh.com.shopkaro.dummy.Product;

/**
 * Created by ammura on 08-03-2016.
 */
public class ProductsOfferedByMeRecyclerViewAdapter extends RecyclerView.Adapter<ProductsOfferedByMeRecyclerViewAdapter.ViewHolder> {

    private final List<Product> products;

    public ProductsOfferedByMeRecyclerViewAdapter(List<Product> items) {
        products = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_offeredbyme_products_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = products.get(position);
        holder.mName.setText(products.get(position).NAME);
        holder.mRating.setText(String.valueOf(products.get(position).AVGRATING));
        holder.mAvailable.setText(String.valueOf(products.get(position).QUANTITYAVAILABLE));
        holder.mSold.setText(String.valueOf(products.get(position).QUANTITYSOLD));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mRating;
        public final TextView mAvailable;
        public final TextView mSold;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.item_ProductName);
            mRating = (TextView) view.findViewById(R.id.item_productRating);
            mAvailable = (TextView) view.findViewById(R.id.item_productAvailable);
            mSold = (TextView) view.findViewById(R.id.item_productSold);
        }
    }
}
