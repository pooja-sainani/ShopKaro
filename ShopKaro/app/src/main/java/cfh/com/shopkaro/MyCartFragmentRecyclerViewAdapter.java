package cfh.com.shopkaro;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import cfh.com.shopkaro.dummy.CartContent;

/**
 * Created by Pooja on 2/27/2016.
 */

public class MyCartFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MyCartFragmentRecyclerViewAdapter.ViewHolder> {

    private final List<CartContent.CartItem> mValues;
    private final MyCartFragment.OnListFragmentInteractionListener mListener;


    public MyCartFragmentRecyclerViewAdapter(List<CartContent.CartItem> items, MyCartFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_one, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mProductName.setText(mValues.get(position).productName.toString());
        Log.e("POOJA", mValues.get(position).price.toString());
        holder.mPrice.setText(mValues.get(position).price.toString());
        holder.mSKprice.setText("15000");
        holder.mAmount.setText(mValues.get(position).Amount.toString());
        holder.qty.setText(Integer.toString(mValues.get(position).Quantity));
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

        holder.deleteFromCart.setTag(R.id.adjust_height,mValues.get(position).id);


        holder.deleteFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootView) {
               new MyCartFragment().removeProductFromCart(holder.deleteFromCart.getTag(R.id.adjust_height).toString());
                Log.e("POOJA","Inside Listeners");

            }
        });



    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mProductName;
        public final TextView mPrice;
        public final TextView mSKprice;
        public final TextView mAmount;
        public ImageButton deleteFromCart;
        public EditText qty;
        public CartContent.CartItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mProductName = (TextView) view.findViewById(R.id.product_name);
            mPrice = (TextView) view.findViewById(R.id.product_mrpvalue);
            mSKprice = (TextView) view.findViewById(R.id.product_skvalue);
            mAmount = (TextView) view.findViewById(R.id.product_value);
            deleteFromCart =(ImageButton)view.findViewById(R.id.delete);
            qty= (EditText) view.findViewById(R.id.qtyValue);
        }


    }
}
