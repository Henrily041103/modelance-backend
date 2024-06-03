package modelance.backend.model.work;

public class Location {
    private String id;
    private String province;
    private String district;
    private String ward;
    private String address;

    public Location(String province, String district, String ward, String address) {
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId() {
        if (id.isEmpty()) {
            id = province + district + ward + address;
        }
    }

}
