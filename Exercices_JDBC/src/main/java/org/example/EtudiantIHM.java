package org.example;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EtudiantIHM {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=exo1_jdbc";
    private static final String username = "postgres";
    private static final String password = "test";

    private static Connection connection = null;
    private static Statement st = null;
    private static ResultSet rs = null;

    public static void StartEtudiantManager() throws SQLException {
        int userChoice;

        do{
            displayMainMenu();
            userChoice = userChoice(0,4);

            switch (userChoice){
                case 1 -> loadStudents(TypeStudentLoading.ALL);
                case 2 -> displayAddStudent();
                case 3 -> loadStudents(TypeStudentLoading.BY_CLASS);
                case 4 -> displayDeleteStudent();
            }
        }while(userChoice != 0);

        System.out.println("Merci d'avoir utilisé notre programme.");
    }

    public static void displayMainMenu(){
        System.out.println("\n=== Student manager ===\n");
        System.out.println("1. Afficher les étudiants");
        System.out.println("2. Ajouter un étudiant");
        System.out.println("3. Afficher les étudiants d'une classe");
        System.out.println("4. Supprimer un étudiant ( ◣∀◢)ψ ");
        System.out.println("0. Quitter le programme");
    }

    public static void displayAddStudent() throws SQLException {
        Etudiant student = new Etudiant();

        System.out.print("Veuillez entrer le nom de l'étudiant : ");
        student.setNom(userNameInput());
        System.out.print("Veuillez entrer le prenom de l'étudiant : ");
        student.setPrenom(userNameInput());
        System.out.print("Veuillez entrer le numéro de sa classe. ");
        student.setNumeroDeClasse(userChoice(0,10));
        System.out.println("Veuillez entrer la date de diplome.");
        student.setDateDiplome(userDateInput());

        saveEtudiant(student);
    }

    public static void displayDeleteStudent() throws SQLException {
        loadStudents(TypeStudentLoading.ALL);
        System.out.println("Veuillez entrer l'id de l'étudiant à supprimer.");
        deleteStudent(userChoice(0, 100));
    }

    public static void loadStudents(TypeStudentLoading type){
        String request;

        try {
            connection = DriverManager.getConnection(url, username, password);
            if(connection != null){
                System.out.println("La connexion est ok");

            }else {
                System.out.println("Connexion échoué");
            }

            request = switch (type) {
                case ALL -> "SELECT * FROM etudiant";
                case BY_CLASS -> {
                    System.out.println("Veuillez entrer le numero de la classe. ");
                    yield "SELECT * FROM etudiant WHERE numero_de_classe = " + userChoice(0, 10);
                }
            };

            st = connection.createStatement();
            rs = st.executeQuery(request);
            while (rs.next()){
                Etudiant etudiant = Etudiant.builder()
                        .id(rs.getInt(1))
                        .nom(rs.getString(2))
                        .prenom(rs.getString(3))
                        .dateDiplome(rs.getDate(4).toLocalDate())
                        .numeroDeClasse(rs.getByte(5))
                        .build();

                System.out.println(etudiant);
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteStudent(int id) throws SQLException {
        PreparedStatement pst;
        connection = DriverManager.getConnection(url, username, password);
        pst = connection.prepareStatement("DELETE FROM etudiant WHERE etudiant_id = ?");
        pst.setInt(1, id);

        int row = pst.executeUpdate();
        System.out.println(row);

        pst.close();
        connection.close();
    }

    private static void saveEtudiant(Etudiant student) throws SQLException {
        PreparedStatement pst;
        connection = DriverManager.getConnection(url, username, password);
        pst = connection.prepareStatement("INSERT INTO etudiant (nom, prenom, date_diplome,numero_de_classe) VALUES (?,?,?,?)");

        pst.setString(1, student.getNom());
        pst.setString(2, student.getPrenom());
        pst.setDate(3, Date.valueOf(student.getDateDiplome()));
        pst.setInt(4, student.getNumeroDeClasse());

        int rows = pst.executeUpdate();
        if(rows > 0)
            System.out.println("INSERT OK");

        pst.close();
        connection.close();
    }

    public static String userNameInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static LocalDate userDateInput(){
        int day = 0;
        int month = 0;
        int year = 0;

        Scanner sc = new Scanner(System.in);

        System.out.print("Veuillez enter le jour. ");
        day = userChoice(0,31);
        System.out.print("Veuillez enter le mois. ");
        month = userChoice(0,12);
        System.out.print("Veuillez enter l'année. ");
        year = userChoice(1800,2050);

        return LocalDate.of(year,month,day);
    }

    public static int userChoice(int minChoice, int maxChoice){
        int inputChoice;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Votre choix : ");
            try {
                inputChoice = sc.nextInt();
                if(inputChoice < minChoice || inputChoice > maxChoice) throw new IllegalArgumentException();
                break;
            }catch (InputMismatchException e){
                System.out.println("Vous n'avez pas entrer un entier.");
            }catch (IllegalArgumentException e){
                System.out.println("Vous n'avez pas entrer une option correct.");
                System.out.println("(La valeur doit être comprise entre "+ minChoice + " et " + maxChoice + ")");
            }
        }while (true);

        return inputChoice;
    }
}
