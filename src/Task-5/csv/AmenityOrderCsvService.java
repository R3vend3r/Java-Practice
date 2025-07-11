package csv;

import Exception.DataExportException;
import Exception.DataImportException;
import model.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AmenityOrderCsvService implements ICsvService<AmenityOrder> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exportCsv(List<AmenityOrder> orders, String filePath) throws DataExportException {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {

            writer.println("id,clientId,clientName,clientSurname,clientRoom,amenityId,creationDate,serviceDate,totalPrice");

            for (AmenityOrder order : orders) {
                Client client = order.getClient();
                writer.println(String.format("%s,%s,%s,%s,%d,%s,%s,%s,%.2f",
                        order.getId(),
                        client.getId(),
                        CsvUtils.escapeCsv(client.getName()),
                        CsvUtils.escapeCsv(client.getSurname()),
                        client.getRoomNumber(),
                        order.getAmenity().getId(),
                        DATE_FORMAT.format(order.getCreationDate()),
                        DATE_FORMAT.format(order.getServiceDate()),
                        order.getTotalPrice()));
            }
        } catch (IOException e) {
            throw new DataExportException("Error exporting amenity orders: " + e.getMessage());
        }
    }

    @Override
    public List<AmenityOrder> importCsv(String filePath) throws DataImportException {
        List<AmenityOrder> orders = new ArrayList<>();

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

                Amenity amenity = new Amenity(
                        parts[5],
                        "Unknown",
                        0.0);

                AmenityOrder order = new AmenityOrder(
                        parts[0],
                        client,
                        amenity,
                        DATE_FORMAT.parse(parts[7]));

                order.setCreationDate(DATE_FORMAT.parse(parts[6]));
                order.setTotalPrice(Double.parseDouble(parts[8]));

                orders.add(order);
            }
        } catch (Exception e) {
            throw new DataImportException("Error importing amenity orders: " + e.getMessage());
        }

        return orders;
    }
}