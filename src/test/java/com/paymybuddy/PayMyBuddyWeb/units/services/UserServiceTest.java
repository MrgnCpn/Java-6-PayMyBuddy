package com.paymybuddy.PayMyBuddyWeb.units.services;

import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.FriendDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.dao.UserDAOInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.SecurityServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.interfaces.service.UserServiceInterface;
import com.paymybuddy.PayMyBuddyWeb.models.Account;
import com.paymybuddy.PayMyBuddyWeb.models.Country;
import com.paymybuddy.PayMyBuddyWeb.models.Currency;
import com.paymybuddy.PayMyBuddyWeb.models.User;
import com.paymybuddy.PayMyBuddyWeb.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private  UserServiceInterface userService;

    private static Map<String, Object> userInfo;
    private static Map<String, Object> requestParam;
    private static Map<String, Object> incompleteParams;
    private static User user;

    @Mock
    private  HttpSession session;

    @Mock
    private  UserDAOInterface userDAO;

    @Mock
    private  SecurityServiceInterface securityService;

    @Mock
    private  FriendDAOInterface friendDAO;

    @BeforeAll
    static void init_all() throws IOException {
        userInfo = new HashMap<>();
        userInfo.put("userID", 1);

        requestParam = new HashMap<>();
        requestParam.put("firstname", "John");
        requestParam.put("lastName", "Smith");
        requestParam.put("email", "john.smith@email.com");
        requestParam.put("age_year", "2000");
        requestParam.put("age_month", "02");
        requestParam.put("age_day", "01");
        requestParam.put("country", "USA");

        incompleteParams = new HashMap<>();
        incompleteParams.put("firstname", "");
        incompleteParams.put("lastName", "");
        incompleteParams.put("email", "");
        incompleteParams.put("age_year", "");
        incompleteParams.put("age_month", "");
        incompleteParams.put("age_day", "");
        incompleteParams.put("country", "");

        user = new User(
                1,
                "John",
                "Smith",
                LocalDate.of(2000, 02, 01),
                "john.smith@email.com",
                new Country("USA"),
                null
        );

        user.setAccount(new Account(1, 1000.00, new Currency("USD"), LocalDate.now()));
    }

    @BeforeEach
    void init_each() {
        userService = new UserService(securityService, userDAO, friendDAO);
    }

    @Tag("UserServiceTest")
    @Test
    void getUser_bySession_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(userDAO.getUserById(anyInt())).thenReturn(user);

        assertThat(userService.getUser(session)).isEqualTo(user);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, times(1)).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getUser_bySession_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        assertThat(userService.getUser(session)).isNull();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, never()).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getUser_byId_test() {
        when(userDAO.getUserById(anyInt())).thenReturn(user);
        assertThat(userService.getUser(1)).isEqualTo(user);
        verify(userDAO, times(1)).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getUser_byId_idNull_test() {
        assertThat(userService.getUser((Integer) null)).isNull();
        verify(userDAO, never()).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getUser_byId_idEqualZero_test() {
        assertThat(userService.getUser(0)).isNull();
        verify(userDAO, never()).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getUserFriends_test() {
        List<Integer> friendsIdList = new ArrayList<>();
        friendsIdList.add(2);
        friendsIdList.add(3);

        user.setFriends(friendsIdList);

        when(userDAO.getUserById(anyInt())).thenReturn(new User());

        List<User> friendsList = userService.getUserFriends(user);

        assertThat(friendsList).isNotNull();
        assertThat(friendsList.size()).isEqualTo(2);

        verify(userDAO, times(2)).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getUserFriends_noFriends_test() {
        user.setFriends(null);

        assertThat(userService.getUserFriends(user)).isNull();
        verify(userDAO, never()).getUserById(anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void updateUserProfile_test() throws IOException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.updateUserProfile(session, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, times(1)).updateUser(any(User.class));
    }

    @Tag("UserServiceTest")
    @Test
    void updateUserProfile_sessionNull_test() throws IOException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        userService.updateUserProfile(session, requestParam);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, never()).updateUser(any(User.class));
    }

    @Tag("UserServiceTest")
    @Test
    void updateUserProfile_incompleteParams_test() throws IOException {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.updateUserProfile(session, incompleteParams);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, never()).updateUser(any(User.class));
    }

    @Tag("UserServiceTest")
    @Test
    void getIfAreFriends_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(friendDAO.areFriends(anyInt(), anyInt())).thenReturn(true);

        assertThat(userService.getIfAreFriends(session, 2)).isTrue();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, times(1)).areFriends(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getIfAreFriends_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        assertThat(userService.getIfAreFriends(session, 2)).isFalse();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).areFriends(anyInt(), anyInt());
    }


    @Tag("UserServiceTest")
    @Test
    void getIfAreFriends_friendIdNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        assertThat(userService.getIfAreFriends(session, null)).isFalse();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).areFriends(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void getIfAreFriends_friendEqualZero_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        assertThat(userService.getIfAreFriends(session, 0)).isFalse();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).areFriends(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void removeFriend_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.removeFriend(session, 2);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, times(1)).removeFriendFromUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void removeFriend_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        userService.removeFriend(session, 2);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).removeFriendFromUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void removeFriend_friendIdNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.removeFriend(session, null);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).removeFriendFromUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void removeFriend_friendIdEqualZero_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.removeFriend(session, 0);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).removeFriendFromUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void addFriend_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.addFriend(session, 2);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, times(1)).addNewFriendToUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void addFriend_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        userService.addFriend(session, 2);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).addNewFriendToUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void addFriend_friendIdNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.addFriend(session, null);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).addNewFriendToUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void addFriend_friendIdEqualZero_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        userService.addFriend(session, 0);

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(friendDAO, never()).addNewFriendToUser(anyInt(), anyInt());
    }

    @Tag("UserServiceTest")
    @Test
    void searchContactUsers_test() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);
        when(userDAO.searchUsers(anyInt(), anyString())).thenReturn(userList);

        String search = userService.searchContactUsers(session, "ju");

        assertThat(search).isNotNull();
        assertThat(search).isNotEmpty();
        assertThat(search).isNotBlank();

        verify(securityService, times(2)).getUserInfoFromJWT(session);
        verify(userDAO, times(1)).searchUsers(anyInt(), anyString());
    }

    @Tag("UserServiceTest")
    @Test
    void searchContactUsers_sessionNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(null);

        assertThat(userService.searchContactUsers(session, "ju")).isNull();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, never()).searchUsers(anyInt(), anyString());
    }

    @Tag("UserServiceTest")
    @Test
    void searchContactUsers_searchNull_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        assertThat(userService.searchContactUsers(session, null)).isNull();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, never()).searchUsers(anyInt(), anyString());
    }

    @Tag("UserServiceTest")
    @Test
    void searchContactUsers_searchEmpty_test() {
        when(securityService.getUserInfoFromJWT(session)).thenReturn(userInfo);

        assertThat(userService.searchContactUsers(session, "")).isNull();

        verify(securityService, times(1)).getUserInfoFromJWT(session);
        verify(userDAO, never()).searchUsers(anyInt(), anyString());
    }

    @AfterEach
    void reset_each() {
        userService = null;
    }

    @AfterAll
    static void reset_All() {
        userInfo = null;
        requestParam = null;
        incompleteParams = null;
        user = null;
    }
}