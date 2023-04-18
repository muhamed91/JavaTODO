package todo;
import dto.TodoDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Zuständigkeit für alle Aktionen die mit einem TodoItem zu tun haben.
 */
public class TodoRepository implements TodoRepositoryInterface {

    public TodoRepository(List<TodoDto> todoItems) {
        this.todoItems = todoItems;
    }
    private List<TodoDto> todoItems = new ArrayList<>();
    private int todoId;
    @Override
    public List<TodoDto> getAllTodos() {
       return this.todoItems;
    }

    @Override
    public void saveTodo(TodoDto todo) {
        int lastId = getAllTodos().isEmpty() ? 0 : getAllTodos().get(getAllTodos().size() - 1).getId();
        todoId = lastId +1;
        if (todo.getId() == null) {
            todo.setId(todoId);
            getAllTodos().add(todo);
        }

    }

    @Override
    public TodoDto getTodo(int id) {
        List<TodoDto> todoList = getAllTodos();
        for (TodoDto todo: todoList) {
            if(todo.getId() == id) {
                return todo;
            }
        }
        return  null;
    }

    @Override
    public boolean removeTodo(int id) {
        for (TodoDto todo : getAllTodos()) {
            if (todo.getId() == id) {
                getAllTodos().remove(todo);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSize() {
       return getAllTodos().size();

    }

    @Override
    public void setDone(int id) {
        TodoDto todo = getTodo(id);
        todo.setDone(true);
    }

    @Override
    public void setUnDone(int id) {
        TodoDto todo = getTodo(id);
        todo.setDone(false);
    }

}
