package com.cygnus.jett.repository;

import com.cygnus.jett.repository.entities.JettScoreRankingEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JettScoreRankingRepository extends CrudRepository<JettScoreRankingEntity,String> {

    @NotNull
    List<JettScoreRankingEntity> findAll();
}
