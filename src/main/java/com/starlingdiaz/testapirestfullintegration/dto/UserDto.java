/**
 * @author Starling Diaz on 4/11/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/11/2024.
 */

package com.starlingdiaz.testapirestfullintegration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Email(message = "El correo no tiene un formato válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Formato de correo no válido")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, incluyendo números y letras")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    private Set<PhoneDto> phones;
}

