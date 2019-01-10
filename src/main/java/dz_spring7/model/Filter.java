package dz_spring7.model;

public class Filter {
    private CategoryType categoryType;
    private String city;
    private String phrase;

    public Filter() {
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategory(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
