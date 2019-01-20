package fashionshop.jasmine.fashionshopproject.model;

public class DataResponse<T> {
    private T data;

    public DataResponse(){

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
