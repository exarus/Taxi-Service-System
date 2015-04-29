package ua.com.tracksee.dao.postrgresql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.tracksee.dao.TaxiOrderDAO;
import ua.com.tracksee.entities.ServiceUserEntity;
import ua.com.tracksee.entities.TaxiOrderEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * <p>Postgresql database implementation of
 * {@link TaxiOrderDAO} interface.</p>
 * <p>Used for persisting and accessing taxi order data.</p>
 *
 * @see ua.com.tracksee.dao.TaxiOrderDAO
 * @author kstes_000
 * @author Ruslan Gunavardana
 * @author Sharaban Sasha
 */
@Stateless
public class TaxiOrderDAOBean implements TaxiOrderDAO {
    private static final Logger logger = LogManager.getLogger();
    @PersistenceContext(unitName = "HibernatePU")
    private EntityManager entityManager;


    @Override
    public void addComment(TaxiOrderEntity entity) {
        String sql = "INSERT INTO taxi_order (comment) VALUES(?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, entity.getComment());
        query.executeUpdate();
    }
    @Override
    public Integer addOrder(TaxiOrderEntity order) {
        String sql="INSERT INTO taxi_order (description,status,price,user_id,service,car_category,way_of_payment,driver_sex," +
                "music_style,animal_transportation,free_wifi,smoking_driver,air_conditioner) " +
                "VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13)" +
                "RETURNING tracking_number";

        //TODO insert into taxi items
        //     "INSERT INTO taxi_order_item (tracking_numer, path, ordered_quantity, driver_id) VALUES ()"

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1,order.getDescription());
        query.setParameter(2, order.getStatus().toString());
        query.setParameter(3, order.getPrice());
        query.setParameter(4,order.getUserId());
        query.setParameter(5, order.getService().toString());
        query.setParameter(6, order.getCarCategory().toString());
        query.setParameter(7, order.getWayOfPayment().toString());
        query.setParameter(8, order.getDriverSex().toString());
        query.setParameter(9, order.getMusicStyle().toString());
        query.setParameter(10, order.getAnimalTransportation());
        query.setParameter(11, order.getFreeWifi());
        query.setParameter(12, order.getNonSmokingDriver());
        query.setParameter(13,order.getAirConditioner());


        //   Query query2 = entityManager.createNativeQuery("SELECT max(tracking_number) FROM taxi_order", OrderEntity.class);
        return (Integer) query.getSingleResult();
    }
    @Override
    public List<TaxiOrderEntity> getQueuedOrders() {
        return null;
    }

    @Override
    public TaxiOrderEntity getOrder(Integer trackingNumber) {
        return null;
    }

    @Override
    public List<TaxiOrderEntity> getOrdersPerPage(int partNumber) {
        if(partNumber <= 0) {
            logger.error("partNumber can't be <= 0");
            throw new IllegalArgumentException("partNumber can't be <= 0");
        }
        Query query = entityManager.createNativeQuery("SELECT * FROM taxi_order " +
                "ORDER BY ordered_date LIMIT ?1 OFFSET ?2", TaxiOrderEntity.class);
        query.setParameter(1, TO_ORDERS_PER_PAGE);
        query.setParameter(2, (partNumber - 1)*TO_ORDERS_PER_PAGE);
        return query.getResultList();
    }
}
