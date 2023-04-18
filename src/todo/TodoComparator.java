package todo;

import dto.TodoDto;
import enums.PriorityDto;

import java.util.Comparator;

/**
 * Sortiert die TodoItems nach Priorität.
 */
public class TodoComparator implements Comparator<TodoDto> {
    @Override
    public int compare(TodoDto o1, TodoDto o2) {
     return o1.getPriority().compareTo(o2.getPriority());
    }
}
