package hotelsystem.csv;

import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.RoomType;
import hotelsystem.Exception.DataExportException;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomCsvService implements ICsvService<Room> {
    @Override
    public void exportCsv(List<Room> rooms, String filePath) throws DataExportException {
        try (PrintWriter writer = new PrintWriter(new File(filePath), "UTF-8")) {
            writer.println("id,number,type,price,capacity,condition,stars,available,clientId");

            for (Room room : rooms) {
                writer.println(String.format("%s,%d,%s,%.2f,%d,%s,%d,%b,%s",
                        CsvUtils.escapeCsv(room.getId()),
                        room.getNumberRoom(),
                        room.getType().name(),
                        room.getPriceForDay(),
                        room.getCapacity(),
                        room.getRoomCondition().name(),
                        room.getStars(),
                        room.isAvailable(),
                        room.getClientId() != null ? CsvUtils.escapeCsv(room.getClientId()) : ""));
            }
        } catch (IOException e) {
            throw new DataExportException("Error exporting rooms: " + e.getMessage());
        }
    }

    @Override
    public List<Room> importCsv(String filePath) throws DataImportException {
        List<Room> rooms = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = CsvUtils.parseCsvLine(line);
                if (parts.length < 8) continue;

                Room room = new Room(
                        CsvUtils.unescapeCsv(parts[0]),
                        Integer.parseInt(parts[1]),
                        RoomType.valueOf(parts[2]),
                        Double.parseDouble(parts[3]),
                        Integer.parseInt(parts[4]),
                        RoomCondition.valueOf(parts[5]),
                        Integer.parseInt(parts[6]));

                if (!Boolean.parseBoolean(parts[7])) {
                    room.changeAvailability();
                }

                if (parts.length > 8 && !parts[8].isEmpty()) {
                    room.setClientId(CsvUtils.unescapeCsv(parts[8]));
                }

                rooms.add(room);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new DataImportException("Error importing rooms: " + e.getMessage());
        }

        return rooms;
    }
}