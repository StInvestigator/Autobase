package org.example.autobase.repository;

import org.example.autobase.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("Select ur.role.name from UserRole ur where ur.appUser.id = ?1")
    List<String> getRoleNames(Long userId);

}
