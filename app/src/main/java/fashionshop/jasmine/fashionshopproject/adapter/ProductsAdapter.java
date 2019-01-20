package fashionshop.jasmine.fashionshopproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.utils.CurrencyManagement;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Products> mProducts;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public ProductsAdapter(List<Products> mProducts, Context mContext) {
        this.mProducts = mProducts;
        this.mContext = mContext;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_product_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Products products = mProducts.get(position);

        if (products.getProductImagesList() == null || products.getProductImagesList().isEmpty() ) {
            holder.mImgProduct.setImageResource(R.mipmap.ic_hanger);
        } else {
            Picasso.get()
                    .load(products.getProductImagesList().get(0).getPicUrl())
                    .placeholder(R.mipmap.ic_hanger)
                    .error(R.mipmap.ic_hanger)
                    .into(holder.mImgProduct);
        }
        holder.mTxtTitle.setText(products.getProductName());
        double unitPrice = products.getProductPrice();
        holder.mTxtPrice.setText(CurrencyManagement.getPrice(unitPrice, " đ"));
        holder.mCardViewDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position);
                }
            }
        });
    }
    public void notifyDataSetChangeCustom(ArrayList<Products> listProduct){
        mProducts = new ArrayList<>(listProduct);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImgProduct;
        public TextView mTxtTitle, mTxtPrice;
        public CardView mCardViewDrink;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgProduct = itemView.findViewById(R.id.image_view_product);
            mTxtTitle = itemView.findViewById(R.id.text_view_title);
            mTxtPrice = itemView.findViewById(R.id.text_view_price);
            mCardViewDrink = itemView.findViewById(R.id.card_view_product);
        }
    }
}
