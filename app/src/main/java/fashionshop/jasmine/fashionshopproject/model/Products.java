package fashionshop.jasmine.fashionshopproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable, Cloneable {
    @SerializedName("product_id")
    private int productId;
    private String productImg;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    private int productQuantity;
    @SerializedName("cat_id")
    private int categoryId;
    @SerializedName("size")
    private String size;
    @SerializedName("color")
    private String color;
    private boolean isExtra;
    private String productSize;
    @SerializedName("product_image_list")
    private List<ProductImage> productImagesList;
    private ArrayList<Color> productColorList;
    private ArrayList<Size> productSizeList;

    public Products() {
    }

    public List<ProductImage> getProductImagesList() {
        return productImagesList;
    }

    public void setProductImagesList(List<ProductImage> productImagesList) {
        this.productImagesList = productImagesList;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public void setExtra(boolean extra) {
        isExtra = extra;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Object clone() throws CloneNotSupportedException {
        Products products = new Products();
        products.setProductId(productId);
        products.setProductImg(productImg);
        products.setProductName(productName);
        products.setProductPrice(productPrice);
        products.setProductQuantity(productQuantity);
        products.setCategoryId(categoryId);
        products.setProductSize(productSize);
        products.setSize(size);
        products.setColor(color);
        products.setExtra(isExtra);
        products.setProductImagesList(productImagesList);
        return products;
    }

    public ArrayList<Color> getColorItem() {
        productColorList = new ArrayList<>();
        String[] colorProducts = getColor().split(",");
        for (int i = 0; i < colorProducts.length; i++) {
            if(i == 0){
                Color color = new Color(productId, colorProducts[i], true);
                productColorList.add(color);
            }else{
                Color color = new Color(productId, colorProducts[i], false);
                productColorList.add(color);;
            }
        }
        return productColorList;
    }
    public ArrayList<Size> getSizeItem() {
        productSizeList = new ArrayList<>();
        String[] sizeProducts = getSize().split(",");
        for (int i = 0; i < sizeProducts.length; i++) {
            if(i == 0){
                Size size = new Size(productId, sizeProducts[i], true);
                productSizeList.add(size);
            }else{
                Size size = new Size(productId, sizeProducts[i], false);
                productSizeList.add(size);
            }
        }
        return productSizeList;
    }
}
