package modelance.backend.firebasedto.wallet;

public class BankTransactionDTO {
    private long orderCode; // Mã đơn hàng từ cửa hàng
    private int amount; // Số tiền thanh toán
    private String description; // Mô tả thanh toán
    private String accountNumber; // Số tài khoản của cửa hàng
    private String reference; // Mã tham chiếu giao dịch, dùng để tra soát với ngân hàng
    private String transactionDateTime; // Ngày giờ giao dịch thực hiện thành công
    private String currency; // Đơn vị tiền tệ
    private String paymentLinkId; // Mã link thanh toán
    private String code; // Mã lỗi của giao dịch
    private String desc; // Thông tin lỗi của giao dịch
    private String counterAccountBankId; // Mã ngân hàng của khách hàng dùng chuyển khoản
    private String counterAccountBankName; // Tên ngân hàng của khách hàng dùng chuyển khoản
    private String counterAccountName; // Tên tài khoản của khách hàng
    private String counterAccountNumber; // Số tài khoản của khách hàng
    private String virtualAccountName; // Tên tài khoản ảo
    private String virtualAccountNumber; // Số tài khoản ảo

    // Getters and Setters
    public long getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(long orderCode) {
        this.orderCode = orderCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
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

    public String getCounterAccountBankId() {
        return counterAccountBankId;
    }

    public void setCounterAccountBankId(String counterAccountBankId) {
        this.counterAccountBankId = counterAccountBankId;
    }

    public String getCounterAccountBankName() {
        return counterAccountBankName;
    }

    public void setCounterAccountBankName(String counterAccountBankName) {
        this.counterAccountBankName = counterAccountBankName;
    }

    public String getCounterAccountName() {
        return counterAccountName;
    }

    public void setCounterAccountName(String counterAccountName) {
        this.counterAccountName = counterAccountName;
    }

    public String getCounterAccountNumber() {
        return counterAccountNumber;
    }

    public void setCounterAccountNumber(String counterAccountNumber) {
        this.counterAccountNumber = counterAccountNumber;
    }

    public String getVirtualAccountName() {
        return virtualAccountName;
    }

    public void setVirtualAccountName(String virtualAccountName) {
        this.virtualAccountName = virtualAccountName;
    }

    public String getVirtualAccountNumber() {
        return virtualAccountNumber;
    }

    public void setVirtualAccountNumber(String virtualAccountNumber) {
        this.virtualAccountNumber = virtualAccountNumber;
    }
}
