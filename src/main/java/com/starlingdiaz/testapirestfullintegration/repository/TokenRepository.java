/**
 * @author Starling Diaz on 4/12/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/12/2024.
 */
package com.starlingdiaz.testapirestfullintegration.repository;

import com.starlingdiaz.testapirestfullintegration.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
}
