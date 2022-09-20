import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
