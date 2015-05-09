package ua.com.tracksee.logic.reports;

import ua.com.tracksee.dao.TaxiOrderDAO;
import ua.com.tracksee.dao.UserDAO;
import ua.com.tracksee.entities.ServiceUserEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean provides prepare data
 * for ExcelReporterBean
 *
 * @author Sharaban Sasha
 */
@Stateless
public class TestComplicateReportBean {
    @EJB
    private
    UserDAO userDAO;
    private String reportTitle="ComplicateReport";

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public ArrayList<String> getTitles(){
       ArrayList<String> titles=new ArrayList<String>();
        titles.add("User Id");
        titles.add("Phone number");
        titles.add("Email");
        titles.add("Ignored times");
        titles.add("Registered");
        return titles;
    }
    public ArrayList<ArrayList<String>> getData(){
        ArrayList<ArrayList<String>> dataArray=new ArrayList<ArrayList<String>>();
        ArrayList<String> data;

        List<ServiceUserEntity> serviceUserEntityList= userDAO.getUnregisteredUsers();
        for (int i = 0; i <serviceUserEntityList.size() ; i++) {
            data=new ArrayList<String>();
            data.add(String.valueOf(serviceUserEntityList.get(i).getUserId()));
            data.add(serviceUserEntityList.get(i).getPhone());
            data.add(serviceUserEntityList.get(i).getEmail());
            data.add(String.valueOf(serviceUserEntityList.get(i).getIgnoredTimes()));
            data.add(String.valueOf(serviceUserEntityList.get(i).getActivated()));
            dataArray.add(data);
        }
        return dataArray;
    }
}
