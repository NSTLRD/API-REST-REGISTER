/**
 * @author Starling Diaz on 4/12/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/12/2024.
 */

package com.starlingdiaz.testapirestfullintegration.dto.response;

import com.starlingdiaz.testapirestfullintegration.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponseDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

    public UserResponseDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.created = user.getCreated();
            this.modified = user.getModified();
            this.lastLogin = user.getLastLogin();
            this.token = user.getToken();
            this.isActive = user.isActive();
        }
    }

    public UserResponseDto(UUID userId, LocalDateTime created, LocalDateTime modified, LocalDateTime lastLogin, String token, boolean isActive) {
        this.id = userId;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
    }
}
