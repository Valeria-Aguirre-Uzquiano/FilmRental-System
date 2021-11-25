package bo.edu.ucb.ingsoft.FilmRental;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import bo.edu.ucb.ingsoft.FilmRental.bl.CustomerBl;
import bo.edu.ucb.ingsoft.FilmRental.bl.FilmSearchBl;
import bo.edu.ucb.ingsoft.FilmRental.dao.CustomerDao;
import bo.edu.ucb.ingsoft.FilmRental.dao.FilmDao;

@SpringBootApplication
public class FilmRentalApplication {


	@Bean
	public FilmDao filmDao(DataSource dataSource){
		return new FilmDao(dataSource);
	}

	@Bean
	public CustomerDao customerDao(DataSource dataSource){
		return new CustomerDao(dataSource);
	}

	@Bean
	public FilmSearchBl filmSearchBl(FilmDao filmDao){
		return new FilmSearchBl(filmDao);
	}

	@Bean
	public CustomerBl customerBl(CustomerDao customerDao){
		return new CustomerBl(customerDao);
	}


	public static void main(String[] args) {
		SpringApplication.run(FilmRentalApplication.class, args);
	}

}
