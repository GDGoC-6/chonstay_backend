package com.webproject.chonstay_backend.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

    Optional<Home> findByUserIdAndHomeId(Long userId, Long homeId);

}
