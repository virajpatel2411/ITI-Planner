package royal.com.itiplanner.models;

import java.util.ArrayList;

public class HomePageItineraryModel {

  String amt;
  ArrayList<String> place;
  String city;
  String no_of_days;
  String no_of_people;
  String state;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getAmt() {
    return this.amt;
  }

  public void setAmt(final String amt) {
    this.amt = amt;
  }

  public ArrayList<String> getPlace() {
    return place;
  }

  public void setPlace(ArrayList<String> place) {
    this.place = place;
  }

  public String getNo_of_days() {
    return this.no_of_days;
  }

  public void setNo_of_days(final String no_of_days) {
    this.no_of_days = no_of_days;
  }

  public String getNo_of_people() {
    return this.no_of_people;
  }

  public void setNo_of_people(final String no_of_people) {
    this.no_of_people = no_of_people;
  }
}
