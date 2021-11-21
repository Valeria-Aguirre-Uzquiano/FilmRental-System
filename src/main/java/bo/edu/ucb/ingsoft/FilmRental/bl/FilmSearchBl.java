package bo.edu.ucb.ingsoft.FilmRental.bl;

import java.util.List;


import bo.edu.ucb.ingsoft.FilmRental.dao.FilmDao;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;



public class FilmSearchBl {

    private FilmDao filmDao;
    

    public FilmSearchBl(FilmDao filmDao){
        this.filmDao = filmDao;
    }

    public List<Film> FilmsByCountry( Integer countryId){

        return filmDao.FilmByCountry(countryId);
    }
    
}
