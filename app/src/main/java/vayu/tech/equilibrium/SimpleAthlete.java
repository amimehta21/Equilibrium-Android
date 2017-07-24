package vayu.tech.equilibrium;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleAthlete implements Serializable {

    private String name;
    private String userId;
    private ArrayList<Trial> trials;

    public SimpleAthlete(String name, String userId, ArrayList<Trial> trials) {
        this.name = name;
        this.userId = userId;
        this.trials = trials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Trial> getTrials() {
        return trials;
    }

    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    }

    @Override
    public String toString() {
        return "SimpleAthlete{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", trials=" + trials +
                '}';
    }

}
