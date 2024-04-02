import java.math.BigDecimal;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.*;

public class ServiceDB {
    private static final BigDecimal MIN_GRADE = new BigDecimal("0");
    private static final BigDecimal MAX_GRADE = new BigDecimal("10");

    public boolean addStudentDB(Connection con, String nome){
        String sql = "INSERT INTO aluno (nome) VALUES (?)";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,nome);
            pstmt.executeUpdate();
            sql = "";
            pstmt.close();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean removeStudentDB(Connection con, int id_aluno){
        String sqlDeleteNotas = "DELETE FROM NOTA WHERE id_aluno = ?";
        String sqlDeleteAluno = "DELETE FROM ALUNO WHERE id_aluno = ?";

        try (PreparedStatement pstmDeleteNotas = con.prepareStatement(sqlDeleteNotas)) {
            // Apagando as notas do aluno
            pstmDeleteNotas.setInt(1, id_aluno);
            pstmDeleteNotas.execute();

            try (PreparedStatement pstmDeleteAluno = con.prepareStatement(sqlDeleteAluno)) {
                // Apagando o aluno da tabela de alunos
                pstmDeleteAluno.setInt(1, id_aluno);
                int rowsAffected = pstmDeleteAluno.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> getStudentsDB(Connection con){
        String sql =  "SELECT * FROM aluno";
        List<Student> alunos = new ArrayList<Student>();

        // mostrar todos os alunos existentes
        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);)
        {
            while(rs.next()){
                int id_aluno = rs.getInt("id_aluno");
                String nome = rs.getString("nome");
                Student student = new Student(nome,id_aluno);
                alunos.add(new Student(rs.getString("nome"), rs.getInt("id_aluno")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return alunos;
    }

    public Statics getStudentGradesDB(Connection con,int id_aluno){
        String sql_statics = "SELECT COUNT(id_nota) as qnt_nota, AVG(nota) as media, MAX(nota) as nota_max FROM nota WHERE id_aluno = (?) GROUP BY (id_aluno)";
        String sql_grades = "SELECT id_nota, nota FROM nota WHERE id_aluno = (?)";
        Statics studentSt = new Statics();

        try (PreparedStatement pstmt_statics = con.prepareStatement(sql_statics);
             PreparedStatement pstmt_grades = con.prepareStatement(sql_grades)) {

            pstmt_statics.setInt(1, id_aluno);
            pstmt_grades.setInt(1, id_aluno);

            try (ResultSet rs_statics = pstmt_statics.executeQuery();
                 ResultSet rs_grades = pstmt_grades.executeQuery()) {

                if (rs_statics.next()) {
                    studentSt.setQntNota(rs_statics.getInt("qnt_nota"));
                    studentSt.setMedia(rs_statics.getBigDecimal("media"));
                    studentSt.setNotaMax(rs_statics.getBigDecimal("nota_max"));

                    while (rs_grades.next()) { // adding the grades to the list in the statics
                        studentSt.addGrade(new Grade(rs_grades.getInt("id_nota"),rs_grades.getBigDecimal("nota")));
                    }
                    System.out.println("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentSt;
    }

    public boolean checkStudentDB(Connection con, int id_aluno){
        boolean sair = false;
        String sql_checkStudent = "SELECT * FROM aluno WHERE id_aluno = (?)";
        try(// query para verificar se aluno existe
            PreparedStatement pstm_check = con.prepareStatement(sql_checkStudent)) {
            pstm_check.setInt(1,id_aluno);
            try(ResultSet rs_check = pstm_check.executeQuery()){
                if(!rs_check.next()){ // aluno inexistente
                    return false;
                } // aluno existe
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean addGradeDB(Connection con, int id_aluno, BigDecimal nota){
        String sql = "INSERT INTO NOTA (id_aluno,nota) VALUES (?,?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, id_aluno);
            pstm.setBigDecimal(2, nota);
            if (pstm.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean removeGradeDB(Connection con, int id_aluno, int id_nota){
        String sql = "DELETE FROM NOTA WHERE id_aluno = (?) AND id_nota = (?)";
        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, id_aluno);
            pstm.setInt(2, id_nota);
            if (pstm.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}