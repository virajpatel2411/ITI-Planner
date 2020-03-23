package royal.com.itiplanner.models;

public class SearchPlace {
  private String placeName;
  private String photoReference;
  private int ratings;

  public String getPlaceName() {
    return placeName;
  }

  public String getPhotoReference() {
    return photoReference;
  }

  public float getRatings() {
    return ratings;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public void setPhotoReference(String photoReference) {
    this.photoReference = photoReference;
  }

  public void setRatings(int ratings) {
    this.ratings = ratings;
  }
}
