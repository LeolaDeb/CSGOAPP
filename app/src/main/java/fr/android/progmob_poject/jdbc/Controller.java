package fr.android.progmob_poject.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import fr.android.progmob_poject.model.*;
import java.time.LocalDate;

public class Controller {
    public static List<Match> getListMatches() {
        return listMatches;
    }

    private static List<Match> listMatches;

    public Controller() {

    }

    public static List<Match> getAllMatches() throws Exception {
        String query = "SELECT * FROM matches";
        List<Match> matches = new ArrayList<Match>();
        try {
            Statement s = DatabaseConnection.getInstance().createStatement();

            ResultSet resultSet = s.executeQuery(query);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String team_a = resultSet.getString("team_a");
                    String team_b = resultSet.getString("team_b");
                    String address = resultSet.getString("address");
                    String coordinates = resultSet.getString("coordinates");
                    LocalDate date_match = (LocalDate) LocalDate.parse(resultSet.getDate("date_match").toString());
                    int score_team_a = resultSet.getInt("score_team_a");
                    int score_team_b = resultSet.getInt("score_team_b");
                    Match match = new Match(id, team_a, team_b, address, coordinates, date_match, score_team_a, score_team_b);
                    matches.add(match);

                }


                resultSet.close();

        } catch(SQLException sqle){
                // TODO Auto-generated catch block
                sqle.printStackTrace();
                //throw sqle;
        } catch(Exception e){
            throw e;
        }
        listMatches = matches;
            return matches;


    }

    public static Match getMatch(String teamA, String teamB, String date) throws Exception {
        String query = "SELECT * FROM matches Where team_a = ? AND team_b = ? AND date_match = ?";
        Match match = null;
        try {
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(query);
            ps.setString(1, teamA);
            ps.setString(2, teamB);
            ps.setDate(3, java.sql.Date.valueOf(date));
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String team_a = resultSet.getString("team_a");
                String team_b = resultSet.getString("team_b");
                String address = resultSet.getString("address");
                String coordinates = resultSet.getString("coordinates");
                LocalDate date_match = (LocalDate) LocalDate.parse(resultSet.getDate("date_match").toString());
                int score_team_a = resultSet.getInt("score_team_a");
                int score_team_b = resultSet.getInt("score_team_b");
                match = new Match(id, team_a, team_b, address, coordinates, date_match, score_team_a, score_team_b);
                

            }


            resultSet.close();
            return match;
        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public static int addMatch(Match match) throws Exception{
        String query = "INSERT INTO Matches (team_a, team_b, address, coordinates, date_match, score_team_a, score_team_b) values (?,?,?,?,?,?,?)";
        int id = 0;
        try{
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, match.getTeam_a());
            ps.setString(2, match.getTeam_b());
            ps.setString(3, match.getAddress());
            ps.setString(4, match.getCoordinates());
            java.sql.Date sqlDate = java.sql.Date.valueOf(match.getDate_match().toString()/*format(DateTimeFormatter.ofPattern("yyy-[m]m-[d]d")*/);
            ps.setDate(5, sqlDate);
            ps.setInt(6, match.getScore_team_a());
            ps.setInt(7, match.getScore_team_b());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating match failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = (int) generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating match failed, no ID obtained.");
                }
            }

        }
        catch(Exception e){
            throw e;
        }
        return id;
    }
}