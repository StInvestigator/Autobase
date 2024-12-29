package org.example.autobase.service.logs;

import org.example.autobase.entity.CompletedTrip;
import org.example.autobase.service.VirtualDateStaticService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
public class LogsManager {
    @Value("${data.logs}")
    private String logsPath;

    public  void createLogsFile(List<CompletedTrip> trips) {
        if (trips == null || trips.isEmpty()) {
            return;
        }
        try (FileOutputStream fos = new FileOutputStream( Path.of(".",logsPath, VirtualDateStaticService.getCurrentVirtualLocalDate() + ".txt").toFile())) {
            StringBuilder logs = new StringBuilder();
            for (CompletedTrip trip : trips) {
                logs.append(trip.toString()).append(";\n");
            }
            byte[] buffer = logs.toString().getBytes();

            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
