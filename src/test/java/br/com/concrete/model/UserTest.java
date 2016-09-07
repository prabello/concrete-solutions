package br.com.concrete.model;

import br.com.concrete.controller.UserBuilder;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private User user = new UserBuilder().build();
    @Test
    public void assureThatPasswordIsHashedAfterSettingIt() throws Exception {
        user.setPassword("banana");
        assertTrue(new BCryptPasswordEncoder().matches("banana",user.getPassword()));
    }

    @Test
    public void assureThatUserIsStillLoggedIfLastLoginWasOnTheLastThirtyMinutes() throws Exception {
        user.setLastLogin(Calendar.getInstance());
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void assureThatUserIsNotLoggedIfLastLoginWasLongerThanThirtyMinutes() throws Exception {
        Calendar halfHourAgo = Calendar.getInstance();
        halfHourAgo.add(Calendar.MINUTE,-30);
        user.setLastLogin(halfHourAgo);
        assertFalse(user.isLoggedIn());
    }

}