package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Photos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
//avec cette annotation on peut directement créer un end ponit avec le chemin sans méme devoir créer un controleur part2 /45:18
public interface PhotosRepository extends JpaRepository<Photos,Long> {
}
