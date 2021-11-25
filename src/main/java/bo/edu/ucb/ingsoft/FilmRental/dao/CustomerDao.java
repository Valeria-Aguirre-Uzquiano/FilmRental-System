package bo.edu.ucb.ingsoft.FilmRental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import bo.edu.ucb.ingsoft.FilmRental.dto.Address;
import bo.edu.ucb.ingsoft.FilmRental.dto.Customer;

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

    public Address GetAddressSend(Integer customerId) {
        System.out.println("customer = "+ customerId);
        Address result = null;
        
        String query = "SELECT a.address_id, " +
                    "   a.address, " +
                    "   a.address2, " +
                    "   a.district, " +
                    "   c.city as city, " + 
                    "   a.postal_code, " +
                    "   a.phone " +
                    "   FROM address a " +
                    "   inner join city c on (a.city_id = c.city_id) " + 
                    "   inner join customer cu on (cu.address_id = a.address_id) " + 
                    "   WHERE cu.customer_id LIKE ( ? ) "; 

        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, customerId);       
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Address address = new Address();
                address.setAddress_id(rs.getInt("address_id"));
                address.setAddress(rs.getString("address"));
                address.setAddress2(rs.getString("address2"));
                address.setDistrict(rs.getString("district"));
                address.setCity(rs.getString("city"));
                address.setPostal_code(rs.getInt("postal_code"));
                address.setPhone(rs.getLong("phone"));
                result = address;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public int UpdateAddress(Address address) {
        int res = -1;
        Integer cId = getCity_ID(address.getCity());       

        String query2 = "UPDATE address " +
                    "   SET address = ? , " +
                    "   address2 = ? , " +
                    "   district = ? , " +
                    "   city_id = ? , " + 
                    "   postal_code = ?, " +
                    "   phone = ? " + 
                    "   WHERE address_id LIKE ( ? ) "; 
        try(
            Connection conn = dataSource.getConnection();
            var pstmt =  conn.prepareStatement(query2);
            ){  
            pstmt.setString(1, address.getAddress());   
            pstmt.setString(2, address.getAddress2()); 
            pstmt.setString(3, address.getDistrict()); 
            pstmt.setInt(4, cId); 
            pstmt.setInt(5, address.getPostal_code());
            pstmt.setLong(6, address.getPhone());
            pstmt.setInt(7, address.getAddress_id());       
            res = pstmt.executeUpdate();
            System.out.println("repuesta: "+res);
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return res;
    }

    public String getEmail(Integer customerId) {
        String email = null;
        String query = "SELECT c.email FROM customer c WHERE c.customer_id LIKE ( ? )";
        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
        ){      
            pstmt.setInt(1,customerId);
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
               email = rs.getString("email");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return email;
    }

    public Integer newAddress(Address address) {
        Integer idadd = 0;
        Integer cId = getCity_ID(address.getCity()); 
        String query = "INSERT INTO address (address, address2, district, city_id, postal_code, phone) " +
                    "   VALUES ( ? , ? , ? , ? , ? , ? ) ";
        try(
            Connection conn = dataSource.getConnection();
            var pstmt =  conn.prepareStatement(query);
        ){  
            pstmt.setString(1, address.getAddress());   
            pstmt.setString(2, address.getAddress2()); 
            pstmt.setString(3, address.getDistrict()); 
            pstmt.setInt(4, cId); 
            pstmt.setInt(5, address.getPostal_code());
            pstmt.setLong(6, address.getPhone());    
            int res = pstmt.executeUpdate();
            System.out.println("repuesta: "+res);
            if (res == 1) {
                String query2 = " SELECT MAX(address_id) as lastId FROM address";
                try(
                    Connection conn2 = dataSource.getConnection();
                    var pstmt2 =  conn.prepareStatement(query2);
                    
                ){ 
                    ResultSet rs =  pstmt2.executeQuery();
                    while(rs.next()){
                        idadd = rs.getInt("lastId");
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return idadd;
    }

    public Integer newCustommer(Customer customer) {
        Integer res = 0;
        String query = "INSERT INTO customer (store_id, first_name, last_name, email, address_id, active, create_date) " +
                    "   VALUES ( ? , ? , ? , ? , ? , ?, ? ) ";
        try(
            Connection conn = dataSource.getConnection();
            var pstmt =  conn.prepareStatement(query);
        ){  
            pstmt.setInt(1, customer.getStore_id()); 
            pstmt.setString(2, customer.getFirst_name()); 
            pstmt.setString(3, customer.getLast_name()); 
            pstmt.setString(4, customer.getEmail());
            pstmt.setInt(5, customer.getAddress_id());
            pstmt.setInt(6, customer.getActive());  
            pstmt.setDate(7, customer.getCreate_date());
            res = pstmt.executeUpdate();
            System.out.println("repuesta: "+res);

        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return res;
    }

    private Integer getCity_ID(String city) {
        Integer cId=0;
        String query = "SELECT c.city_id FROM city c WHERE c.city LIKE ( ? ); ";
        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
        ){      
            pstmt.setString(1,"%"+ city +"%");
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
               cId = rs.getInt("city_id");
            }
            System.out.println("city id ="+ cId);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return cId;
    }

    
}            

