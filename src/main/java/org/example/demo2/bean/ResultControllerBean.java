package org.example.demo2.bean;

import org.example.demo2.entity.ResultEntity;
import org.example.demo2.service.ResultService;
import org.example.demo2.util.HitChecker;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

public class ResultControllerBean implements Serializable {

    private double x;           // [-3;5]
    private double y = -4;       // [-4;4]
    private double r = 1.0;     // [1;4], шаг 0.5

    private int currentR;

    private List<ResultEntity> results;

    @EJB
    private ResultService resultService;

    private String sessionId;

    @PostConstruct
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        sessionId = ctx.getExternalContext().getSessionId(true);
        results = resultService.findBySession(sessionId);

        this.currentR = (int) Math.round((r - 1.0) / 0.5) + 1;
    }

    public void submit() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "submit() вызван", null));
        addPoint(x, y, r);
    }



    public void clear() {
        resultService.deleteBySession(sessionId);
        results.clear();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "История очищена", null));
    }

    private void addPoint(double x, double y, double r) {
        if (!validate(x, y, r)) return;

        long t0 = System.nanoTime();
        boolean hit = HitChecker.hit(x, y, r);
        long execMs = Math.max(1, Math.round((System.nanoTime() - t0) / 1_000_000.0));

        ResultEntity e = new ResultEntity();
        e.setX(x);
        e.setY(y);
        e.setR(r);
        e.setHit(hit);
        e.setExecMs(execMs);
        e.setSessionId(sessionId);

        resultService.save(e);
        results.add(0, e);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        hit ? "Точка попала в область" : "Точка вне области", null));
    }

    private boolean validate(double x, double y, double r) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        boolean ok = true;

        if (x < -3 || x > 5) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "X должен быть в диапазоне [-3; 5]", null));
            ok = false;
        }
        if (y < -4 || y > 4) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Y должен быть в диапазоне [-4; 4]", null));
            ok = false;
        }
        if (r < 1 || r > 4 || Math.abs(r * 2 - Math.round(r * 2)) > 1e-9) {
            ctx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "R должен быть от 1 до 4 с шагом 0.5", null));
            ok = false;
        }
        return ok;
    }

    public double getX() { return x; }
    public void setX(double x) {
        if (Math.abs(x) < 1e-9) {
            x = 0.0;
        }
        this.x = x;
    }


    public double getY() { return y; }
    public void setY(double y) {
        long rounded = Math.round(y);
        if (rounded < -4) rounded = -4;
        if (rounded > 4)  rounded = 4;
        this.y = rounded;
    }

    public double getR() { return r; }


    public void setR(double r) {
        double rounded = Math.round(r * 2.0) / 2.0;
        if (rounded < 1.0) rounded = 1.0;
        if (rounded > 4.0) rounded = 4.0;
        this.r = rounded;
        this.currentR = (int) Math.round((this.r - 1.0) / 0.5) + 1;
    }

    public int getCurrentR() {
        return currentR;
    }

    public void setCurrentR(int currentR) {
        this.currentR = currentR;
        this.r = 1.0 + (currentR - 1) * 0.5;
    }

    public List<ResultEntity> getResults() { return results; }
}
