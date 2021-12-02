package bo.edu.ucb.ingsoft.FilmRental.bl;

import java.sql.Date;
import java.util.List;

import bo.edu.ucb.ingsoft.FilmRental.dao.CustomerDao;
import bo.edu.ucb.ingsoft.FilmRental.dto.Address;
import bo.edu.ucb.ingsoft.FilmRental.dto.Customer;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;
import bo.edu.ucb.ingsoft.FilmRental.dto.RentalCart;

public class CustomerBl {

    private CustomerDao customerDao;

    public CustomerBl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer insertNewCustomer(Customer customer) {
        System.out.println(customer);
        return customer;
    }

    public Address getAddress(Integer customerId) {
        return customerDao.GetAddressSend(customerId);
        
    }

    public String UpdateAddress(Address address) {
        int res = customerDao.UpdateAddress(address);
        if (res==1) {
            return "ACtualizacion exitosa";
        }else{
            return "Error en actualizacion";
        }
    }

    public String getEmail(Integer customerId) {
        return customerDao.getEmail(customerId);
    }

    public Integer newAddress(Address address) {
        return customerDao.newAddress(address);
    }

    public Integer newCustomer(Customer customer) {
        Integer res = customerDao.newCustommer(customer);
        if(res != null){
            return res;
        }else{
            return 0;
        }
    }

    public String addPayment(Integer customerId, RentalCart rentalCart) {
        double totalFinal = rentalCart.getTotal();
        int days = calculateRentalDays(rentalCart.getRental_date(), rentalCart.getReturn_date());
        //System.out.println("Dias: "+days);
        double totalcal = calculateTotal(rentalCart.getRentalFilm(), rentalCart.getDiscount(), days);
        //System.out.println("total calculado: "+ totalcal);
        if(totalFinal == totalcal){            
            int res = customerDao.insertPayment(customerId, rentalCart, days);
            if(res== 1){
                return "Registro exitoso";
            }else{
                return "Error en registro";
            }
        }else{
            return "Error en calcular el total";
        }
    }

    private int calculateRentalDays(Date rental_date, Date return_date) {
        int milisecondsByDay = 86400000;
        int d = (int) ((return_date.getTime() - rental_date.getTime()) / milisecondsByDay);
        return d;
    }

    private double calculateTotal(List<Film> rentalFilm, double dis, int days) {
        double sum = 0;
        for(Film f: rentalFilm){
            sum = sum + ((days * f.getRental_rate()) - ((days * f.getRental_rate())*dis));
        }
        return sum;
    }

    public Customer getCustomer(String user) {

        return customerDao.GetCustomer(user);
    }

    public List<String> getCities(Integer countryId) {
        return customerDao.getCities(countryId);
    }
    
}
