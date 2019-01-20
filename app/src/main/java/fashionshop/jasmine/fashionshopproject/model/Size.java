package fashionshop.jasmine.fashionshopproject.model;

import java.io.Serializable;

public class Size implements Serializable{
    private int productId;
    private String sizeName;
    private boolean isChecked;

    public Size(int productId, String sizeName, boolean isChecked) {
        this.productId = productId;
        this.sizeName = sizeName;
        this.isChecked = isChecked;
    }

    public Size() {
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
