package fashionshop.jasmine.fashionshopproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.model.Size;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {
    private ArrayList<Size> mSizeList;
    private Context mContext;
    private OnClickListener mListener;

    public SizeAdapter(ArrayList<Size> mSizeList, Context mContext) {
        this.mSizeList = mSizeList;
        this.mContext = mContext;
    }

    public ArrayList<Size> getmSizeList() {
        return mSizeList;
    }

    public void setmSizeList(ArrayList<Size> mSizeList) {
        this.mSizeList = mSizeList;
    }

    public void setOnItemListner(OnClickListener mItemListner){
        this.mListener = mItemListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_item_size, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Size size = mSizeList.get(position);
        holder.mRdnSize.setText("Size " + size.getSizeName());
        holder.mRdnSize.setChecked(size.isChecked());
        holder.mRdnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Size size : mSizeList) {
                    size.setChecked(false);

                }
                mSizeList.get(position).setChecked(holder.mRdnSize.isChecked());
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSizeList == null ? 0 : mSizeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public RadioButton mRdnSize;
        public ViewHolder(View itemView) {
            super(itemView);
            mRdnSize = itemView.findViewById(R.id.radio_button_size);
        }
    }
    public interface OnClickListener{
        void OnItemClickListener(int position);
    }
}
