package readFileData;

import java.io.IOException;
import java.util.List;

public interface DataReader<T> {
    List<T> readData(String filename) throws IOException;
}
