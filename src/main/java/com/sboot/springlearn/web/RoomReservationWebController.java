package com.sboot.springlearn.web;


import com.sboot.springlearn.business.domain.RoomReservation;
import com.sboot.springlearn.business.service.ReservationService;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Date;
import java.util.List;

//To get reservations on a particular date

@Controller
@RequestMapping("/reservations")
public class RoomReservationWebController {

    private final ReservationService reservationService;

    @Autowired
    public RoomReservationWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getReservations(@RequestParam(value="date", required=false)String dateString, Model model){

        Date date=DateUtils.createDateFormatString(dateString);
        List<RoomReservation> roomReservations = this.reservationService.getRoomReservationsForDate(date);
        model.addAttribute("roomReservations",roomReservations);
        return "reservations";

    }


}
