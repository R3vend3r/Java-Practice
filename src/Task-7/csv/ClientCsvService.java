package csv;

import Exception.DataExportException;
import Exception.DataImportException;
import model.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientCsvService implements ICsvService<Client> {
    @Override
    public void exportCsv(List<Client> clients, String filePath) throws DataExportException {
        try (PrintWriter writer = new PrintWriter(new File(filePath), "UTF-8")) {
            writer.println("id,name,surname,roomNumber");

            for (Client client : clients) {
                writer.println(String.format("%s,%s,%s,%d",
                        CsvUtils.escapeCsv(client.getId()),
                        CsvUtils.escapeCsv(client.getName()),
                        CsvUtils.escapeCsv(client.getSurname()),
                        client.getRoomNumber()));
            }
        } catch (IOException e) {
            throw new DataExportException("Error exporting clients: " + e.getMessage());
        }
    }

    @Override
    public List<Client> importCsv(String filePath) throws DataImportException {
        List<Client> clients = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"))) {

            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = CsvUtils.parseCsvLine(line);
                if (parts.length < 4) continue;

                clients.add(new Client(
                        CsvUtils.unescapeCsv(parts[0]),
                        CsvUtils.unescapeCsv(parts[1]),
                        CsvUtils.unescapeCsv(parts[2]),
                        Integer.parseInt(parts[3])));
            }
        } catch (IOException | NumberFormatException e) {
            throw new DataImportException("Error importing clients: " + e.getMessage());
        }

        return clients;
    }
}