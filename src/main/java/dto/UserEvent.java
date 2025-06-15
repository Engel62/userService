package dto;

public record UserEvent(
        String email,
        EventType eventType
) {
    public enum EventType { CREATED, DELETED }
}
