package modelance.backend.firebasedto.account;

public class ModelBodyIndexDTO {
    private int bust;
    private int height;
    private int hip;
    private int waist;
    private int weight;

    public ModelBodyIndexDTO() {
    }

    public ModelBodyIndexDTO(int bust, int height, int hip, int waist, int weight) {
        this.bust = bust;
        this.height = height;
        this.hip = hip;
        this.waist = waist;
        this.weight = weight;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
