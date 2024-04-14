/**
 * @author Starling Diaz on 4/12/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/12/2024.
 */

package com.starlingdiaz.testapirestfullintegration.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class PhoneDto {
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String number;
    @NotBlank(message = "El código de ciudad no puede estar vacío")
    private String citycode;
    @NotBlank(message = "El código de país no puede estar vacío")
    private String countrycode;

    public PhoneDto(String number, String citycode, String countrycode) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }
}
