package cfh.com.shopkaro;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cfh.com.shopkaro.MyOrderServicesFragment.OnListFragmentInteractionListener;
import cfh.com.shopkaro.dummy.OrderServiceContent;
import cfh.com.shopkaro.dummy.OrderServiceContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyOrderServicesRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderServicesRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyOrderServicesRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_myorder_services_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mService.setText(mValues.get(position).service);
        holder.mDetails.setText(mValues.get(position).details);
        holder.mDescription.setText(mValues.get(position).description);
        holder.mLocation.setText(mValues.get(position).location);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final TextView mService;
        public final TextView mDescription;
        public final TextView mDetails;
        public final TextView mLocation;
        public OrderServiceContent.DummyItem mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mService = (TextView) view.findViewById(R.id.item_Servicename);
            mDescription = (TextView) view.findViewById(R.id.item_ServiceDescription);
            mDetails = (TextView) view.findViewById(R.id.item_SellerDetails);
            mLocation = (TextView) view.findViewById(R.id.item_Location);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mService.getText() + "'";
        }
    }
}
