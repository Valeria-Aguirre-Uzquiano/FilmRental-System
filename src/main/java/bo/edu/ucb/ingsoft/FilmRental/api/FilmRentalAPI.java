package bo.edu.ucb.ingsoft.FilmRental.api;

import java.util.List;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.ingsoft.FilmRental.bl.CustomerBl;
import bo.edu.ucb.ingsoft.FilmRental.bl.FilmSearchBl;
import bo.edu.ucb.ingsoft.FilmRental.dto.Customer;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;

/*
Bajo la perspectiva high cohesion. El API reste deberia validar lo que el cliente envio,
entendiendo por cliente a las aplicaciones web y movil, son datos correctos....  
*/
@RestController
public class FilmRentalAPI {
    FilmSearchBl filmSearchBl;
    CustomerBl customerBl;
    
    public FilmRentalAPI(FilmSearchBl filmSearchBl){
        this.filmSearchBl = filmSearchBl;
    }

    @GetMapping(value = "/film/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> FilmsByCountry(@PathVariable Integer countryId){
        return filmSearchBl.FilmsByCountry(countryId);
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

    @DeleteMapping(value = "/film/rental/deleteFilm/{filmId}")
    public void deleteFilml(@PathVariable Integer filmId){
        filmSearchBl.CartDeleteFilm(filmId);
    }

    @PostMapping( value = "/customer/addCustomer", consumes = "application/json")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerBl.insertNewCustomer(customer);
    }
}
