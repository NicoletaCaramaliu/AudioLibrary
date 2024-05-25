package readFileData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtility<T> implements DataReader<T>, DataWriter<T> {

    private final Gson gson = new Gson();
    private final Class<T> type;

    public JsonUtility(Class<T> type) {
        this.type = type;
    }

    @Override
    public List<T> readData(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            List<T> dataList = gson.fromJson(reader, listType);
            return dataList != null ? dataList : new ArrayList<>();
        }
    }

    @Override
    public void writeData(List<T> data, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(data, writer);
        }
    }
}
