import java.math.BigDecimal;
import java.sql.*;
import java.sql.PreparedStatement;

public class ServiceDB {
    String sql = "";
    public void addStudent(Connection con, String nome){
        sql = "INSERT INTO aluno (nome) VALUES (?)";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,nome);
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
            int id;
            switch (option){
                case ('1'):
                    System.out.println("Digite o id do aluno");
                    id = Main.sc.nextInt();
                    Main.sc.nextLine();
                    selectStudent(con,id);
                    break;
                case ('2'):
                    System.out.println("Digite o id do aluno");
                    id = Main.sc.nextInt();
                    Main.sc.nextLine();
                    removeStudent(con,id);
                    break;
                case ('3'):
                    sair=true;
                    System.out.println("\n");
                    break;
            }
        }
    }
    public void selectStudent(Connection con, int id_aluno){
        String sql_statics = "SELECT COUNT(id_nota) as qnt_nota, AVG(nota) as media, MAX(nota) as nota_max FROM nota WHERE id_aluno = (?) GROUP BY (id_aluno)";
        String sql_grades = "SELECT id_nota, nota FROM nota WHERE id_aluno = (?)";


        try{
            // preparando a query
            PreparedStatement pstmt_statics = con.prepareStatement(sql_statics);
            PreparedStatement pstmt_grades = con.prepareStatement(sql_grades);
            pstmt_statics.setInt(1,id_aluno);
            pstmt_grades.setInt(1,id_aluno);
            ResultSet rs_statics = pstmt_statics.executeQuery();
            ResultSet rs_grades = pstmt_grades.executeQuery();

            // percorrendo por ela e printar os resultados
            if(rs_statics.next()){ // verificando se existe alguma nota com esse id

                // pegar estatísticas de média, quant, maior nota
                int qnt_nota = rs_statics.getInt("qnt_nota");
                BigDecimal media = rs_statics.getBigDecimal("media");
                BigDecimal nota_max = rs_statics.getBigDecimal("nota_max");
                System.out.printf(":::ALUNO(%d):::\nMédia:(%.2f) | N°provas:(%d) | Maior nota:(%.2f)\n",id_aluno,media,qnt_nota,nota_max);
                System.out.println("==Notas==");
                // mostrando as notas
                while(rs_grades.next()){
                    int id_nota = rs_grades.getInt("id_nota");
                    BigDecimal nota = rs_grades.getBigDecimal("nota");
                    System.out.printf("Id_nota:(%d) | Nota: %.2f\n",id_nota,nota);
                };
            }else {
                System.out.println("Não há nenhuma nota para esse id_aluno");
            }
            System.out.println("\n");
            menuStudent(con,id_aluno);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void menuStudent(Connection con, int id_aluno){
        boolean sair = false;
        // menu de opcoes para um aluno

        while (!sair){
            System.out.println("-- O que você deseja fazer? -- ");
            System.out.println("1 - adicionar nova nota");
            System.out.println("2 - remover nota");
            System.out.println("3 - voltar");
            char option = Main.sc.next().charAt(0);
            switch (option){
                case ('1'):
                    System.out.println("Digite a nota do aluno");
                    BigDecimal nota= Main.sc.nextBigDecimal();
                    Main.sc.nextLine();
                    addGrade(con,id_aluno,nota);
                    // TO-DO levar a algum outro método passando a con e o id para fazer query
                    break;
                case ('2'):
                    System.out.println("Digite o id da nota que deseja apagar");
                    int id_nota = Main.sc.nextInt();
                    Main.sc.nextLine();
                    removeGrade(con,id_aluno,id_nota);
                    break;
                case ('3'):
                    sair=true;
                    System.out.println("\n");
                    break;
            }
        }
    }

    public void addGrade(Connection con, int id_aluno, BigDecimal nota){
        String sql = "INSERT INTO NOTA (id_aluno,nota) VALUES (?,?)";
        try{
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1,id_aluno);
            pstm.setBigDecimal(2,nota);
            if(pstm.executeUpdate()>0){
                System.out.printf("Uma nova nota foi inserida com sucesso para o aluno (%d)\n",id_aluno);
            }
            else{
                System.out.println("Houve algum erro na inserção da nota, tente novamente");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeGrade(Connection con, int id_aluno, int id_nota){
        String sql = "DELETE FROM NOTA WHERE id_aluno = (?) AND id_nota = (?)";
        try{
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1,id_aluno);
            pstm.setInt(2,id_nota);
            if(pstm.executeUpdate()>0){
                System.out.printf("A nota de id (%d) foi removido com sucesso para o aluno (%d)\n",id_nota,id_aluno);
            }
            else{
                System.out.println("Houve algum erro na remoção da nota, tente novamente\n");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void removeStudent(Connection con, int id_aluno){

        String sql = "DELETE FROM NOTA WHERE id_aluno = (?)";
        try{
            // apagando as notas do aluno
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1,id_aluno);
            pstm.execute();
            // apagando o aluno da tabela de alunos
            sql = "DELETE FROM ALUNO WHERE id_aluno = (?)";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1,id_aluno);
            if(pstm.executeUpdate()>0){
                System.out.println("Aluno foi removido com sucesso");
            }
            else{
                System.out.println("Houve algum erro, tente novamente");
            }
        }catch (SQLException e){

        }
    }
}