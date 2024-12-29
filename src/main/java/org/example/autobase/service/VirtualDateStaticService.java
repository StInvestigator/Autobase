package org.example.autobase.service;

import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class VirtualDateStaticService {
    @Getter
    private static LocalDate currentVirtualLocalDate = LocalDate.now();

    public static void setNextVirtualDay() {
        currentVirtualLocalDate = currentVirtualLocalDate.plusDays(1);
    }

    public static Date getCurerentVirtualUtilDate(){
        return Date.from(currentVirtualLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private VirtualDateStaticService() {
    }
}
