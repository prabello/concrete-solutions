package br.com.concrete.model;

import br.com.concrete.controller.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    private User user;

    @Before
    public void setUp(){
        user = new UserBuilder().build();
    }

    @Test
    public void ensureThatPasswordIsHashedAfterSettingIt() throws Exception {
        user.setPassword("banana");
        assertTrue(new BCryptPasswordEncoder().matches("banana",user.getPassword()));
    }

    @Test
    public void ensureThatPasswordStillMathOriginal(){
        user.setPassword("banana");
        assertTrue(user.isPasswordEquals("banana"));
    }

    @Test
    public void ensureThatUserIsStillLoggedIfLastLoginWasOnTheLastThirtyMinutes() throws Exception {
        user.setLastLogin(Calendar.getInstance());
        assertTrue(user.isLoggedIn());
    }

    @Test
    public void ensureThatUserIsNotLoggedIfLastLoginWasLongerThanThirtyMinutes() throws Exception {
        Calendar halfHourAgo = Calendar.getInstance();
        halfHourAgo.add(Calendar.MINUTE,-30);
        user.setLastLogin(halfHourAgo);
        assertFalse(user.isLoggedIn());
    }

}