package com.example.mettleflaggr.dao;

import com.example.mettleflaggr.model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlagRepository extends JpaRepository<Flag, Long> {

    @Query("""
        select f
        from Flag f
        left join f.users uf
        left join uf.user u
        where (f.state = true and u.username is null)
        or (uf.state = true and u.username = :username)
    """)
    List<Flag> findActiveFlagNamesByUsername(@Param("username") String username);

    Optional<Flag> findByName(String name);
}
