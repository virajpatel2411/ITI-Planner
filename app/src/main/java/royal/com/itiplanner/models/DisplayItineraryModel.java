package royal.com.itiplanner.models;

public class DisplayItineraryModel {

  private FinalModel finalModel;
  private String city;

  public FinalModel getFinalModel() {
    return finalModel;
  }

  public void setFinalModel(FinalModel finalModel) {
    this.finalModel = finalModel;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
