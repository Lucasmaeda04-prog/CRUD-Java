import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String sql = "insert into aluno (id_aluno,nome) values (10,'maria')";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "guia";
        String password = "Senh@123";

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            st.executeQuery(sql);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}