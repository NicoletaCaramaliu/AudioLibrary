package pagination;

import java.util.List;
import java.util.Scanner;

public class Paginator<T> {
    private final List<T> items;
    private final int itemsPerPage;
    private int currentPage;

    public Paginator(List<T> items, int itemsPerPage) {
        this.items = items;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 1;
    }

    public void paginate() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            List<T> page = Pagination.getPage(items, currentPage, itemsPerPage);
            page.forEach(item -> System.out.println(item.toString()));
            System.out.println("\nPage " + currentPage + " of " + Pagination.getTotalPages(items, itemsPerPage));
            System.out.print("Enter command: n (next), p (previous), q (quit)\n");
            String navCommand = scanner.nextLine();
            switch (navCommand) {
                case "n":
                    if (Pagination.isValidPage(items, currentPage + 1, itemsPerPage)) {
                        currentPage++;
                    } else {
                        System.out.println("You are on the last page.");
                    }
                    break;
                case "p":
                    if (Pagination.isValidPage(items, currentPage - 1, itemsPerPage)) {
                        currentPage--;
                    } else {
                        System.out.println("You are on the first page.");
                    }
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
