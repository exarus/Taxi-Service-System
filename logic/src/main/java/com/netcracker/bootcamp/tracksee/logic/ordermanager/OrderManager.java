package com.netcracker.bootcamp.tracksee.logic.ordermanager;


import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import com.netcracker.bootcamp.tracksee.entity.Address;
import com.netcracker.bootcamp.tracksee.entity.Role;
import com.netcracker.bootcamp.tracksee.entity.TaxiOrder;
import com.netcracker.bootcamp.tracksee.entity.User;

/**
 * @author Sasha Avlasov
 * Session Bean implementation class OrderRegistrator
 */
@Singleton
@LocalBean
//TODO codereview(akymov vadym): Sasha, you have used ejb 3.0
//TODO you need to remove interface and write one class with @Stateless and @Local
//TODO we use ejb 3.2
@Stateless
@Local
public class OrderManager {

    /**
     * Default constructor. 
     */
    public OrderManager() {
        // TODO Auto-generated constructor stub
    }
	public boolean makeOrder(Address destination,Address origin,long phone){
    	if(!checkPhone(phone)){
    	User user = new User();
    	TaxiOrder order = new TaxiOrder();
    	user.setRole(Role.NOT_REGISTER_USER);
    	user.setPhone(phone);
    	//TODO order.setCustomer(user);
    	order.setOrigin(origin);
    	order.setDestination(destination);
    	if(checkBlackList(phone)){
    		order.incruasePrice();
    	}
    	return true;
    	}
    	else return false;
    }
    private boolean checkPhone(long phone){
    	/**
    	 * check phone in DB if exist 
    	 * return true;
    	 */
    	return false;
    }
    private boolean checkBlackList(long phone){
    	/**
    	 * Check phone in black list 
    	 * if find return true;
    	 * 
    	 */
    	return false;
    }

}
