package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String logref = "error";
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}