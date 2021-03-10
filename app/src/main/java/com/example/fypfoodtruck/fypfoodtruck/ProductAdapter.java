package com.example.fypfoodtruck.fypfoodtruck;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import static com.example.fypfoodtruck.fypfoodtruck.MenuDetailActivity.arrayList;




public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public static ArrayList<Item> productsArray;
    public static ArrayList<ProductImage> cartModels = new ArrayList<>();
    public static ProductImage cartModel;
    Context context;
    private CallBackUs mCallBackus;
    private HomeCallBack homeCallBack;

    public ProductAdapter(ArrayList<Item> productsArray, Context context, HomeCallBack mCallBackus) {
        this.productsArray = productsArray;
        this.context = context;
        this.homeCallBack = mCallBackus;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_adapter_layout, viewGroup, false);
        return new ProductAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder viewHolder, final int i) {
        /*viewHolder.productImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bag));*/
        viewHolder.productName.setText(productsArray.get(i).getProduct());
        /*viewHolder.productImage.setImageDrawable(ContextCompat.getDrawable(context, productsArray.get(i).imagePath));*/

        viewHolder.productImage.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(context);
            // Include dialog.xml file
            dialog.setContentView(R.layout.dialog_item_quantity_update);
            // Set dialog title
            dialog.setTitle("Custom Dialog");
            final ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
            ImageView cartIncrement = dialog.findViewById(R.id.cart_increment);
            ImageView closeDialog = dialog.findViewById(R.id.imageView_close_dialog_cart);
            TextView updateQtyDialog = dialog.findViewById(R.id.update_quantity_dialog);
            TextView viewCartDialog = dialog.findViewById(R.id.view_cart_button_dialog);
            final TextView quantity = dialog.findViewById(R.id.cart_product_quantity_tv);
            quantity.setText(String.valueOf(0));
            final int[] cartCounter = {0};//{(arrayListImage.get(position).getStocks())};
            cartDecrement.setEnabled(false);
            cartDecrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cartCounter[0] == 1) {
                        Toast.makeText(context, "cant add less than 0", Toast.LENGTH_SHORT).show();
                    } else {
                        cartCounter[0] -= 1;
                        quantity.setText(String.valueOf(cartCounter[0]));
                    }

                }
            });
            cartIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartDecrement.setEnabled(true);
                    cartCounter[0] += 1;
                    quantity.setText(String.valueOf(cartCounter[0]));


                }
            });
            viewCartDialog.setOnClickListener(v1 -> context.startActivity(new Intent(context, CartActivity.class)));

            dialog.show();
            updateQtyDialog.setOnClickListener(v12 -> {
                Toast.makeText(context, String.valueOf(cartCounter[0]) + "", Toast.LENGTH_SHORT).show();

                // from these line of code we add items in cart
                cartModel = new ProductImage();
                cartModel.setProductQuantity((cartCounter[0]));
                cartModel.setProductPrice(arrayList.get(i).getPrice());
                cartModel.setTotalCash(cartCounter[0] *
                        Integer.parseInt(arrayList.get(i).getPrice()));
                Log.d("pos", String.valueOf(i));

                cartModels.add(cartModel);

//
                // from these lines of code we update badge count value
                MenuDetailActivity.cart_count = 0;
                for (int i1 = 0; i1 < cartModels.size(); i1++) {
                    for (int j = i1 + 1; j < cartModels.size(); j++) {
                        if (cartModels.get(i1).getProductImage().equals(cartModels.get(j).getProductImage())) {
                            cartModels.get(i1).setProductQuantity(cartModels.get(j).getProductQuantity());
                            cartModels.get(i1).setTotalCash(cartModels.get(j).getTotalCash());
                            //          cartModels.get(i).setImageIdSlide(cartModels.get(j).getImageIdSlide());
                            cartModels.remove(j);
                            j--;
                            Log.d("remove", String.valueOf(cartModels.size()));

                        }
                    }
                }
                MenuDetailActivity.cart_count = cartModels.size();

                // from this interface method calling we show the updated value of cart cout in badge
                homeCallBack.updateCartCount(context);
                dialog.dismiss();
            });

            closeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuDetailActivity.cart_count = 0;
                    for (int i1 = 0; i1 < cartModels.size(); i1++) {
                        for (int j = i1 + 1; j < cartModels.size(); j++) {
                            if (cartModels.get(i1).getProductImage().equals(cartModels.get(j).getProductImage())) {
                                cartModels.get(i1).setProductQuantity(cartModels.get(j).getProductQuantity());
                                cartModels.get(i1).setTotalCash(cartModels.get(j).getTotalCash());
                                //          cartModels.get(i).setImageIdSlide(cartModels.get(j).getImageIdSlide());
                                cartModels.remove(j);
                                j--;
                                Log.d("remove", String.valueOf(cartModels.size()));

                            }
                        }
                    }


                    MenuDetailActivity.cart_count = cartModels.size();
                    homeCallBack.updateCartCount(context);
                    dialog.dismiss();
                }
            });


        });


    }

    @Override
    public int getItemCount() {
        return productsArray.size();
    }

    public interface CallBackUs {
        void addCartItemView();
    }

    // this interface creates for call the invalidateoptionmenu() for refresh the menu item
    public interface HomeCallBack {
        void updateCartCount(Context context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;

        ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.android_gridview_image);
            productName = itemView.findViewById(R.id.android_gridview_text);
        }
    }
}
