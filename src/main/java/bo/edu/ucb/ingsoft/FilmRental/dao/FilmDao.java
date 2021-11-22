package bo.edu.ucb.ingsoft.FilmRental.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import bo.edu.ucb.ingsoft.FilmRental.dto.Film;


public class FilmDao {
    private DataSource dataSource;

    
    public FilmDao(DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    public List<Film> FilmByCountry(Integer countryId){
        List<Film> result = new ArrayList<>();
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   group by f.film_id";   
        
        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
        ){
            pstmt.setInt(1, countryId);
            //setShort(1,"%"+countryId+"%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }                

        return result;
    }

    public List<Film> FindByTitle(Integer cid, String title) {
        List<Film> result = new ArrayList<>();
        
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   AND UPPER(title) LIKE ( ? )" +
                        "   group by f.film_id";


        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, cid);
            pstmt.setString(2,"%"+title+"%");
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public List<Film> FindByActorFirst(Integer cid, String actor) {
            List<Film> result = new ArrayList<>();
            
            String query = "SELECT f.film_id, " +
                            "   f.title, " +
                            "   f.description, " +
                            "   f.release_year, " +
                            "   f.rental_duration, " +
                            "   f.rental_rate, " +
                            "   f.last_update " +
                            "   FROM film f " +
                            "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                            "   inner join store s on (i.store_id = s.store_id) " +
                            "   inner join address a on (s.address_id = a.address_id) " +
                            "   inner join city c on (a.city_id = c.city_id) " +
                            "   inner JOIN film_actor fa ON ( f.film_id = fa.film_id ) " +
                            "   inner JOIN actor ac ON ( ac.actor_id = fa.actor_id ) "+
                            "   WHERE c.country_id LIKE ( ? ) " +
                            "   AND UPPER( ac.first_name ) LIKE ( ? )" +
                            "   group by f.film_id";
            try(
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt =  conn.prepareStatement(query);
                ){      
                pstmt.setInt(1, cid);
                pstmt.setString(2,"%"+actor+"%");
                ResultSet rs =  pstmt.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setFilmId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getShort("release_year"));
                    film.setRental_duration(rs.getInt("rental_duration"));
                    film.setRental_rate(rs.getDouble("rental_rate"));
                    java.sql.Date lastUpdate = rs.getDate("last_update");
                    film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                    result.add(film);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
                return null;
            }
            return result;
    }

