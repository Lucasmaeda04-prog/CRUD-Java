import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Statics {
    private List<Grade> notas;
    private BigDecimal media;
    private BigDecimal nota_max;
    private int qnt_nota;


    // CONSTRUTOR
    public Statics() {
        this.notas = new ArrayList<Grade>();
        this.media = BigDecimal.ZERO;
        this.qnt_nota = 0;
        this.nota_max = BigDecimal.ZERO;
    }

    public BigDecimal getMedia() {
        return media;
    }

    public int getQntNota() {
        return qnt_nota;
    }

    public BigDecimal getNotaMax() {
        return nota_max;
    }

    // Setters
    public void setMedia(BigDecimal media) {
        this.media = media;
    }

    public void setQntNota(int qnt_nota) {
        this.qnt_nota = qnt_nota;
    }

    public void setNotaMax(BigDecimal nota_max) {
        this.nota_max = nota_max;
    }

    // Método para adicionar uma nota
    public void addGrade(Grade novaNota) {
        notas.add(novaNota);
    }

    // Método para imprimir os elementos da lista de notas
    public void print() {
        System.out.println("Notas:");
        for (Grade nota : notas) {
            System.out.printf("Id_nota:(%d) | Nota: %.2f\n", nota.getId_nota(), nota.getNota());
        }
    }
}
