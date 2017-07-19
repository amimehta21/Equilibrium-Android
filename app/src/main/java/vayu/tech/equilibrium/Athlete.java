package vayu.tech.equilibrium;

import java.util.Date;

public class Athlete {

    private int athleteId;
    private int userId;
    private Date dob;
    private char gender;
    private int height_ft;
    private int height_in;
    private String height_unit;
    private int weight;
    private String weightUnit;

    public Athlete(int athleteId, int userId, Date dob, char gender, int height_ft, int height_in, String height_unit, int weight, String weightUnit) {
        this.athleteId = athleteId;
        this.userId = userId;
        this.dob = dob;
        this.gender = gender;
        this.height_ft = height_ft;
        this.height_in = height_in;
        this.height_unit = height_unit;
        this.weight = weight;
        this.weightUnit = weightUnit;
    }

    // getters and setters

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getHeight_ft() {
        return height_ft;
    }

    public void setHeight_ft(int height_ft) {
        this.height_ft = height_ft;
    }

    public int getHeight_in() {
        return height_in;
    }

    public void setHeight_in(int height_in) {
        this.height_in = height_in;
    }

    public String getHeight_unit() {
        return height_unit;
    }

    public void setHeight_unit(String height_unit) {
        this.height_unit = height_unit;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "athleteId=" + athleteId +
                ", userId=" + userId +
                ", dob=" + dob +
                ", gender=" + gender +
                ", height_ft=" + height_ft +
                ", height_in=" + height_in +
                ", height_unit='" + height_unit + '\'' +
                ", weight=" + weight +
                ", weightUnit='" + weightUnit + '\'' +
                '}';
    }


    /*

        private var athleteID: Int!
    private var userID: Int!
    private var dob: Date!
    private var gender: Character!
    public enum type_gender: Character {
        case male = "M"
        case female = "F"
    }
    private var height_ft: Int!
    private var height_in: Int!
    private var heightUnit: String! // "cms" or "ft" or ","
    public enum type_height_unit: String {
        case cms = "cms"
        case ft = "ft"
        case comma = ","
    }
    private var weight: Int!
    private var weightUnit: String! // "lb" or "kg"
    public enum unit_weight: String {
        case lb = "lb";
        case kg = "kg";
    }

     */

}
