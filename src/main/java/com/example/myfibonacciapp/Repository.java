package com.example.myfibonacciapp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Repository extends JpaRepository<entity, Long>
{
    Optional<entity> findByIndex(Integer index);
}
