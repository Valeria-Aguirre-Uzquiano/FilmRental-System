package bo.edu.ucb.ingsoft.FilmRental.bl;

import bo.edu.ucb.ingsoft.FilmRental.dao.CustomerDao;
import bo.edu.ucb.ingsoft.FilmRental.dto.Address;
import bo.edu.ucb.ingsoft.FilmRental.dto.Customer;

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

    public String newCustomer(Customer customer) {
        Integer res = customerDao.newCustommer(customer);
        if(res == 1){
            return "Registro exitoso";
        }else{
            return "Error en registro";
        }
    }
    
}
