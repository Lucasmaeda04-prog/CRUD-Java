import java.math.BigDecimal;
import java.sql.*;
import java.sql.PreparedStatement;

public class ServiceDB {
    String sql = "";
    public void addStudent(Connection con, int id, String nome){
        sql = "INSERT INTO aluno (id_aluno,nome) VALUES (?,?)";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
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

    public void showStudents(Connection con){
        sql =  "SELECT * FROM aluno";
        boolean sair = false;

        while(!sair){
            // mostrar todos os alunos existentes
            try{
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println(":::::: ALUNOS :::::::");
                while(rs.next()){
                    int id_aluno = rs.getInt("id_aluno");
                    String nome = rs.getString("nome");
                    System.out.printf( "Id Aluno: (%d) | Nome: %s \n", id_aluno,nome);
                }
                st.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
            // menu de opcoes dos alunos
            System.out.println("-- O que você deseja fazer? -- ");
            System.out.println("1 - selecionar aluno");
            System.out.println("2 - remover aluno");
            System.out.println("3 - voltar");
            char option = Main.sc.next().charAt(0);
            switch (option){
                case ('1'):
                    System.out.println("Digite o id do aluno");
                    int id = Main.sc.nextInt();
                    selectStudent(con,id);
                    // TO-DO levar a algum outro método passando a con e o id para fazer query
                    break;
                case ('2'):
                    System.out.println("Digite o id do aluno");
                    // TO-DO levar a algum outro método passando a con e o id para fazer query
                    break;
                case ('3'):
                    sair=true;
                    System.out.println("\n");
                    break;
            }
        }
    }
    public void selectStudent(Connection con, int id){
        String sql_statics = "SELECT COUNT(id_nota) as qnt_nota, AVG(nota) as media, MAX(nota) as nota_max FROM nota WHERE id_aluno = (?) GROUP BY (id_aluno)";
        String sql_grades = "SELECT id_nota, nota FROM nota WHERE id_aluno = (?)";


        try{
            // preparando a query
            PreparedStatement pstmt_statics = con.prepareStatement(sql_statics);
            PreparedStatement pstmt_grades = con.prepareStatement(sql_grades);
            pstmt_statics.setInt(1,id);
            pstmt_grades.setInt(1,id);
            ResultSet rs_statics = pstmt_statics.executeQuery();
            ResultSet rs_grades = pstmt_grades.executeQuery();

            // percorrendo por ela e printar os resultados
            if(rs_statics.next()){ // verificando se existe alguma nota com esse id

                // pegar estatísticas de média, quant, maior nota
                int qnt_nota = rs_statics.getInt("qnt_nota");
                BigDecimal media = rs_statics.getBigDecimal("media");
                BigDecimal nota_max = rs_statics.getBigDecimal("nota_max");
                System.out.printf(":::ALUNO(%d):::\nMédia:(%.2f) | N°provas:(%d) | Maior nota:(%.2f)\n",id,media,qnt_nota,nota_max);
                System.out.println("==Notas==");
                // mostrando as notas
                while(rs_grades.next()){
                    int id_nota = rs_grades.getInt("id_nota");
                    BigDecimal nota = rs_grades.getBigDecimal("nota");
                    System.out.printf("Id_nota:(%d) | Nota: %.2f\n",id_nota,nota);
                };
            }else {
                System.out.println("Não há nenhuma nota com esse id, verifique se digitou o id correto");
            }
            System.out.println("\n");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void menuStudent(Connection con){
        boolean sair = false;
        // menu de opcoes para um aluno
        System.out.println("-- O que você deseja fazer? -- ");
        System.out.println("1 - adicionar nota");
        System.out.println("2 - remover nota");
        System.out.println("3 - voltar");
        char option = Main.sc.next().charAt(0);
        while (!sair){
            switch (option){
                case ('1'):
                    System.out.println("Digite o id do aluno e sua nota");
                    int id = Main.sc.nextInt();
                    // TO-DO levar a algum outro método passando a con e o id para fazer query
                    break;
                case ('2'):
                    System.out.println("Digite o id do aluno e o id da nota que deseja apagar");
                    // TO-DO levar a algum outro método passando a con e o id para fazer query
                    break;
                case ('3'):
                    sair=true;
                    System.out.println("\n");
                    break;
            }
        }
    }
}