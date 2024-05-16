package io.github.arturtcs.model.dto;

import io.github.arturtcs.model.Car;
import lombok.Builder;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Builder
public record UserTokenDTO(String firstName, String lastName, String login, String email, Date birthday, String phone, List<Car> cars, Instant createdAt, Instant lastLogin) {
}
