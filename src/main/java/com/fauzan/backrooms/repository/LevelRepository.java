package com.fauzan.backrooms.repository;

import com.fauzan.backrooms.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    @Override
    <S extends Level> S save(S entity);
    Level findByName(String s);
    Optional<Level> findByUrl(String s);
}
