package bo.edu.ucb.ingsoft.FilmRental.api;

import java.util.List;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import bo.edu.ucb.ingsoft.FilmRental.bl.FilmSearchBl;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;

/*
Bajo la perspectiva high cohesion. El API reste deberia validar lo que el cliente envio,
entendiendo por cliente a las aplicaciones web y movil, son datos correctos....  
*/
@RestController
public class FilmRentalAPI {
    FilmSearchBl filmSearchBl;
    
    public FilmRentalAPI(FilmSearchBl filmSearchBl){
        this.filmSearchBl = filmSearchBl;
    }

    @GetMapping(value = "/film/{countryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Film> FilmsByCountry(@PathVariable Integer countryId){
        return filmSearchBl.FilmsByCountry(countryId);
    }
}
