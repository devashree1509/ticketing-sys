package com.devashree.ticketing.repository;
 import com.devashree.ticketing.entity.User;
 import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


}
