package bo.edu.ucb.ingsoft.FilmRental.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import bo.edu.ucb.ingsoft.FilmRental.dto.Address;
import bo.edu.ucb.ingsoft.FilmRental.dto.Customer;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;
import bo.edu.ucb.ingsoft.FilmRental.dto.RentalCart;

public class CustomerDao {
    private DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
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

    public Integer insertPayment(Integer customerId, RentalCart rentalCart, int days) {
        Integer pay = 0;
        Integer staff_id = 0;
        List<Integer> idIn_List = new ArrayList<Integer>();
        List<Integer> idRe_List = new ArrayList<Integer>();
        List<Double> amount_List = new ArrayList<Double>();
        List<Film> Films = rentalCart.getRentalFilm();
        
        for(Film f: Films){
            Integer inven = InsertInventory(rentalCart.getStore_id(), f.getFilmId());
            System.out.println("id inventory: "+inven);
            if(inven != -1){
                idIn_List.add(inven);
            }
        }
        amount_List = calculateAmount(Films, rentalCart.getDiscount(), days);

        if(rentalCart.getStore_id() == 1 ){ staff_id = 1;}else{ staff_id = 2;}

        for(int i=0; i < idIn_List.size(); i++ ){
            Integer ren = InsertRental( rentalCart.getRental_date(), idIn_List.get(i), customerId, rentalCart.getReturn_date(), staff_id);
            if(ren != -1){
                idRe_List.add(ren);
            }
        }

        for(int i=0; i < idRe_List.size(); i++ ){
            pay = InsertPayment( customerId, staff_id, idRe_List.get(i), amount_List.get(i), rentalCart.getPayment_date() );
        }

        return pay;
    }

    private Integer InsertPayment(Integer customerId, Integer staff_id, Integer rental, Double amount, Date payment_date) {
        Integer res= -1;
        String query = "INSERT INTO payment " +
                    "  (customer_id, staff_id, rental_id, amount, payment_date)" +
                    "  VALUES ( ? , ? , ? , ? , ? )";
         try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ){
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, staff_id);
            pstmt.setInt(3, rental);
            pstmt.setDouble(4, amount);
            pstmt.setDate(5, payment_date);
            res = pstmt.executeUpdate();
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    private List<Double> calculateAmount(List<Film> films, Double discount, int days) {
        List<Double> list = new ArrayList<Double>();
        for(Film f: films){
            double aux = ((days * f.getRental_rate()) - ((days * f.getRental_rate())*discount));
            list.add(aux);
        }
        return list;
    }

    private Integer InsertRental(Date rental_date, Integer inven, Integer customerId, Date return_date, Integer staff_id) {
        Integer res= -1;
        String query = "INSERT INTO rental " +
                    "  (rental_date, inventory_id, customer_id, return_date, staff_id)" +
                    "  VALUES ( ? , ? , ? , ? , ? )";
         try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ){
            pstmt.setDate(1, rental_date);
            pstmt.setInt(2, inven);
            pstmt.setInt(3, customerId);
            pstmt.setDate(4, return_date);
            pstmt.setInt(5, staff_id);
            int row = pstmt.executeUpdate();
            if(row == 1){
                String query2 = " SELECT MAX(rental_id) as lastId FROM rental";
                try(
                    Connection conn2 = dataSource.getConnection();
                    var pstmt2 =  conn.prepareStatement(query2);
                    
                ){ 
                    ResultSet rs =  pstmt2.executeQuery();
                    while(rs.next()){
                        res = rs.getInt("lastId");
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }else{
                res =  -1;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    private Integer InsertInventory(Integer idStore, Integer film_id) {
        Integer res= -1;
        String query = "INSERT INTO inventory " +
                    "  (film_id, store_id)" +
                    "  VALUES ( ?, ? )";
         try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ){
            pstmt.setInt(1, film_id);
            pstmt.setInt(2, idStore);
            int row = pstmt.executeUpdate();
            if(row == 1){
                String query2 = " SELECT MAX(inventory_id) as lastId FROM inventory";
                try(
                    Connection conn2 = dataSource.getConnection();
                    var pstmt2 =  conn.prepareStatement(query2);
                    
                ){ 
                    ResultSet rs =  pstmt2.executeQuery();
                    while(rs.next()){
                        res = rs.getInt("lastId");
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }else{
                res =  -1;
            }
        }catch (Exception ex) {
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

