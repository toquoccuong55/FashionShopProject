package fashionshop.jasmine.fashionshopproject.model;

import java.io.Serializable;

public class Color implements Serializable{
    private int productId;
    private String colorName;
    private String colorCode;
    private boolean isChecked;

    public Color() {
    }

    public Color(int productId, String colorName, boolean isChecked) {
        this.productId = productId;
        this.colorName = colorName;
        this.isChecked = isChecked;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
