import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Udlån {
    static void udlånBog() {
        String sql = "INSERT INTO udlåntabel (Bogid, Lånerid) VALUES (?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setInt(1, Input.getInt("Skriv Bog ID"));
            ps.setInt(2, Input.getInt("Skriv Låner ID"));


            ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            System.out.println("Vi er nået til række : " +id);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void byOversigt(){
        List<String> byListe = new ArrayList<>();

        String sql = "select * from oversigt where ByNavn = ?";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement

            ps.setString(1, Input.getString("Skriv et bynavn"));

            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                String Navn = resultSet.getString("Navn");
                String ByNavn = resultSet.getString("ByNavn");
                String Title = resultSet.getString("Title");


                byListe.add(Navn + " : " + ByNavn + " : " + Title);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : byListe) {
            System.out.println(s);
        }
    }
    public static void navnOversigt(){
        List<String> byListe = new ArrayList<>();

        String sql = "select * from oversigt where Navn = ?";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement

            ps.setString(1, Input.getString("Skriv et udlåner navn"));

            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                String Navn = resultSet.getString("Navn");
                String ByNavn = resultSet.getString("ByNavn");
                String Title = resultSet.getString("Title");


                byListe.add(Navn + " : " + ByNavn + " : " + Title);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : byListe) {
            System.out.println(s);
        }
    }
    }
