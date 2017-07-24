package vayu.tech.equilibrium;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SimpleCoach implements Serializable {

    private ArrayList<HashMap<String, String>> athletes;
    private User user;

    public SimpleCoach(ArrayList<HashMap<String, String>> athletes, User user) {
        if (athletes == null) {
            this.athletes = new ArrayList<HashMap<String, String>>();
        } else {
            this.athletes = athletes;
        }
        this.user = user;
    }

    public ArrayList<HashMap<String, String>> getAthletes() {
        return athletes;
    }

    public void setAthletes(ArrayList<HashMap<String, String>> athletes) {
        this.athletes = athletes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
