package id.ac.ui.cs.advprog.admin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;
    private Object dummyUser1;
    private Object dummyUser2;

    @BeforeEach
    void setUp() throws Exception {
        var userService = mock(Class.forName("id.ac.ui.cs.advprog.admin.service.UserService"));

        // Create dummy user objects using UserDTO builder via reflection
        Class<?> userDTOClass = Class.forName("id.ac.ui.cs.advprog.admin.dto.UserDTO");
        Method builderMethod = userDTOClass.getMethod("builder");
        Object builder = builderMethod.invoke(null);

        Method setId = builder.getClass().getMethod("id", Long.class);
        Method setName = builder.getClass().getMethod("name", String.class);
        Method setRole = builder.getClass().getMethod("role", Class.forName("id.ac.ui.cs.advprog.admin.enums.UserRole"));
        Method setBlocked = builder.getClass().getMethod("isBlocked", boolean.class);
        Method build = builder.getClass().getMethod("build");

        Class<?> userRole = Class.forName("id.ac.ui.cs.advprog.admin.enums.UserRole");
        Object fundraiser = Enum.valueOf((Class<Enum>) userRole, "FUNDRAISER");
        Object donatur = Enum.valueOf((Class<Enum>) userRole, "DONATUR");

        Object builder1 = setBlocked.invoke(setRole.invoke(setName.invoke(setId.invoke(builder, 1L), "Andi"), fundraiser), false);
        dummyUser1 = build.invoke(builder1);

        Object builder2 = setBlocked.invoke(setRole.invoke(setName.invoke(setId.invoke(builder, 2L), "Siti"), donatur), false);
        dummyUser2 = build.invoke(builder2);

        // Mocking service responses
        when(userService.getClass().getMethod("getAllUsers").invoke(userService))
                .thenReturn(Arrays.asList(dummyUser1, dummyUser2));

        when(userService.getClass().getMethod("getUserById", Long.class).invoke(userService, 1L))
                .thenReturn(Optional.of(dummyUser1));

        when(userService.getClass().getMethod("blockUser", Long.class).invoke(userService, 2L))
                .thenReturn(dummyUser2);

        // Setup controller with mocked service
        userController = new UserController((id.ac.ui.cs.advprog.admin.service.UserService) userService);
    }

    @Test
    void testGetAllUsers() {
        ResponseEntity<?> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        List<?> body = (List<?>) response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
    }

    @Test
    void testGetUserById() throws Exception {
        ResponseEntity<?> response = userController.getUserById(1L);
        assertEquals(200, response.getStatusCodeValue());
        Object user = response.getBody();
        assertNotNull(user);

        Method getName = user.getClass().getMethod("getName");
        assertEquals("Andi", getName.invoke(user));
    }

    @Test
    void testBlockUser() throws Exception {
        ResponseEntity<?> response = userController.blockUser(2L);
        assertEquals(200, response.getStatusCodeValue());
        Object user = response.getBody();
        assertNotNull(user);
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
