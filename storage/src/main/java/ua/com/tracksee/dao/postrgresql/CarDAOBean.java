package ua.com.tracksee.dao.postrgresql;

import ua.com.tracksee.dao.CarDAO;
import ua.com.tracksee.entities.CarEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author KatiaStetsiuk
 */

public class CarDAOBean implements CarDAO {
    @PersistenceContext(unitName = "HibernatePU")
    private EntityManager entityManager;

    @Override
    public void createCar(CarEntity carEntity) {
        String sql = "INSERT INTO car (car_model, color, car_category, " +
                "animal_transportation_applicable, free_wifi, air_conditioner " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, carEntity.getCarModel());
        query.setParameter(2, carEntity.getCarCategory());
        query.setParameter(3, carEntity.getAnimalTransportationApplicable());
        query.setParameter(4, carEntity.getFreeWifi());
        query.setParameter(5, carEntity.getAirConditioner());
        query.executeUpdate();
    }

    @Override
    public void updateCar(CarEntity carEntity) {
        String sql = "UPDATE car SET car_model = ?, car_category = ? ,animal_transportation = ?, " +
                "free_wifi = ? , air_conditioner = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, carEntity.getCarCategory());
        query.setParameter(2, carEntity.getCarCategory());
        query.setParameter(3, carEntity.getAnimalTransportationApplicable());
        query.setParameter(4, carEntity.getFreeWifi());
        query.setParameter(5, carEntity.getAirConditioner());
        query.executeUpdate();
    }

    @Override
    public void deleteCar(CarEntity carEntity) {
        String sql = "DELETE from car WHERE car_id = " + carEntity.getCarNumber();
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}