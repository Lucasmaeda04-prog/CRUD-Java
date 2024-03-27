
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main {

    public static void main(String[] args) {
        ConnectionDB db = new ConnectionDB();
        boolean sair = false;
        Scanner sc = new Scanner(System.in);
        System.out.println(":::BEM VINDO AO SISTEMA DE ALUNOS ::::");

        while(!sair){
            System.out.println("O que você deseja fazer ?");
            System.out.println(" 1 - Adicionar alunos\n 2 - Listar alunos\n 3 - Ver maior nota\n 4 - sair");
            char option = sc.next().charAt(0);

            switch(option){
                case '1':
                    System.out.println("Aluno adicionado com sucesso.");
                    break;
                case '2':
                    System.out.println("ALUNOS - ");
                    break;
                case '3':
                    System.out.println("Maior nota");
                    break;
                case '4':
                    System.out.println("Até breve!");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inváida.");

            }
        }



    }
}