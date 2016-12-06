package edu.sjsu.cmpe275.lms.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@Repository
public class UserBookDaoImpl implements UserBookDao {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
    /**
     * Returns number of books the user is holding on a particular day
     *
     * @param userId
     * @return number of books issued by user on current date
     */
    @Override
    public int getUserDayBookCount(int userId) {
        String query = "Select ub From UserBook ub WHERE ub.user = :user AND ub.checkout_date = :checkout_date";
        Query q = entityManager.createQuery(query);
        q.setParameter("user", userId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String currDate = dtf.format(LocalDate.now());
        q.setParameter("checkout_date", currDate);

        return q.getResultList().size();
    }
}
