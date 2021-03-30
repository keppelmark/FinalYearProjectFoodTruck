package com.example.fypfoodtruck.fypfoodtruck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.fypfoodtruck.fypfoodtruck.CartActivity.grandTotal;
import static com.example.fypfoodtruck.fypfoodtruck.CartActivity.grandTotalplus;
import static com.example.fypfoodtruck.fypfoodtruck.CartActivity.temparraylist;





public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<ProductImage> cartModelArrayList;
    Context context;

    public CartAdapter(ArrayList<ProductImage> cartModelArrayList, Context context) {
        this.context = (Context) context;
        this.cartModelArrayList = cartModelArrayList;
    }


    @NotNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new CartAdapter.ViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {
        // holder.productCartImage.setImageResource(R.drawable.burger);
        holder.productCartPrice.setText(String.valueOf(cartModelArrayList.get(position).getTotalCash()));
        holder.productCartCode.setText(cartModelArrayList.get(position).getProductName());
        holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.food3);
        requestOptions.error(R.drawable.food3);
        Log.d("imageurl", String.valueOf(cartModelArrayList.get(position).getProductImage()));
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cartModelArrayList.get(position).getProductImage()).into(holder.productCartImage);

        //for remove single item in cart and update the total value and list
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cartModelArrayList.size() == 1) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());
                    grandTotalplus = 0;
                    grandTotal.setText(String.valueOf(grandTotalplus));
                }

                if (cartModelArrayList.size() > 0) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());
                    grandTotalplus = 0;
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
                    }

                    Log.d("totalcashthegun", String.valueOf(grandTotalplus));
                    grandTotal.setText(String.valueOf(grandTotalplus));

                } else {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        holder.cartIncrement.setOnClickListener(v -> {
            //total_cash=0;\
            Log.d("posthegun", String.valueOf(cartModelArrayList.get(position).getStocks()));

            grandTotalplus = 0;
            holder.cartDecrement.setEnabled(true);

            int cartUpdateCounter = (cartModelArrayList.get(position).getProductQuantity());
            Log.d("counterthegun", String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

            holder.cartIncrement.setEnabled(true);
            cartUpdateCounter += 1;

            cartModelArrayList.get(position).setProductQuantity((cartUpdateCounter));
            int cash = (Integer.parseInt(cartModelArrayList.get(position).getProductPrice()) * (cartModelArrayList.get(position).getProductQuantity()));

            holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

            cartModelArrayList.get(position).setTotalCash(cash);
            holder.productCartPrice.setText(String.valueOf(cash));


            for (int i = 0; i < temparraylist.size(); i++) {
                grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
            }
            Log.d("totalcashthegun", String.valueOf(grandTotalplus));
            grandTotal.setText(String.valueOf(grandTotalplus));

        });

        //
        holder.cartDecrement.setOnClickListener(v -> {
            //total_cash=0;
            grandTotalplus = 0;
            holder.cartIncrement.setEnabled(true);

            int cartUpdateCounter = (cartModelArrayList.get(position).getProductQuantity());
            Log.d("counterthegun", String.valueOf(cartModelArrayList.get(position).getProductQuantity()));


            if (cartUpdateCounter == 1) {
                holder.cartDecrement.setEnabled(false);
                Toast.makeText(context, "quantity can't be zero", Toast.LENGTH_SHORT).show();
            } else {
                holder.cartDecrement.setEnabled(true);
                cartUpdateCounter -= 1;
                cartModelArrayList.get(position).setProductQuantity((cartUpdateCounter));
                holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));
                int cash = (Integer.parseInt(cartModelArrayList.get(position).getProductPrice()) * (cartModelArrayList.get(position).getProductQuantity()));

                cartModelArrayList.get(position).setTotalCash(cash);
                holder.productCartPrice.setText(String.valueOf(cash));
                for (int i = 0; i < temparraylist.size(); i++) {
                    grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCash();
                }

                Log.d("totalcashthegun", String.valueOf(grandTotalplus));
                grandTotal.setText(String.valueOf(grandTotalplus));

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("sizecart", String.valueOf(cartModelArrayList.size()));
        return cartModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productCartImage, cartIncrement, cartDecrement, deleteItem;
        TextView productCartCode, productCartPrice, productCartQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            productCartImage = itemView.findViewById(R.id.list_image_cart);
            deleteItem = itemView.findViewById(R.id.delete_item_from_cart);
            productCartCode = itemView.findViewById(R.id.product_cart_code);
            productCartPrice = itemView.findViewById(R.id.product_cart_price);
            productCartQuantity = itemView.findViewById(R.id.cart_product_quantity_tv);
            cartDecrement = itemView.findViewById(R.id.cart_decrement);
            cartIncrement = itemView.findViewById(R.id.cart_increment);


        }
    }


}