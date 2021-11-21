package bo.edu.ucb.ingsoft.FilmRental;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import bo.edu.ucb.ingsoft.FilmRental.bl.FilmSearchBl;
import bo.edu.ucb.ingsoft.FilmRental.dao.FilmDao;

@SpringBootApplication
public class FilmRentalApplication {


	@Bean
	public FilmDao filmDao(DataSource dataSource){
		return new FilmDao(dataSource);
	}

	@Bean
	public FilmSearchBl filmSearchBl(FilmDao filmDao){
		return new FilmSearchBl(filmDao);
	}


	public static void main(String[] args) {
		SpringApplication.run(FilmRentalApplication.class, args);
	}

}
