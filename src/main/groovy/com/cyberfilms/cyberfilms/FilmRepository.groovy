package com.cyberfilms.cyberfilms

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "films", path = "films")
interface FilmRepository extends MongoRepository<Film, String> {

    Optional<Film> findByProjectName(@Param("projectName") String projectName)

    Film save(Film entity)
}
