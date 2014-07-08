package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Merchant;
import org.personal.mason.fl.domain.model.Order;
import org.personal.mason.fl.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by mason on 6/28/14.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatusIn(Collection<String> status);
	
	List<Order> findByStatus(String status);
	
	List<Order> findByStatusAndCavalier(String status, User cavalier);
	
	List<Order> findByStatusAndUser(String status, User user);
	
	List<Order> findByStatusAndMerchant(String status, Merchant merchant);

    List<Order> findByMerchantAndStatusIn(Merchant merchant, Collection<String> status);
	
	List<Order> findBySubmitTimeAfter(Date submitTime);
	
	List<Order> findByStatusAndSubmitTimeAfter(String status, Date submitTime);
	
	List<Order> findByStatusAndCavalierAndSubmitTimeAfter(String status, User cavalier, Date submitTime);

	List<Order> findByStatusAndUserAndSubmitTimeAfter(String status, User user, Date submitTime);
	
	List<Order> findByStatusAndMerchantAndSubmitTimeAfter(String status, Merchant merchant, Date submitTime);
    List<Order> findByStatusInAndMerchantAndSubmitTimeAfter(Collection<String> status, Merchant merchant, Date submitTime);

    List<Order> findBySubmitTimeBetween(Date from, Date to);

    List<Order> findByCavalierAndSubmitTimeBetween(User cavalier, Date from, Date to);

    List<Order> findByUserAndSubmitTimeBetween(User user, Date from, Date to);

    List<Order> findByMerchantAndSubmitTimeBetween(Merchant merchant, Date from, Date to);

    List<Order> findByStatusAndSubmitTimeBetween(String status, Date from, Date to);

    List<Order> findByStatusAndCavalierAndSubmitTimeBetween(String status, User cavalier, Date from, Date to);

    List<Order> findByStatusAndUserAndSubmitTimeBetween(String status, User user, Date from, Date to);

    List<Order> findByStatusAndMerchantAndSubmitTimeBetween(String status, Merchant merchant, Date from, Date to);
    List<Order> findByStatusInAndMerchantAndSubmitTimeBetween(Collection<String> status, Merchant merchant, Date from, Date to);

    List<Order> findByCavalierIsNull();

    @Query("select count(o) from Order o where o.cavalier = :cavalier and o.status = :status")
    long countByCavalierAndStatus(@Param("cavalier") User cavalier, @Param("status") String status);
}
