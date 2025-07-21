package csv;

import Exception.DataExportException;
import Exception.DataImportException;
import model.Amenity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AmenityCsvService implements ICsvService<Amenity> {
    @Override
    public void exportCsv(List<Amenity> amenities, String filePath) throws DataExportException {
        try (PrintWriter writer = new PrintWriter(new File(filePath), "UTF-8")) {
            writer.println("id,name,price");

            for (Amenity amenity : amenities) {
                writer.println(String.format("%s,%s,%.2f",
                        CsvUtils.escapeCsv(amenity.getId()),
                        CsvUtils.escapeCsv(amenity.getName()),
                        amenity.getPrice()));
            }
        } catch (IOException e) {
            throw new DataExportException("Error exporting amenities: " + e.getMessage());
        }
    }

    @Override
    public List<Amenity> importCsv(String filePath) throws DataImportException {
        List<Amenity> amenities = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {

            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = CsvUtils.parseCsvLine(line);
                if (parts.length < 3) continue;

                amenities.add(new Amenity(
                        CsvUtils.unescapeCsv(parts[0]),
                        CsvUtils.unescapeCsv(parts[1]),
                        Double.parseDouble(parts[2])));
            }
        } catch (IOException | NumberFormatException e) {
            throw new DataImportException("Error importing amenities: " + e.getMessage());
        }

        return amenities;
    }
}