public class Student {
    private String nome;
    private int id_aluno;

    public Student(String nome, int id_aluno){
        this.nome = nome;
        this.id_aluno = id_aluno;
    }

    public String getNome() {
        return this.nome;
    }
    public int getId_aluno() {
        return this.id_aluno;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
