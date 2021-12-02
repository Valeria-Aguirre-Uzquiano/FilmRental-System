package bo.edu.ucb.ingsoft.FilmRental.bl;

import java.util.ArrayList;
import java.util.List;


import bo.edu.ucb.ingsoft.FilmRental.dao.FilmDao;
import bo.edu.ucb.ingsoft.FilmRental.dto.Film;
import bo.edu.ucb.ingsoft.FilmRental.dto.Store;



public class FilmSearchBl {

    private FilmDao filmDao;
    Store store = new Store();
    List<Film> rentalList = new ArrayList<>();
    

    public FilmSearchBl(FilmDao filmDao){
        this.filmDao = filmDao;
    }

    public List<Film> FilmsByCountry( Integer countryId){        
        store.setStoreId(countryId);
        return filmDao.FilmByCountry(countryId);
    }

    public List<Film> SearchDebutFilms(Integer countryId) {
        store.setStoreId(countryId);
        return filmDao.SearchDebutFilms(countryId);
    }

    public List<Film> SearchLastRental(Integer countryId) {
        store.setStoreId(countryId);
        return filmDao.SearchLastRental(countryId);
    }

    public List<Film> SearchMaxRental(Integer countryId) {
        store.setStoreId(countryId);
        return filmDao.SearchMaxRental(countryId);
    }

    public List<Film> FilmsByParam(String title, String actor) {
        System.out.println("tienda =" + store.getStoreId());
        List<Film> result = null;
        if (title == null || title.trim().equals("")) {
            //System.out.println("title empty");
            if (actor == null || actor.trim().equals("")) {
                System.out.println("actor empty");
            }else{
               // System.out.println("actor ="+actor);
                result = processActor(store.getStoreId(), null, actor);
            }
        }else{
            if (actor == null || actor.trim().equals("")) {
                //System.out.println("actor empty");
                //System.out.println("title ="+title);
                result = filmDao.FindByTitle(store.getStoreId(), title);
            }else{
                System.out.println("title = "+title + "y actor ="+actor);
                result = processActor(store.getStoreId(), title, actor);

            }
        }
        return result;
    }

    private List<Film> processActor(Integer cid, String title,String actor) {
        List<Film> res= null;
        String[] array_actor = actor.split("_");
        if(title == null){
            if(array_actor.length == 1){
                res = filmDao.FindByActorFirst(cid, array_actor[0]);
                //System.out.println("res length: "+ res.size());
                if(res.size() == 0){
                    res = filmDao.FindByActorLast(cid, array_actor[0]);
                }
            }else{
                res = filmDao.FindByActor(cid, array_actor[0], array_actor[1]);
            }
        }else{
            System.out.println("busqueda doble");
            if(array_actor.length == 1){
                res = filmDao.FindByTitleActorFirst(cid, title, array_actor[0]);
                //System.out.println("res length: "+ res.size());
                if(res.size() == 0){
                    res = filmDao.FindByTitleActorLast(cid, title, array_actor[0]);;
                }
            }else{
                res = filmDao.FindByTitleActor(cid, title, array_actor[0], array_actor[1]);
            }
            
        }
        
        return res;
    }

    public void CartAddFilm(Integer filmId) {
        System.out.println("tienda =" + store.getStoreId());        
        rentalList.add(filmDao.InsertFilm(store.getStoreId(),filmId));
    }

    public List<Film> FilmInCart() {
        return rentalList;
    }

    public void CartDeleteFilm(Integer filmId) {
        
        for(Film it: rentalList){
            System.out.println("entra a delete "+it.getFilmId());
            if(it.getFilmId().equals(filmId)){
                System.out.println("Filmid ="+filmId);
                rentalList.remove(it);
            }
        }
    }    
    
}
