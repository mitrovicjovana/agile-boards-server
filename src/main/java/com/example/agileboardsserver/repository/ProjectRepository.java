package com.example.agileboardsserver.repository;

import com.example.agileboardsserver.model.Project;
import com.example.agileboardsserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByUser(User user);

    Optional<Project> findById(UUID id);

    void deleteById(UUID id);

    @Query(
            value = "SELECT * " +
                    "FROM project p " +
                    "WHERE p.name " +
                    "LIKE %:searchTerm% " +
                    "AND p.owner_username = :username",
            nativeQuery = true
    )
    List<Project> findProjectsBySearchTerm(@Param("searchTerm") String searchTerm, @Param("username") String username);

    @Modifying
    @Query(
            value = "UPDATE project p " +
                    "SET p.name = :name " +
                    "WHERE p.project_id = :projectId",
            nativeQuery = true)
    Integer updateNameById(@Param("projectId") UUID id, @Param("name") String name);

}
