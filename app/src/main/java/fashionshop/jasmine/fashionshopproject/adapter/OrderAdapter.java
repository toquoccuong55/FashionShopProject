package fashionshop.jasmine.fashionshopproject.adapter;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.activity.CartActivity;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.OrderItemManagement;
import fashionshop.jasmine.fashionshopproject.model.Color;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.model.Size;
import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;
import fashionshop.jasmine.fashionshopproject.utils.CurrencyManagement;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>    {
    private ArrayList<OrderItemEntity> mOrderItemList;
    private OrderItemManagement mOderItemManagement;
    private Application mApplication;
    private Context mContext;
    private static final String BUNDLE = "BUNDLE";
    private static final String BUNDLE_POSITION = "BUNDLE_POSITION";
    private static final String BUNDLE_PRODUCT_OBJECT = "PRODUCT_OBJECT";
    private static final String BUNDLE_ORDER_ITEM = "ORDER_ITEM";
    private static final int REQUEST_CODE = 9999;

    public OrderAdapter(ArrayList<OrderItemEntity> mOrderItemList, Context mContext) {
        this.mOrderItemList = mOrderItemList;
        this.mContext = mContext;
    }

    public OrderAdapter(ArrayList<OrderItemEntity> mOrderItemList, Application mApplication, Context mContext) {
        this.mOrderItemList = mOrderItemList;
        this.mApplication = mApplication;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final OrderItemEntity orderItem = mOrderItemList.get(position);
        if (orderItem.getProducts().getProductImagesList().isEmpty()) {
            holder.mImgProducPicURL.setImageResource(R.mipmap.ic_hanger);
        } else {
            Picasso.get()
                    .load(orderItem.getProducts().getProductImagesList().get(0).getPicUrl())
                    .placeholder(R.mipmap.ic_hanger)
                    .error(R.mipmap.ic_hanger)
                    .into(holder.mImgProducPicURL);
        }
        holder.mTxtProductName.setText(orderItem.getProducts().getProductName());

        String sizeName = "";
        for (Size size : orderItem.getProducts().getSizeItem()) {
            if (orderItem.getProducts().getSize() == size.getSizeName()) {
                sizeName = size.getSizeName();
                break;
            }
        }
        String colorName = "";
        for (Color color : orderItem.getProducts().getColorItem()) {
            if (orderItem.getProducts().getColor() == color.getColorName()) {
                colorName = color.getColorName();
                break;
            }
        }
        if (colorName.isEmpty() && sizeName.isEmpty()) {

        } else if(!colorName.isEmpty() && sizeName.isEmpty()){
            holder.mTxtSizeAndColor.setText("Màu " + colorName);
        }else if(colorName.isEmpty() && !sizeName.isEmpty()){
            holder.mTxtSizeAndColor.setText("Size " + sizeName);
        }else{
            holder.mTxtSizeAndColor.setText("Size " + sizeName + " & Màu " + colorName);
        }
        holder.mTxtQuantityAndPrice.setText(orderItem.getProducts().getProductQuantity()
                + " x " + CurrencyManagement.getPrice(orderItem.getProducts().getProductPrice(), "đ"));
        holder.mTxtDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOderItemManagement == null){
                    mOderItemManagement = new OrderItemManagement(mApplication);

                }
                mOderItemManagement.deleteOrderItem(orderItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderItemList == null ? 0 : mOrderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImgProducPicURL;
        public TextView mTxtProductName, mTxtSizeAndColor, mTxtQuantityAndPrice, mTxtDeleteProduct;
        public ViewHolder(View itemView) {

            super(itemView);
            mImgProducPicURL = itemView.findViewById(R.id.image_view_product);
            mTxtProductName = itemView.findViewById(R.id.text_view_product_name);
            mTxtSizeAndColor = itemView.findViewById(R.id.text_view_size_and_color);
            mTxtQuantityAndPrice = itemView.findViewById(R.id.text_view_quantity_and_price);
            mTxtDeleteProduct = itemView.findViewById(R.id.text_view_delete);
        }
    }
}
