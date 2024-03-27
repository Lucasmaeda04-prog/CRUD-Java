import java.sql.*;

public class ConnectionDB {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "guia";
    String password = "Senh@123";
    Connection con;


    // CONSTRUCTOR:
    public ConnectionDB(){
        try {
            this.con = (Connection) DriverManager.getConnection(this.url, this.username, this.password);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    // METHODS:
    public void closeConnection(){
        try{
            if(!this.con.isClosed()){
                this.con.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void execQuery(String sql){
        try{
            Statement st = con.createStatement();
            st.executeQuery(sql);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
