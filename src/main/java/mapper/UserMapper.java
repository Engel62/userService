package mapper;

import dto.UserDTO;

@Mapper(componentModel="spring")
public interface UserMapper {
    UsserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDTO(UserDTO userDTO, @MappingTarget User user);


}
