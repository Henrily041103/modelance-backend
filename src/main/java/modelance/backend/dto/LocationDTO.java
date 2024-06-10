package modelance.backend.dto;

public class LocationDTO {
    private String andress;
    private String district;
    private String province;
    private String ward;

    public LocationDTO(String andress, String district, String province, String ward) {
        this.andress = andress;
        this.district = district;
        this.province = province;
        this.ward = ward;
    }

    public LocationDTO() {
    }

    public String getAndress() {
        return andress;
    }

    public void setAndress(String andress) {
        this.andress = andress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

}
