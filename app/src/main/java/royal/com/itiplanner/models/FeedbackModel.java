package royal.com.itiplanner.models;

public class FeedbackModel {

  private String feedbackMessage;
  private int ratings;
  private String user_id;
  private String date;

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getFeedbackMessage() {
    return feedbackMessage;
  }

  public void setFeedbackMessage(String feedbackMessage) {
    this.feedbackMessage = feedbackMessage;
  }

  public int getRatings() {
    return ratings;
  }

  public void setRatings(int ratings) {
    this.ratings = ratings;
  }

}
