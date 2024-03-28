
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main {

    public static void main(String[] args) {
        ConnectionDB con = new ConnectionDB();
        ServiceDB serv = new ServiceDB();
        boolean sair = false;
        Scanner sc = new Scanner(System.in);
        System.out.println(":::BEM VINDO AO SISTEMA DE ALUNOS ::::");

        while(!sair){
            System.out.println("O que você deseja fazer ?");
            System.out.println(" 1 - Adicionar alunos\n 2 - Listar alunos\n 3 - Ver maior nota\n 4 - sair");
            char option = sc.next().charAt(0);
            switch(option){
                case '1':

                    System.out.println("Digite o id do aluno:");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Digite o nome do aluno:");
                    String nome = sc.nextLine();
                    serv.addStudent(con,id,nome);
                    System.out.println("Aluno adicionado com sucesso.");
                    break;
                case '2':
                    serv.showStudents(con);
                    break;
                case '3':
                    System.out.println("Maior nota");
                    break;
                case '4':
                    System.out.println("Até breve!");
                    sair = true;
                    con.closeConnection();
                    break;
                default:
                    System.out.println("Opção inváida.");

            }
        }



    }
}