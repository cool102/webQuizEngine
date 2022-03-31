package engine.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "completedquizess")

public class CompletedQuizzes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long completedQuizzesId;

    @JsonIgnore
    private String userId;


    private Long id;
    private LocalDateTime completedAt;

    public CompletedQuizzes() {
    }

    public void setCompletedQuizzesId(Long completedQuizzesId) {
        this.completedQuizzesId = completedQuizzesId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getCompletedQuizzesId() {
        return completedQuizzesId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public CompletedQuizzes(Long completedQuizzesId, String userId, Long id, LocalDateTime completedAt) {
        this.completedQuizzesId = completedQuizzesId;
        this.userId = userId;
        this.id = id;
        this.completedAt = completedAt;
    }
}
