package com.example.c23team2.Interfacemethods;

import com.example.c23team2.Models.PublicHoliday;
import com.example.c23team2.Repositories.PublicHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PublicHolidayService {
    List<PublicHoliday> getPublicHolidays();
}