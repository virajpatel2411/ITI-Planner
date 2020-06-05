package royal.com.itiplanner.models;

public class FeedbackModel {

  private String feedbackMessage;
  private int ratings;

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
