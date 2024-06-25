package com.example.c23team2.Services;

import com.example.c23team2.Interfacemethods.PublicHolidayService;
import com.example.c23team2.Models.PublicHoliday;
import com.example.c23team2.Repositories.PublicHolidayRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Override
    @Transactional
    public List<PublicHoliday> getPublicHolidays() {
        return publicHolidayRepository.findAll();
    }
}
