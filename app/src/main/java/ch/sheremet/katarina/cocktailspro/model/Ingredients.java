package ch.sheremet.katarina.cocktailspro.model;

public class Ingredients {
    private String mIngredient;
    private String mMeasure;

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String ingredient) {
        this.mIngredient = ingredient;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public Ingredients(String ingredient, String measure) {
        this.mIngredient = ingredient;
        this.mMeasure = measure;
    }

    //TODO: StringBuilder
    @Override
    public String toString() {
        return "Ingredients{" +
                "mIngredient='" + mIngredient + '\'' +
                ", mMeasure='" + mMeasure + '\'' +
                '}';
    }
}
