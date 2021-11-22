package bo.edu.ucb.ingsoft.FilmRental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

public class CustomerDao {
    private DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void InsertCustomer(){
        String query = "INSERT INTO sakila.customer " +
                        "   (store_id, first_name, last_name, email, address_id, active, create_date, last_update)" +
                        "  VALUES (?'2',? 'a',? 'b',? 'ab@gamil.com', ?'599', ?'1', ?'2020-11-21 22:04:37', ?'2020-11-21 22:04:37')";
         try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            //pstmt.setInt(1, storeId);

                //preparedStatement.setString(1, "mkyong");
                //preparedStatement.setBigDecimal(2, new BigDecimal(799.88));
                //preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
    
                //int row = preparedStatement.executeUpdate();
    
                // rows affected
                //System.out.println(row); //1
        ){}catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
