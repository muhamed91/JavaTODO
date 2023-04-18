package todo;

import dto.TodoDto;

import java.util.Comparator;

/**
 * Sortiert die TodoItems nach ausführdatum.
 */
public class SortItemsComparator implements Comparator<TodoDto> {
    @Override
    public int compare(TodoDto o1, TodoDto o2) {
        return o1.getDueDate().compareTo(o2.getDueDate());
    }
}
