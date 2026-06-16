package model;

/**
 * Model representing guest feedback information.
 * Follows the standard getters, setters, and constructors format.
 */
public class FeedbackModel {
    private int id;
    private int serviceRating;
    private int cleanlinessRating;
    private int foodRating;
    private String reviewText;

    // Default Constructor
    public FeedbackModel() {
    }

    // Constructor without id (for insertion)
    public FeedbackModel(int serviceRating, int cleanlinessRating, int foodRating, String reviewText) {
        this.serviceRating = serviceRating;
        this.cleanlinessRating = cleanlinessRating;
        this.foodRating = foodRating;
        this.reviewText = reviewText;
    }

    // Full Constructor
    public FeedbackModel(int id, int serviceRating, int cleanlinessRating, int foodRating, String reviewText) {
        this.id = id;
        this.serviceRating = serviceRating;
        this.cleanlinessRating = cleanlinessRating;
        this.foodRating = foodRating;
        this.reviewText = reviewText;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(int serviceRating) {
        this.serviceRating = serviceRating;
    }

    public int getCleanlinessRating() {
        return cleanlinessRating;
    }

    public void setCleanlinessRating(int cleanlinessRating) {
        this.cleanlinessRating = cleanlinessRating;
    }

    public int getFoodRating() {
        return foodRating;
    }

    public void setFoodRating(int foodRating) {
        this.foodRating = foodRating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
// git