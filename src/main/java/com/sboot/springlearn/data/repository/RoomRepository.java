package com.sboot.springlearn.data.repository;
// Interface for room repository
import com.sboot.springlearn.data.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {
}
