import java.math.BigDecimal;

public class Grade {
    private int id_nota;
    private BigDecimal nota;

    public Grade(int id_nota,BigDecimal nota){
        this.id_nota = id_nota;
        this.nota = nota;
    }

    public int getId_nota() {
        return id_nota;
    }

    public BigDecimal getNota() {
        return nota;
    }
}
