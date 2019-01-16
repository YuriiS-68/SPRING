package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.StringJoiner;

public class Filter {
    private CategoryType categoryType;
    private String city;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonCreator
    public static Filter createFromJson(String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.AUTO_DETECT_FIELDS, true);

        Filter filter = null;

        try {
            filter = objectMapper.readValue(jsonString, Filter.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return filter;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Filter.class.getSimpleName() + "[", "]")
                .add("categoryType=" + categoryType)
                .add("city='" + city + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
