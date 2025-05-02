package id.ac.ui.cs.advprog.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetDashboardStats() throws Exception {
        mockMvc.perform(get("/admin/dashboard/stats"))
                .andExpect(status().isOk());
        // Jika ingin cek isi responsenya, bisa tambahkan jsonPath di sini
        //.andExpect(jsonPath("$.totalUsers").value(10));
    }
}
