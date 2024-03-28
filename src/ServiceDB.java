import java.sql.*;
import java.sql.PreparedStatement;

public class ServiceDB {
    String sql = "";
    public void addStudent(ConnectionDB con, int id, String nome){
        sql = "INSERT INTO aluno (id_aluno,nome) VALUES (?,?)";
        try{
            PreparedStatement pstmt = con.con.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.setString(2,nome);
            pstmt.executeUpdate();
            sql = "";
            pstmt.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void showStudents(ConnectionDB con){
        sql =  "SELECT * FROM aluno";
        try{
            Statement st = con.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println(":::::: ALUNOS :::::::");
            while(rs.next()){
                int id_aluno = rs.getInt("id_aluno");
                String nome = rs.getString("nome");
                System.out.printf( "Id Aluno: (%d) | Nome: %s \n", id_aluno,nome);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
