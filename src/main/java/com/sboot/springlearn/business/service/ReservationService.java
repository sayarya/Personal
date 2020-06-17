package com.sboot.springlearn.business.service;

import com.sboot.springlearn.business.domain.RoomReservation;
import com.sboot.springlearn.data.entity.Guest;
import com.sboot.springlearn.data.entity.Reservation;
import com.sboot.springlearn.data.entity.Room;
import com.sboot.springlearn.data.repository.GuestRepository;
import com.sboot.springlearn.data.repository.ReservationRepository;
import com.sboot.springlearn.data.repository.RoomRepository;
import groovy.lang.GroovyShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ReservationService {

    @Autowired
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;


    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    // Used to populate roomReservation
    public List<RoomReservation> getGuestNameForDate(Date date) throws IOException {
        Iterable<Guest> guests = this.guestRepository.findAll();
        Map<Long,RoomReservation> guestReservationMap=new HashMap();
        guests.forEach(guest ->{
            RoomReservation guestReservation = new RoomReservation();
            guestReservation.setGuestId(guest.getGuestId());
            guestReservation.setFirstName(guest.getFirstName());
            guestReservation.setLastName(guest.getLastName());
            guestReservationMap.put(guest.getGuestId(),guestReservation);
        });

        Iterable<Reservation> reservationName=
                this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        //Retrieving roomId and guestId for a particular reservation date
        reservationName.forEach(individual -> {
            RoomReservation roomReservationn = guestReservationMap.get(individual.getGuestId());
            roomReservationn.setDate(date);

            Guest guest=this.guestRepository.findById(individual.getGuestId()).get();
            roomReservationn.setFirstName(guest.getFirstName());
            roomReservationn.setLastName(guest.getLastName());
            roomReservationn.setGuestId(guest.getGuestId());


        });

        List<RoomReservation> roomReservationName=new ArrayList<>();
        for(Long id:guestReservationMap.keySet()){
            roomReservationName.add(guestReservationMap.get(id));

        }
        return roomReservationName;
    }


    public List<RoomReservation> getRoomReservationsForDate(Date date){
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long,RoomReservation> roomReservationMap=new HashMap();
        rooms.forEach(room ->{
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(),roomReservation);
        });

            Iterable<Reservation> reservations=
                this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        //Retrieving roomId and guestId for a particular reservation date
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);

            Guest guest=this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());


        });


        List<RoomReservation> roomReservations=new ArrayList<>();
        for(Long id:roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(id));
        }



        //Sorting rooms alphabetically
        roomReservations.sort(new Comparator<RoomReservation>(){
            @Override
            public int compare(RoomReservation o1,RoomReservation o2){
                if(o1.getRoomName().equals(o2.getRoomName())){
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });

        return roomReservations;
    }

}
