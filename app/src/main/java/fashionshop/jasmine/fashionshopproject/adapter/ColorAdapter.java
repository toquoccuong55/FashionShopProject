package fashionshop.jasmine.fashionshopproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.model.Color;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ArrayList<Color> mColorList;
    private Context mContext;
    private OnClickListener mListener;

    public ColorAdapter(ArrayList<Color> mColorList, Context mContext) {
        this.mColorList = mColorList;
        this.mContext = mContext;
    }

    public ArrayList<Color> getmColorList() {
        return mColorList;
    }

    public void setmColorList(ArrayList<Color> mColorList) {
        this.mColorList = mColorList;
    }

    public void setOnItemListner(OnClickListener mItemListner){
        this.mListener = mItemListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_item_color, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Color color = mColorList.get(position);
        holder.mRdnColor.setText(color.getColorName());
        holder.mRdnColor.setChecked(color.isChecked());
        holder.mRdnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Color size : mColorList) {
                    size.setChecked(false);
                }
                mColorList.get(position).setChecked(holder.mRdnColor.isChecked());
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColorList == null ? 0 : mColorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public RadioButton mRdnColor;
        public ViewHolder(View itemView) {
            super(itemView);
            mRdnColor = itemView.findViewById(R.id.radio_button_color);
        }
    }
    public interface OnClickListener{
        void OnItemClickListener(int position);
    }
}
