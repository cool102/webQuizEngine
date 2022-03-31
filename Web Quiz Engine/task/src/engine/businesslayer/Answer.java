package engine.businesslayer;

public class Answer {

    public final static Answer CORRECT_ANSWER = new Answer(true, "Congratulations, you're right!");
    public final static Answer WRONG_ANSWER = new Answer(false, "Wrong answer! Please, try again.");
    boolean success;
    String feedback;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
