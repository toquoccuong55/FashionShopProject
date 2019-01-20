package fashionshop.jasmine.fashionshopproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductAndCategory implements Serializable{
    @SerializedName("product_list")
    List<Products> productList;
    @SerializedName("product_category_list")
    List<Category> categoryList;

    public ProductAndCategory() {
    }

    public List<Products> getProductList() {
        return productList;
    }

    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
