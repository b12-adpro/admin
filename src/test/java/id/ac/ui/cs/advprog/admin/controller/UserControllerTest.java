package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testBlockUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setBlocked(true);

        when(userService.blockUser(1L)).thenReturn(user);

        mockMvc.perform(put("/api/admin/users/1/block"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.blocked").value(true));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/admin/users/1"))
                .andExpect(status().isNoContent());
    }
}
