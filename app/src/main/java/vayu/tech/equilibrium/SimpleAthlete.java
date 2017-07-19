package vayu.tech.equilibrium;

import java.util.Arrays;

public class SimpleAthlete {

    private String name;
    private String userId;
    private Trial[] trials;

    public SimpleAthlete(String name, String userId, Trial[] trials) {
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

    public Trial[] getTrials() {
        return trials;
    }

    public void setTrials(Trial[] trials) {
        this.trials = trials;
    }

    @Override
    public String toString() {
        return "SimpleAthlete{" +
                "name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", trials=" + Arrays.toString(trials) +
                '}';
    }

}