    public List<Film> FindByActorLast(Integer cid, String actor) {
        List<Film> result = new ArrayList<>();
        
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   inner JOIN film_actor fa ON ( f.film_id = fa.film_id ) " +
                        "   inner JOIN actor ac ON ( ac.actor_id = fa.actor_id ) "+
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   AND UPPER( ac.last_name ) LIKE ( ? )" +
                        "   group by f.film_id";


        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, cid);
            pstmt.setString(2,"%"+actor+"%");
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
}

    public List<Film> FindByActor(Integer cid, String first, String last) {
        List<Film> result = new ArrayList<>();
        
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   inner JOIN film_actor fa ON ( f.film_id = fa.film_id ) " +
                        "   inner JOIN actor ac ON ( ac.actor_id = fa.actor_id ) "+
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   AND UPPER( ac.first_name ) LIKE ( ? )" +
                        "   AND UPPER( ac.last_name ) LIKE ( ? )" +
                        "   group by f.film_id";


        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, cid);
            pstmt.setString(2,"%"+first+"%");
            pstmt.setString(3,"%"+last+"%");
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public List<Film> FindByTitleActorFirst(Integer cid, String title, String first) {
            List<Film> result = new ArrayList<>();
            
            String query = "SELECT f.film_id, " +
                            "   f.title, " +
                            "   f.description, " +
                            "   f.release_year, " +
                            "   f.rental_duration, " +
                            "   f.rental_rate, " +
                            "   f.last_update " +
                            "   FROM film f " +
                            "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                            "   inner join store s on (i.store_id = s.store_id) " +
                            "   inner join address a on (s.address_id = a.address_id) " +
                            "   inner join city c on (a.city_id = c.city_id) " +
                            "   inner JOIN film_actor fa ON ( f.film_id = fa.film_id ) " +
                            "   inner JOIN actor ac ON ( ac.actor_id = fa.actor_id ) "+
                            //"   WHERE c.country_id LIKE ( ? ) " +
                            "   WHERE UPPER( title ) LIKE ( ? )" +
                            "   AND UPPER( ac.first_name ) LIKE ( ? )" +
                            "   group by f.film_id";
    
    
            try(
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt =  conn.prepareStatement(query);
                ){      
                //pstmt.setInt(1, cid);
                pstmt.setString(1,"%"+title+"%");
                pstmt.setString(2,"%"+first+"%");                
                ResultSet rs =  pstmt.executeQuery();
                while(rs.next()){
                    Film film = new Film();
                    film.setFilmId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    film.setDescription(rs.getString("description"));
                    film.setReleaseYear(rs.getShort("release_year"));
                    film.setRental_duration(rs.getInt("rental_duration"));
                    film.setRental_rate(rs.getDouble("rental_rate"));
                    java.sql.Date lastUpdate = rs.getDate("last_update");
                    film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                    result.add(film);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
                return null;
            }
            return result;
        
    }

    public List<Film> FindByTitleActorLast(Integer cid, String title, String last) {
        List<Film> result = new ArrayList<>();
        
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   inner JOIN film_actor fa ON ( f.film_id = fa.film_id ) " +
                        "   inner JOIN actor ac ON ( ac.actor_id = fa.actor_id ) "+
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   AND UPPER( title ) LIKE ( ? )" +
                        "   AND UPPER( ac.last_name ) LIKE ( ? )" +
                        "   group by f.film_id";


        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, cid);
            pstmt.setString(2,"%"+title+"%");
            pstmt.setString(3,"%"+last+"%");                
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public List<Film> FindByTitleActor(Integer cid, String title, String first, String last) {
        List<Film> result = new ArrayList<>();
        
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   inner JOIN film_actor fa ON ( f.film_id = fa.film_id ) " +
                        "   inner JOIN actor ac ON ( ac.actor_id = fa.actor_id ) "+
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   AND UPPER( title ) LIKE ( ? )" +
                        "   AND UPPER( ac.first_name ) LIKE ( ? )" +
                        "   AND UPPER( ac.last_name ) LIKE ( ? )" +
                        "   group by f.film_id";


        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, cid);
            pstmt.setString(2,"%"+title+"%");
            pstmt.setString(3,"%"+first+"%");   
            pstmt.setString(4,"%"+last+"%");             
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result.add(film);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public Film InsertFilm(Integer cid,Integer filmId) {
        Film result = null;
        
        String query = "SELECT f.film_id, " +
                        "   f.title, " +
                        "   f.description, " +
                        "   f.release_year, " +
                        "   f.rental_duration, " +
                        "   f.rental_rate, " +
                        "   f.last_update " +
                        "   FROM film f " +
                        "   inner JOIN inventory i ON (f.film_id = i.film_id) " +
                        "   inner join store s on (i.store_id = s.store_id) " +
                        "   inner join address a on (s.address_id = a.address_id) " +
                        "   inner join city c on (a.city_id = c.city_id) " +
                        "   WHERE c.country_id LIKE ( ? ) " +
                        "   AND f.film_id LIKE ( ? )" +
                        "   group by f.film_id";

        try(
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt =  conn.prepareStatement(query);
            ){      
            pstmt.setInt(1, cid);
            pstmt.setInt(2,filmId);          
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getShort("release_year"));
                film.setRental_duration(rs.getInt("rental_duration"));
                film.setRental_rate(rs.getDouble("rental_rate"));
                java.sql.Date lastUpdate = rs.getDate("last_update");
                film.setLastUpdate(new java.util.Date(lastUpdate.getTime()));
                result = film;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
        return result;
    }

}