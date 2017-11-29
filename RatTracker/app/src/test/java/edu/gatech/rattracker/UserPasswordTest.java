package edu.gatech.rattracker;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
Matthew Kaufer
 */
public class UserPasswordTest {
    // tests whether or not BackendManager.validateUserPassword works as expected

    @Test
    public void verify_normal_user_normal_password() throws Exception {
        String username = "ValidUsername";
        String password = "Sufficiently Long Password";
        boolean status = BackendManager.validateUserPassword(username, password, null);
        assertTrue("Failed verification for normal user/password combo", status);
    }

    @Test
    public void verify_normal_user_short_password() throws Exception {
        String username = "ValidUsername";
        String password = "short";

        boolean status = BackendManager.validateUserPassword(username, password, null);
        assertFalse("Should have returned false, because password was too short", status);
    }

    @Test
    public void verify_empty_user_normal_password() throws Exception {
        String username = "";
        String password = "This is Fine";

        boolean status = BackendManager.validateUserPassword(username, password, null);
        assertFalse("Should have returned false, because username was empty", status);
    }

    @Test
    public void verify_empty_user_short_password() throws Exception {
        String username = "";
        String password = "short";

        boolean status = BackendManager.validateUserPassword(username, password, null);
        assertFalse("Should have returned false, because username was empty and password was too short", status);
    }
}
