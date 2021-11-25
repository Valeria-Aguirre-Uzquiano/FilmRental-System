package bo.edu.ucb.ingsoft.FilmRental.dto;


import java.sql.Date;
import java.util.List;

public class RentalCart {
    private Integer store_id;
    private List<Film> rentalFilm;
    private Date rental_date;
    private Date payment_date;
    private Date return_date;
    private Double discount;
    private Double total;

    public RentalCart() {
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }

    public List<Film> getRentalFilm() {
        return rentalFilm;
    }

    public void setRentalFilm(List<Film> rentalFilm) {
        this.rentalFilm = rentalFilm;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }
        
}
