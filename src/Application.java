import java.math.BigDecimal;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.*;

public class Application {
    private static final BigDecimal MIN_GRADE = new BigDecimal("0");
    private static final BigDecimal MAX_GRADE = new BigDecimal("10");
    ServiceDB serv = new ServiceDB();


    public void addStudent(Connection con, String nome){
        if(serv.addStudentDB(con, nome)){
            System.out.println("---- ALUNO ADICIONADO COM SUCESSO! ----\n");
            return;
        }
        System.out.println("-- não foi possível adicionar o aluno, tente novamente mais tarde--");
    }
    public void removeStudent(Connection con, int id_aluno){

        if (serv.removeStudentDB(con,id_aluno)) {
            System.out.println("Aluno foi removido com sucesso");
            return;
        }
        System.out.println("Houve algum erro na remoção, verifique o id e tente novamente");

    }


    public void showStudents(Connection con){
        boolean sair = false;


        while(!sair){
            // printing students
            List<Student> alunos = serv.getStudentsDB(con);
            System.out.println(":::::: ALUNOS :::::::");
            for(Student al: alunos){
                System.out.printf( "Id Aluno: (%d) | Nome: %s \n", al.getId_aluno(),al.getNome());
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

    public void showStudentGrades(Connection con,int id_aluno){
        Statics studentSt = serv.getStudentGradesDB(con,id_aluno);
        System.out.printf(":::ALUNO(%d):::\nMédia:(%.2f) | N°provas:(%d) | Maior nota:(%.2f)\n", id_aluno, studentSt.getMedia(), studentSt.getQntNota(), studentSt.getNotaMax());
        System.out.println("==Notas==");
        studentSt.print();
    }

    public void selectStudent(Connection con, int id_aluno){
        //verificando se o aluno existe
        if(!serv.checkStudentDB(con,id_aluno)){
            System.out.println("Esse aluno não existe, verifique o id digitado\n");
            return;
        }

        // menu de opcoes para um aluno existente
        boolean sair = false;
        while (!sair){
            showStudentGrades(con,id_aluno);
            System.out.println("-- O que você deseja fazer? -- ");
            System.out.println("1 - adicionar nova nota");
            System.out.println("2 - remover nota");
            System.out.println("3 - voltar");
            char option = Main.sc.next().charAt(0);
            switch (option){
                case ('1'): // adicionar aluno
                    System.out.println("Digite a nota do aluno");
                    BigDecimal nota= Main.sc.nextBigDecimal();
                    Main.sc.nextLine();
                    // verificando se a nota está dentro do intervalo permitido
                    if (nota.compareTo(MIN_GRADE) >= 0 && nota.compareTo(MAX_GRADE) <= 0) {
                        addGrade(con,id_aluno,nota);
                        showStudentGrades(con,id_aluno);
                    } else {
                        System.out.println("A nota deve estar entre 0 e 10\n");
                    }
                    break;
                case ('2'): // apagar aluno
                    System.out.println("Digite o id da nota que deseja apagar");
                    int id_nota = Main.sc.nextInt();
                    Main.sc.nextLine();
                    removeGrade(con,id_aluno,id_nota);
                    showStudentGrades(con,id_aluno);
                    break;
                case ('3'): // voltar
                    sair=true;
                    System.out.println("\n");
                    break;
            }
        }
    }

    public void addGrade(Connection con, int id_aluno, BigDecimal nota){
        if(serv.addGradeDB(con,id_aluno,nota)){
            System.out.println("Nota adicionada com sucesso!\n");
            return;
        }
        System.out.println("Houve algum erro na inserção da nota, verifique se o id do aluno foi digitado corretamente;\n");

    }

    public void removeGrade(Connection con, int id_aluno, int id_nota){

        if(serv.removeGradeDB(con,id_aluno,id_nota)){
            System.out.printf("A nota de id (%d) foi removida com sucesso para o aluno (%d)\n", id_nota, id_aluno);
            return;
        }
        System.out.println("Houve algum erro na remoção da nota, verifique se o id do aluno foi digitado corretamente;\n");
    }

}