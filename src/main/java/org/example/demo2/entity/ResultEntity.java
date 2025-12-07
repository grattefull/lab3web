package org.example.demo2.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "results")
public class ResultEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // бд  генерит знач. id при инсерте строки через автоинкрем.
    private Long id;

    private double x;
    private double y;
    private double r;

    private boolean hit;

    @Column(name = "exec_ms")
    private long execMs;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "session_id", length = 64)
    private String sessionId;



    public Long getId() {
        return id;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public long getExecMs() {
        return execMs;
    }
    public void setExecMs(long execMs) {
        this.execMs = execMs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
