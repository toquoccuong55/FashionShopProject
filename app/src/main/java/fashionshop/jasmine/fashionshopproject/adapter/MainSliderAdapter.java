package fashionshop.jasmine.fashionshopproject.adapter;

import java.util.List;

import fashionshop.jasmine.fashionshopproject.model.ProductImage;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    List<ProductImage> productImagesList;

    @Override
    public int getItemCount() {
        return productImagesList.size();
    }

    public MainSliderAdapter(List<ProductImage> productImagesList) {
        this.productImagesList = productImagesList;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {

        imageSlideViewHolder.bindImageSlide(productImagesList.get(position).getPicUrl());

    }
}
