package royal.com.itiplanner.models;

import java.io.Serializable;
import java.util.ArrayList;

public class FinalModel implements Serializable {

    private String daysCount;
    private String ratings;
    private ArrayList<String> tags;
    private ArrayList<PlaceModel> placeModels;

    public String getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(String daysCount) {
        this.daysCount = daysCount;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<PlaceModel> getPlaceModels() {
        return placeModels;
    }

    public void setPlaceModels(ArrayList<PlaceModel> placeModels) {
        this.placeModels = placeModels;
    }
}
