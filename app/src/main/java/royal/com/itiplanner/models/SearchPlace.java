package royal.com.itiplanner.models;

public class SearchPlace {
  private String placeName;
  private String photoReference;
  private double ratings;
  private double latitude,longitude;
  private int numberOfDays,budget;

  public int getNumberOfDays() { return numberOfDays; }

  public void setNumberOfDays(int numberOfDays) { this.numberOfDays = numberOfDays; }

  public void setBudget(int budget) { this.budget = budget; }

  public int getBudget() { return budget; }

  public void setLatitude(double latitude) { this.latitude = latitude; }

  public void setLongitude(double longitude) { this.longitude = longitude; }

  public double getLatitude() { return latitude; }

  public double getLongitude() { return longitude; }

  public String getPlaceName() {
    return placeName;
  }

  public String getPhotoReference() {
    return photoReference;
  }

  public double getRatings() {
    return ratings;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public void setPhotoReference(String photoReference) {
    this.photoReference = photoReference;
  }

  public void setRatings(double ratings) {
    this.ratings = ratings;
  }
}
