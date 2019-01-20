package fashionshop.jasmine.fashionshopproject.model;

import java.io.Serializable;
import java.util.List;

public class CategoryItem implements Serializable {
    private Category category;
    private List<Products> productList;

    public CategoryItem() {
    }

    public CategoryItem(Category category, List<Products> productList) {
        this.category = category;
        this.productList = productList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Products> getProductList() {
        return productList;
    }

    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }
}
