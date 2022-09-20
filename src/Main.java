import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        menu();

    }

    private static void menu() {
        String svar;

        svar = getString("Hvad ønsker du? \n"+
                "Opret Låner (1) / Udskriv Låner (2) / Slet Låner (3) \n" +
                "Opret By (4) / Udskriv Byer (5)\n" +
                "Opret Bog (6) / Udskriv bøger (7) / Slet bog (8)");

        switch (svar){
            case "1":
                OpretLåner();
                break;
            case "2":
                udskrivLånere();
                break;
            case "3":
                SletLåner(); //kan ikke slette hvis der er udlånt bog til dem
                break;
            case "4":
                OpretBy(); //virker ikke
                break;
            case "5":
                UdskrivByer();
                break;
            case "6":
                OpretBog();
                break;
            case "7":
                udskrivBøger();
                break;
            case "8":
                SletBog(); //kan ikke slette bog der er udlånt
                break;
            default:
                System.out.println("ugyldigt valg, prøv igen");
                break;
        }
        menu();
    }

    private static void UdskrivByer() {
        List<String> byListe = new ArrayList<>();

        String sql = "select * from postnrtabel ";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                int id = resultSet.getInt("Postnrid");
                String PostNr = resultSet.getString("PostNr");
                String ByNavn = resultSet.getString("ByNavn");

                byListe.add(id + " : " + PostNr + " : " + ByNavn);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : byListe) {
            System.out.println(s);
        }
    }

    private static void udskrivBøger() {
        List<String> bogListe = new ArrayList<>();

        String sql = "select * from bogtabel ";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                int id = resultSet.getInt("bogid");
                String navn = resultSet.getString("Forfatter");
                String adresse = resultSet.getString("Title");
                String postNr = resultSet.getString("UdgivelsesÅr");

                bogListe.add(id + " : " + navn + " : " + adresse + " : " + postNr);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : bogListe) {
            System.out.println(s);
        }
    }

    private static void SletBog() {
        udskrivBøger();

        String sql = "delete from bogtabel where Title = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            String bogTitle = getString("Skriv titlen på den bog der skal slettes");
            ps.setString(1, bogTitle );


            int res = ps.executeUpdate();       //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            if (res > 0) {

                System.out.println("Kunden med navnet " + "\"" + bogTitle + "\"" + " er nu blevet slettet");
            }else{
                System.out.println("en kunde med navnet " + "\"" + bogTitle + "\"" + " fandtes ikke i listen");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        udskrivBøger();
    }

    private static void OpretBog() {
        String sql = "INSERT INTO bogtabel (Forfatter, Title, UdgivelsesÅr ) VALUES (?, ?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setString(1, getString("Skriv forfatterens navn"));
            ps.setString(2, getString("Skriv bogens title"));
            ps.setString(3, getString("Skriv skriv bogens udgivelsesår"));


            ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            System.out.println("Vi er nået til række : " +id);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void SletLåner() {
        udskrivLånere();

        String sql = "delete from lånertabel where Navn = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            String lånerNavn = getString("Skriv navnet på låner der skal slettes");
            ps.setString(1, lånerNavn );


            int res = ps.executeUpdate();       //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            if (res > 0) {

                System.out.println("Kunden med navnet " + "\"" + lånerNavn + "\"" + " er nu blevet slettet");
            }else{
                System.out.println("en kunde med navnet " + "\"" + lånerNavn + "\"" + " fandtes ikke i listen");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        udskrivLånere();
    }

    private static void udskrivLånere() {
        List<String> lånerListe = new ArrayList<>();

        String sql = "select * from lånertabel ";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                int id = resultSet.getInt("lånerid");
                String navn = resultSet.getString("Navn");
                String adresse = resultSet.getString("Adresse");
                String postNr = resultSet.getString("PostNr");

                lånerListe.add(id + " : " + navn + " : " + adresse + " : " + postNr);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : lånerListe) {
            System.out.println(s);
        }
    }

    private static void OpretBy() {
        String sql = "INSERT INTO postnrtabel (PostNr, ByNavn) VALUES (?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
        ) {
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setInt(1, getInt("Skriv et postNr"));
            ps.setString(2, getString("Skriv en by"));


            ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            System.out.println("Vi er nået til række : " +id);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void OpretLåner() {
        String sql = "INSERT INTO lånertabel (Navn, Adresse, PostNr ) VALUES (?, ?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setString(1, getString("Skriv et navn"));
            ps.setString(2, getString("Skriv en adresse"));
            ps.setString(3, getString("Skriv et postNr"));


            ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            System.out.println("Vi er nået til række : " +id);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String s){
        System.out.println(s + " : ");
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }
    public static int getInt(String s){

        while (true) {
                    try {
                        int ans = Integer.parseInt(getString(s));
                        return ans;
                    } catch (NumberFormatException e) {
                        System.out.println("husk ikke et tal ord");
                    }
                }
        }

    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/bibliotek?serverTimezone=CET&useSSL=false";
        String user = "root";
        String password = "oliver";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
