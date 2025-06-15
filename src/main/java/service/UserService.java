package service;

import dto.UserDTO;
import dto.UserEvent;
import exeption.EmailAlreadyExistsException;
import exeption.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.UserMapper;
import model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.util.List;

import static dto.UserEvent.EventType.CREATED;
import static dto.UserEvent.EventType.DELETED;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String kafkaTopic;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);

        sendUserEvent(user.getEmail(), CREATED);
        log.info("Created user with email: {}", user.getEmail());

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userMapper.updateUserFromDTO(userDTO, existingUser);
        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
        sendUserEvent(user.getEmail(), DELETED);
        log.info("Deleted user with id: {}", id);
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void sendUserEvent(String email, UserEvent.EventType eventType) {
        UserEvent event = new UserEvent(email, eventType);
        kafkaTemplate.send(kafkaTopic, email, event)
                .addCallback(
                        result -> log.debug("Sent event: {}", event),
                        ex -> log.error("Failed to send event: {}", event, ex)
                );
    }
    }