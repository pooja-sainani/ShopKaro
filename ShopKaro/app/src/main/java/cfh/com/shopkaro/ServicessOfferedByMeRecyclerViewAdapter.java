package cfh.com.shopkaro;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cfh.com.shopkaro.dummy.Product;
import cfh.com.shopkaro.dummy.Service;

/**
 * Created by ammura on 08-03-2016.
 */
public class ServicessOfferedByMeRecyclerViewAdapter extends RecyclerView.Adapter<ServicessOfferedByMeRecyclerViewAdapter.ViewHolder> {

    private final List<Service> services;

    public ServicessOfferedByMeRecyclerViewAdapter(List<Service> items) {
        services = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_offeredbyme_serviceitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = services.get(position);
        holder.mName.setText(services.get(position).NAME);
        holder.mRating.setText(String.valueOf(services.get(position).AVGRATING));
        holder.mSold.setText(String.valueOf(services.get(position).NUMBEROFUSERS));
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mRating;
        public final TextView mSold;
        public Service mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = (TextView) view.findViewById(R.id.item_serviceName);
            mRating = (TextView) view.findViewById(R.id.item_serviceRating);
            mSold = (TextView) view.findViewById(R.id.item_serviceSold);
        }
    }
}
