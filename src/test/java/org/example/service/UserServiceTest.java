package org.example.service;

import mock.UserRepositoryMock;
import org.example.model.User;
import org.example.repository.dao.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private List<User> users;
    private UserService usersService;
    @BeforeEach
    void setUp() {
        users = List.of(User.builder().nickname("dasha1").password("qwerty").build());
        usersService = new UserService(new UserRepositoryMock(users));
    }

    @AfterEach
    void tearDown() {
       usersService = null;
    }

    @Test
    void testIsFindUserPresent() {
        String nickname = "dasha1";
        String password = "qwerty";

        User result = usersService.findUser(nickname, password);
        String expectedNickname = result.getNickname();
        assertEquals(nickname, expectedNickname);

        String expectedPassword = result.getPassword();
        assertEquals(password, expectedPassword);
    }
    @Test
    void testFindUserIsNonAvailable() {
        String nickname = "dasha";
        String password = "qwerty";

        User actual = usersService.findUser(nickname, password);
        User expected = User.builder().build();
        assertEquals(expected, actual);
    }

    @Test
    void updateAccount() {
    }

    @Test
    void save() {
        User expected = users.get(0);
        User actual = usersService.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void testIsNicknamePresent() {
        String nickname = "dasha1";
        boolean expected = usersService.nicknameIsPresent(nickname);
        assertTrue(expected);
    }

    @Test
    void testNicknameNotPresent() {
        String nickname = "dasha";
        boolean expected = usersService.nicknameIsPresent(nickname);
        assertFalse(expected);
    }

    @Test
    void addGameToUserAccount() {

    }
}