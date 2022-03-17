package com.example.agileboardsserver.repository;

import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByUser(User user);

    void deleteById(UUID id);

    @Modifying
    @Query(
            value = "UPDATE project p " +
                    "SET p.name = :name " +
                    "WHERE p.project_id = :projectId",
            nativeQuery = true)
    Integer updateNameById(@Param("projectId") UUID id, @Param("name") String name);

    @Modifying
    @Query(
            value = "UPDATE project p " +
                    "SET p.description = :description " +
                    "WHERE p.project_id = :projectId",
            nativeQuery = true)
    Integer updateDescriptionById(@Param("projectId") UUID id, @Param("description") String description);
}
