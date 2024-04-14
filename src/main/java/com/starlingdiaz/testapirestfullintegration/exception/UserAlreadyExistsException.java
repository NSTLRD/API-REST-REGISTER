/**
 * @author Starling Diaz on 4/11/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/11/2024.
 */

package com.starlingdiaz.testapirestfullintegration.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
