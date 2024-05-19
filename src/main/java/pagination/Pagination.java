package pagination;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    public static <T> List<T> getPage(List<T> list, int pageNumber, int itemsPerPage) {
        int fromIndex = (pageNumber - 1) * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, list.size());
        if (fromIndex >= list.size() || fromIndex < 0) {
            return new ArrayList<>();
        }
        return list.subList(fromIndex, toIndex);
    }

    public static <T> int getTotalPages(List<T> list, int itemsPerPage) {
        return (int) Math.ceil((double) list.size() / itemsPerPage);
    }

    public static <T> boolean isValidPage(List<T> list, int pageNumber, int itemsPerPage) {
        return pageNumber > 0 && pageNumber <= getTotalPages(list, itemsPerPage);
    }
}