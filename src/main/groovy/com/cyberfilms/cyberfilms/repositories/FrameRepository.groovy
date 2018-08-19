package com.cyberfilms.cyberfilms.repositories

import com.cyberfilms.cyberfilms.entities.Frame
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "frames", path = "frames")
interface FrameRepository extends MongoRepository<Frame, String> {

    Optional<Frame[]> findByName(@Param("name") String name)

    Frame save(Frame entity)
}
