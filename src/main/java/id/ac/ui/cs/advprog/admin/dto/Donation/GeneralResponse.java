package id.ac.ui.cs.advprog.admin.dto.Donation;

import lombok.Data;

@Data
public class GeneralResponse<T> {
    private String status;
    private String message;
    private T data;
}
