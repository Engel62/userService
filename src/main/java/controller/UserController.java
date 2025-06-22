package controller;

import dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API для управления пользователями")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Создать пользователя",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<EntityModel<UserDTO>> createUser(
            @RequestBody @Valid UserDTO userDTO
    ) {
        UserDTO createdUser = userService.createUser(userDTO);
        URI location = linkTo(methodOn(UserController.class).getUser(createdUser.getId())).toUri();

        return ResponseEntity
                .created(location)
                .body(EntityModel.of(createdUser,
                        linkTo(methodOn(UserController.class).getUser(createdUser.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users")));
    }

    @Operation(summary = "Получить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь найден",
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id
    ) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).updateUser(id, null)).withRel("update-user"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete-user"),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users")));
    }

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> getAllUsers() {
        List<EntityModel<UserDTO>> users = userService.getAllUsers().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update"),
                        linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()));
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id,
            @RequestBody @Valid UserDTO userDto
    ) {
        UserDTO updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(EntityModel.of(updatedUser,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users")));
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}