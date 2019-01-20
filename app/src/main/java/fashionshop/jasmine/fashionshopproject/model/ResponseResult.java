package fashionshop.jasmine.fashionshopproject.model;

public class ResponseResult<T> {
    private Status status;
    private DataResponse<T> data;

    public ResponseResult() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DataResponse<T> getData() {
        return data;
    }

    public void setData(DataResponse<T> data) {
        this.data = data;
    }
}
