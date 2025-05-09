package id.ac.ui.cs.advprog.admin.dto;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private UserRole role;
    private boolean isBlocked;
}
