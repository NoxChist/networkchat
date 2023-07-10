package tools;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class CsvParser<T> {
    private File file = null;

    public CsvParser(File file) {
        if (file.exists() && file.isFile() && file.canRead()) {
            this.file = file;
        } else throw new IllegalStateException();
    }

    public List<T> parse(Class<T> type, String[] column_mapping) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();

            strategy.setType(type);
            strategy.setColumnMapping(column_mapping);
            CsvToBean<T> csv = new CsvToBeanBuilder<T>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
