package ua.com.tracksee.logic.customer;

import ua.com.tracksee.dao.FavoritePlaceDAO;
import ua.com.tracksee.entities.FavoritePlaceEntity;
import ua.com.tracksee.entities.FavoritePlaceEntityPK;
import ua.com.tracksee.dto.FavoritePlaceDTO;
import ua.com.tracksee.dto.Location;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

import static ua.com.tracksee.util.GeometryConverter.pointToLocation;
import static ua.com.tracksee.util.GeometryConverter.locationToPoint;

/**
 * @author Ruslan Gunavardana
 */
@Stateless
public class FavoritePlacesBean {
    private @EJB
    FavoritePlaceDAO favoritePlaceDAO;

    /**
     * Returns a list of customer user's favourite addresses.
     */
    public List<FavoritePlaceDTO> getFavoritePlacesFor(int userId) {
        List<FavoritePlaceEntity> entities = favoritePlaceDAO.getAddressesByUserId(userId);
        List<FavoritePlaceDTO> dtoList = new ArrayList<>(entities.size());

        // filling dtoList
        for (FavoritePlaceEntity entity : entities) {
            Location location = pointToLocation(entity.getLocation());
            FavoritePlaceDTO placeDTO = new FavoritePlaceDTO(entity.getName(), location);
            dtoList.add(placeDTO);
        }

        return dtoList;
    }

    /**
     * Saves favorite place for customer user.
     *
     * @param userId customer user id
     * @param favoritePlaceDto favorite place to save
     * @return true if successfully saved, false if not
     */
    public boolean addFavoritePlaceFor(Integer userId, FavoritePlaceDTO favoritePlaceDto) {
        String name = favoritePlaceDto.getName();
        Location loc = favoritePlaceDto.getLocation();
        return favoritePlaceDAO.addAddress(new FavoritePlaceEntity(name, userId, locationToPoint(loc)));
    }

    /**
     * Removes favourite place for customer user.
     *
     * @param userId customer user id
     * @param name place name
     * @return true if successfully removed, false if not
     */
    public boolean removeFavoritePlaceFor(Integer userId, String name) {
        return favoritePlaceDAO.deleteAddress(new FavoritePlaceEntityPK(name, userId));
    }

    /**
     * Updates favorite place for customer user.
     *
     * @param userId customer user id
     * @param oldName old place name
     * @param newData new place data transfer object
     * @return true if successfully updated, false if not
     */
    public boolean updateFavoritePlaceFor(Integer userId, String oldName, FavoritePlaceDTO newData) {
        Location loc = newData.getLocation();
        FavoritePlaceEntity newEntity = new FavoritePlaceEntity(newData.getName(), userId, locationToPoint(loc));
        return favoritePlaceDAO.updateAddress(new FavoritePlaceEntityPK(oldName, userId), newEntity);
    }
}
