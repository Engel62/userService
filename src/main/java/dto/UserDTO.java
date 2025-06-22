package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Relation(collectionRelation = "users", itemRelation = "user")
@Schema(description = "DTO для представления пользователя")
public class UserDTO extends RepresentationModel<UserDTO> {
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @Schema(description = "Имя пользователя", example = "Иван Иванов", required = true)
    private String name;

    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не может быть пустым")
    @Schema(description = "Email пользователя", example = "ivan@example.com", required = true)
    private String email;

    @Schema(description = "Статус активности пользователя", example = "true")
    private boolean isActive;
}
