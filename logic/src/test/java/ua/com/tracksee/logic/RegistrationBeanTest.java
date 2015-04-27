package ua.com.tracksee.logic;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.postgresql.geometric.PGpoint;
import org.postgresql.util.PGBinaryObject;
import ua.com.tracksee.dao.UserDAO;
import ua.com.tracksee.dao.postrgresql.UserDAOBean;
import ua.com.tracksee.entities.ServiceUserEntity;
import ua.com.tracksee.logic.exception.RegistrationException;

import javax.ejb.EJB;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ruslan Gunavardana
 */
public class RegistrationBeanTest {

    private @EJB RegistrationBean registrationBean;
    private @EJB UserDAO userDAO;

    @Deployment
    public	static WebArchive createTestArchive(){
        File[] log4jApi = getLibraryFromMaven("org.apache.logging.log4j","log4j-api","2.2");
        File[] log4jCore = getLibraryFromMaven("org.apache.logging.log4j", "log4j-core", "2.2");
        File[] hibernateLib = getLibraryFromMaven("org.hibernate", "hibernate-core", "4.3.9.Final");
        return	ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(log4jApi)
                .addAsLibraries(log4jCore)
                .addAsLibraries(hibernateLib)
                .addPackage(PGpoint.class.getPackage())
                .addPackage(PGBinaryObject.class.getPackage())
                .addPackage(ServiceUserEntity.class.getPackage())
                .addPackage(UserDAOBean.class.getPackage())
                .addPackage(UserDAO.class.getPackage())
                .addPackage(RegistrationBean.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    /**
     *  Method is used for getting all library files from maven repo
     */
    private static File[] getLibraryFromMaven(String groupId, String artifactId, String version){
        return Maven.resolver().resolve(groupId + ":" + artifactId + ":" + version)
                .withTransitivity().asFile();
    }

    private static final String TEST_EMAIL = "rusan.rus@gmail.com";
    private static final String TEST_PASSWORD = "very@Secure";
    private static final String TEST_PHONE = "+380635005050";

    @Test(expected = RegistrationException.class)
    public void testRegisterBadCustomerUser() throws Exception {
        registrationBean.registerCustomerUser("badmail@", "nonsecurepassword", null);
    }

    @Test
    public void testRegisterGoodCustomerUser() throws Exception {
        clearUserIfExists(TEST_EMAIL);
        registrationBean.registerCustomerUser(TEST_EMAIL, TEST_PASSWORD, TEST_PHONE);
        ServiceUserEntity newUser = userDAO.getUserByEmail(TEST_EMAIL);
        assertFalse(newUser.getActivated());
        userDAO.deleteUser(newUser.getUserId());
    }

    @Test
    public void testRegisterAndActivate() throws Exception {
        ServiceUserEntity unactivatedUser;
        ServiceUserEntity activatedUser;

        clearUserIfExists(TEST_EMAIL);
        registrationBean.registerCustomerUser(TEST_EMAIL, TEST_PASSWORD, TEST_PHONE);
        unactivatedUser = userDAO.getUserByEmail(TEST_EMAIL);
        assertFalse(unactivatedUser.getActivated());

        registrationBean.activateCustomerUserAccount(unactivatedUser.getUserId().toString());
        activatedUser = userDAO.getUserByEmail(TEST_EMAIL);
        assertTrue(activatedUser.getActivated());
    }

    private void clearUserIfExists(String email) {
        ServiceUserEntity oldUser = userDAO.getUserByEmail(email);
        if (oldUser != null) {
            userDAO.deleteUser(oldUser.getUserId());
        }
    }
}