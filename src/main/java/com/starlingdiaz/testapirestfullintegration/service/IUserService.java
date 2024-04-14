/**
 * @author Starling Diaz on 4/12/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/12/2024.
 */
package com.starlingdiaz.testapirestfullintegration.service;

import com.starlingdiaz.testapirestfullintegration.dto.LoginDto;
import com.starlingdiaz.testapirestfullintegration.dto.UserDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.LoginResponseDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.UserResponseDto;
import com.starlingdiaz.testapirestfullintegration.entity.User;

public interface IUserService {

    UserResponseDto registerUser(UserDto userDto) throws Exception;
    String generateAndSaveActivationToken(User user);
    String generateActivationCode(int length);
    LoginResponseDto loginAuthenticate(LoginDto loginDto);
}
