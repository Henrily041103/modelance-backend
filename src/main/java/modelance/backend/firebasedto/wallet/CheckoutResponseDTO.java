package modelance.backend.firebasedto.wallet;

public class CheckoutResponseDTO {

    private String bin; // Mã BIN ngân hàng
    private String accountNumber; // Số tài khoản của kênh thanh toán
    private String accountName; // Tên chủ tài khoản của kênh thanh toán
    private double amount; // Số tiền của đơn hàng
    private String description; // Mô tả đơn hàng, được sử dụng làm nội dung chuyển khoản
    private int orderCode; // Mã đơn hàng
    private String currency; // Đơn vị tiền tệ
    private String paymentLinkId; // ID link thanh toán
    private String status; // Trạng thái của link thanh toán
    private String checkoutUrl; // Đường dẫn trang thanh toán
    private String qrCode; // Mã QR thanh toán

    // Getters and setters for each field
    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentLinkId() {
        return paymentLinkId;
    }

    public void setPaymentLinkId(String paymentLinkId) {
        this.paymentLinkId = paymentLinkId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
