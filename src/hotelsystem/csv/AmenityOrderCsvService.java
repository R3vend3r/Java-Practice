package hotelsystem.csv;

import hotelsystem.Exception.DataExportException;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AmenityOrderCsvService implements ICsvService<AmenityOrder> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exportCsv(List<AmenityOrder> orders, String filePath) throws DataExportException {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {

            writer.println("id,clientId,clientName,clientSurname,clientRoom,amenityId,amenityName,amenityPrice,creationDate,serviceDate,totalPrice");

            for (AmenityOrder order : orders) {
                Client client = order.getClient();
                Amenity amenity = order.getAmenity();

                writer.println(String.format("%s,%s,%s,%s,%d,%s,%s,%.2f,%s,%s,%.2f",
                        order.getId(),
                        client.getId(),
                        CsvUtils.escapeCsv(client.getName()),
                        CsvUtils.escapeCsv(client.getSurname()),
                        client.getRoom().getNumberRoom(),
                        amenity.getId(),
                        CsvUtils.escapeCsv(amenity.getName()),
                        amenity.getPrice(),
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
                if (parts.length < 11) {
                    throw new DataImportException("Invalid data format in line: " + line);
                }

                Client client = new Client(
                        parts[1],
                        CsvUtils.unescapeCsv(parts[2]),
                        CsvUtils.unescapeCsv(parts[3]),
                null);

                Amenity amenity = new Amenity(
                        parts[5],
                        CsvUtils.unescapeCsv(parts[6]),
                        Double.parseDouble(parts[7]));

                AmenityOrder order = new AmenityOrder(
                        parts[0],
                        client,
                        Double.parseDouble(parts[10]),
                        amenity,
                        DATE_FORMAT.parse(parts[9]));

                order.setCreationDate(DATE_FORMAT.parse(parts[8]));

                orders.add(order);
            }
        } catch (Exception e) {
            throw new DataImportException("Error importing amenity orders: " + e.getMessage());
        }

        return orders;
    }
}