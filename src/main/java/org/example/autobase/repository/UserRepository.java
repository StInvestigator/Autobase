package org.example.autobase.repository;


import org.example.autobase.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("from AppUser a where a.userName = ?1")
    Optional<AppUser> findUserAccount(String userName);

}
