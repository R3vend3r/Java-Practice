package csv;

import Exception.DataExportException;
import Exception.DataImportException;
import enums.RoomType;
import model.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RoomBookingCsvService implements ICsvService<RoomBooking> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exportCsv(List<RoomBooking> bookings, String filePath) throws DataExportException {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {

            writer.println("id,clientId,clientName,clientSurname,clientRoom,roomNumber,creationDate,checkOutDate,totalPrice");

            for (RoomBooking booking : bookings) {
                Client client = booking.getClient();
                writer.println(String.format("%s,%s,%s,%s,%d,%d,%s,%s,%.2f",
                        booking.getId(),
                        client.getId(),
                        CsvUtils.escapeCsv(client.getName()),
                        CsvUtils.escapeCsv(client.getSurname()),
                        client.getRoomNumber(),
                        booking.getRoom().getNumberRoom(),
                        DATE_FORMAT.format(booking.getCreationDate()),
                        DATE_FORMAT.format(booking.getCheckOutDate()),
                        booking.getTotalPrice()));
            }
        } catch (IOException e) {
            throw new DataExportException("Error exporting room bookings: " + e.getMessage());
        }
    }

    @Override
    public List<RoomBooking> importCsv(String filePath) throws DataImportException {
        List<RoomBooking> bookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"))) {

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = CsvUtils.parseCsvLine(line);
                if (parts.length < 9) {
                    throw new DataImportException("Invalid data format in line: " + line);
                }

                Client client = new Client(
                        parts[1],
                        CsvUtils.unescapeCsv(parts[2]),
                        CsvUtils.unescapeCsv(parts[3]),
                        Integer.parseInt(parts[4]));

                Room room = new Room(
                        Integer.parseInt(parts[5]),
                        RoomType.STANDARD, 0.0, 1);

                RoomBooking booking = new RoomBooking(
                        parts[0],
                        client,
                        room,
                        DATE_FORMAT.parse(parts[7]));

                booking.setCreationDate(DATE_FORMAT.parse(parts[6]));
                booking.setTotalPrice(Double.parseDouble(parts[8]));

                bookings.add(booking);
            }
        } catch (Exception e) {
            throw new DataImportException("Error importing room bookings: " + e.getMessage());
        }

        return bookings;
    }
}