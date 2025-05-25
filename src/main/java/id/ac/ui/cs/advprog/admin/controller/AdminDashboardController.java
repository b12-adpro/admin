package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.service.facade.AdminDashboardFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardFacade adminDashboardFacade;

    @GetMapping("/stats")
    public ResponseEntity<AdminDashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(adminDashboardFacade.getDashboardStats());
    }
}
