package com.sboot.springlearn.data.repository;
//Interface for guest repository
import com.sboot.springlearn.data.entity.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends CrudRepository<Guest,Long> {
}
