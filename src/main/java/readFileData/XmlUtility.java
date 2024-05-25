package readFileData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlUtility<T> implements DataReader<T>, DataWriter<T> {

    private final Class<T> type;
    private final ObjectMapper xmlMapper;

    public XmlUtility(Class<T> type) {
        this.type = type;
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public List<T> readData(String filename) throws IOException {
        File file = new File(filename);
        return xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, type));
    }

    @Override
    public void writeData(List<T> data, String filename) throws IOException {
        File file = new File(filename);
        xmlMapper.writeValue(file, data);
    }
}
