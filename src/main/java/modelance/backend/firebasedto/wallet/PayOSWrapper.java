package modelance.backend.firebasedto.wallet;

public class PayOSWrapper<T> {
    private String code;
    private String desc;
    private String signature;
    private T data;

    public PayOSWrapper() {
    }

    public PayOSWrapper(String code, String desc, String signature) {
        this.code = code;
        this.desc = desc;
        this.signature = signature;
    }

    public PayOSWrapper(String code, String desc, String signature, T data) {
        this.code = code;
        this.desc = desc;
        this.signature = signature;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
