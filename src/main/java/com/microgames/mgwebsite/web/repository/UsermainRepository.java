//Paquete del Website.
package com.microgames.mgwebsite.web.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.microgames.mgwebsite.web.entities.Usermain;

public interface UsermainRepository extends JpaRepository<Usermain, Long> {
    Optional<Usermain> findByUsername(String username);

    Optional<Usermain> findByEmail(String email);

    Optional<Usermain> findByUsernameIgnoreCase(String username);

    Optional<Usermain> findByEmailIgnoreCase(String email);

}
