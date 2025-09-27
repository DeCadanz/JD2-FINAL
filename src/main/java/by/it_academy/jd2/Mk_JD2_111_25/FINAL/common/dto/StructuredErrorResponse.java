package by.it_academy.jd2.Mk_JD2_111_25.FINAL.common.dto;

import lombok.Data;
import java.util.List;

@Data
public class StructuredErrorResponse {
    private String logref = "structured_error";
    private List<FieldError> fields;

    public StructuredErrorResponse(List<FieldError> fields) {
        this.fields = fields;
    }

    @Data
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
