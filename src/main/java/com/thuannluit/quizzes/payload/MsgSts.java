package com.thuannluit.quizzes.payload;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgSts {
    @NotBlank
    private String code;
    @NotBlank
    private String message;
    @NotBlank
    private List<String> listMessage;

}
