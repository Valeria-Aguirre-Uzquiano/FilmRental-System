package bo.edu.ucb.ingsoft.FilmRental.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.ingsoft.FilmRental.bl.CustomerBl;
import bo.edu.ucb.ingsoft.FilmRental.bl.FilmSearchBl;
import bo.edu.ucb.ingsoft.FilmRental.dto.Address;
import bo.edu.ucb.ingsoft.FilmRental.dto.Customer;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;
import bo.edu.ucb.ingsoft.FilmRental.dto.RentalCart;


@RestController
public class FilmRentalAPI {
    FilmSearchBl filmSearchBl;
    CustomerBl customerBl;

    public FilmRentalAPI(FilmSearchBl filmSearchBl, CustomerBl customerBl) {
        this.filmSearchBl = filmSearchBl;
        this.customerBl = customerBl;
    }

    @GetMapping(value = "/film/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> FilmsByCountry(@PathVariable Integer countryId){
        return filmSearchBl.FilmsByCountry(countryId);
    }

    @GetMapping(value = "/film/debut/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> SearchDebutFilms(@PathVariable Integer countryId){
        return filmSearchBl.SearchDebutFilms(countryId);
    }

    @GetMapping(value = "/film/lastrental/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> SearchLastRental(@PathVariable Integer countryId){
        return filmSearchBl.SearchLastRental(countryId);
    }

    @GetMapping(value = "/film/maxrental/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> SearchMaxRental(@PathVariable Integer countryId){
        return filmSearchBl.SearchMaxRental(countryId);
    }

    @GetMapping(value = "/film/findByParameters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> findByParameters(@RequestParam (name = "title", required = false) String title, @RequestParam (name = "actor", required = false) String actor){
        return filmSearchBl.FilmsByParam(title, actor);
    }

    @PostMapping(value = "/film/rental/addFilm/{filmId}")
    public void addFilm(@PathVariable Integer filmId){
        filmSearchBl.CartAddFilm(filmId);
    }

    @GetMapping(value = "/film/rental", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> rental(){
        return filmSearchBl.FilmInCart();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "/film/rental/deleteFilm/{filmId}")
    public void deleteFilml(@PathVariable Integer filmId){
        filmSearchBl.CartDeleteFilm(filmId);
    }

    @PostMapping( value = "/customer/addCustomer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> addCustomer(@RequestBody Customer customer) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("IdCos", customerBl.newCustomer(customer));
        return map;
        
    }

    @PostMapping( value = "/customer/addAddress", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Integer> addCustomer(@RequestBody Address address) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("IdAdd", customerBl.newAddress(address));
        return map;
    }

    @GetMapping(value = "/costumer/payment/address/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Address getAddress(@PathVariable Integer customerId){
       return customerBl.getAddress(customerId);
        
    }

    @PutMapping(value = "/costumer/payment/address", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateAddress(@RequestBody Address address){
        return customerBl.UpdateAddress(address);
    }

    @GetMapping(value = "/costumer/payment/email/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getEmail(@PathVariable Integer customerId){
        HashMap<String, String> map = new HashMap<>();
        map.put("email", customerBl.getEmail(customerId));
        return map;
        
    }

    @PostMapping( value = "/costumer/payment/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addCustomer(@PathVariable Integer customerId, @RequestBody RentalCart rentalCart)  {
        return customerBl.addPayment(customerId, rentalCart);
    }

    @GetMapping(value = "/costumer/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable String user){
        return customerBl.getCustomer(user);
        
    }

    @GetMapping(value = "/costumer/city/{countryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCities(@PathVariable Integer countryId){
        return customerBl.getCities(countryId);
        
    }
}
