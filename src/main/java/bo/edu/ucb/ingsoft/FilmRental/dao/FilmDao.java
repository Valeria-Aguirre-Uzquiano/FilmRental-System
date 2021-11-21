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
}
