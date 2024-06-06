package modelance.backend.model.work;

public class Location {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((province == null) ? 0 : province.hashCode());
        result = prime * result + ((district == null) ? 0 : district.hashCode());
        result = prime * result + ((ward == null) ? 0 : ward.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        if (province == null) {
            if (other.province != null)
                return false;
        } else if (!province.equals(other.province))
            return false;
        if (district == null) {
            if (other.district != null)
                return false;
        } else if (!district.equals(other.district))
            return false;
        if (ward == null) {
            if (other.ward != null)
                return false;
        } else if (!ward.equals(other.ward))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return address + ", " + ward + ", " + district + ", " + province;
    }

}
