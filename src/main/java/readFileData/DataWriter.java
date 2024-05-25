package readFileData;

import java.io.IOException;
import java.util.List;

public interface DataWriter<T> {
    void writeData(List<T> data, String filename) throws IOException;
}
