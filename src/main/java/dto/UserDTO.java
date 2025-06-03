package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank(message = "Имя является обязательным")
    private String name;
    @Email(message = "Email должен быть действительным")
    @NotBlank(message = "Email является обязательным")
    private String email;
    @Min(value = 1, message = "Возраст должен быть больше 0")
    private Integer age;
    private LocalDateTime createdAt;
}
