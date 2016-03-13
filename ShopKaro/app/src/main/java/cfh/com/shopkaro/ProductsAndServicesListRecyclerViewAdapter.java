package cfh.com.shopkaro;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cfh.com.shopkaro.dummy.ProductContent.DummyItem;
import cfh.com.shopkaro.ProductsAndServicesListFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductsAndServicesListRecyclerViewAdapter extends RecyclerView.Adapter<ProductsAndServicesListRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ProductsAndServicesListRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_productsandservices_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);
        holder.mContentView.setText(Double.toString(mValues.get(position).cost));
        holder.mProductDescription.setText(mValues.get(position).tags);
        holder.mPlace.setText(mValues.get(position).place);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.e("POOJA", "inside listener");
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mProductDescription;
        public final TextView mPlace;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_ProductName);
            mContentView = (TextView) view.findViewById(R.id.item_ProductCost);
            mProductDescription = (TextView) view.findViewById(R.id.item_ProductDescription);
            mPlace = (TextView) view.findViewById(R.id.item_ServicePlace);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mProductDescription.getText() + "'";
        }
    }
}
