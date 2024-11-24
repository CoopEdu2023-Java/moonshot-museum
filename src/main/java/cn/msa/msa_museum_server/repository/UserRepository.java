package cn.msa.msa_museum_server.repository;

import java.util.Optional;
import cn.msa.msa_museum_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
    public boolean existsByUsernameAndPassword(String username, String password);

    public Optional<User> findByUsernameAndPassword(String username, String password);
    public boolean existsByUsername(String username);
} 

// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByUsername(String username);
//     Optional<User> findByUsernameAndPassword(String username, String password);
//     boolean existsByUsername(String username);
//     boolean existsByUsernameAndPassword(String username, String password);
// }