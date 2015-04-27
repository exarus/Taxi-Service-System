package ua.com.tracksee.dao;

import ua.com.tracksee.entities.TaxiOrderEntity;

import javax.ejb.Local;

/**
 * Interface for persisting and accessing taxi order data.
 *
 * @author kstes_000
 * @author Ruslan Gunavardana
 */
@Local
public interface TaxiOrderDAO {
    public void addComment(TaxiOrderEntity taxiOrderEntity);

    Long addOrder(TaxiOrderEntity taxiOrderEntity);
}
