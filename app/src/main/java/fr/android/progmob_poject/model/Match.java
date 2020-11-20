package fr.android.progmob_poject.model;

import java.time.LocalDate;

public class Match {

    private int id;
    private String team_a;
    private String team_b;
    private String address;
    private String coordinates;
    private LocalDate date_match;
    private int score_team_a;
    private  int score_team_b;



    public Match(int id, String team_a, String team_b, String address, String coordinates, LocalDate date_match, int score_team_a, int score_team_b){
        this.id = id;
        this.team_a = team_a;
        this.team_b = team_b;
        this.address = address;
        this.coordinates = coordinates;
        this.date_match = date_match;
        this.score_team_a = score_team_a;
        this.score_team_b = score_team_b;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam_a() {
        return team_a;
    }

    public void setTeam_a(String team_a) {
        this.team_a = team_a;
    }

    public String getTeam_b() {
        return team_b;
    }

    public void setTeam_b(String team_b) {
        this.team_b = team_b;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getDate_match() {
        return date_match;
    }

    public void setDate_match(LocalDate date_match) {
        this.date_match = date_match;
    }

    public int getScore_team_b() {
        return score_team_b;
    }

    public void setScore_team_b(int score_team_b) {
        this.score_team_b = score_team_b;
    }

    public int getScore_team_a() {
        return score_team_a;
    }

    public void setScore_team_a(int score_team_a) {
        this.score_team_a = score_team_a;
    }
}
