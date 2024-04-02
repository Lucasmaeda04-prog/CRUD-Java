//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        ConnectionDB con = new ConnectionDB();
        ServiceDB serv = new ServiceDB();
        Application app = new Application();
        boolean sair = false;
        System.out.println("::: BEM VINDO AO SISTEMA DE ALUNOS ::::");

        while(!sair){
            System.out.println("-- O que você deseja fazer? --");
            System.out.println(" 1 - Adicionar alunos\n 2 - Listar alunos\n 3 - sair");
            char option = sc.next().charAt(0);
            switch(option){
                case '1':
                    sc.nextLine();
                    System.out.println("Digite o nome do aluno:");
                    String nome = sc.nextLine();
                    app.addStudent(con.con,nome);
                    break;

                case '2':
                    app.showStudents(con.con);
                    break;

                case '3':
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