package com.example.mettleflaggr.dao;

import com.example.mettleflaggr.model.UserFlag;
import com.example.mettleflaggr.model.UserFlagKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFlagRepository extends JpaRepository<UserFlag, UserFlagKey> {

    Optional<UserFlag> findByUserUsernameAndFlagName(String username, String flag);

}
