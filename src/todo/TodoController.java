package todo;


import enums.PriorityDto;
import dto.TodoDto;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * TodoController ist eine Drehscheibe für alle andere Klassen, die abhängig davon sind um Daten und Funktion weiterzuleiten.
 * @author Muhamed Rustemi
 */
public class TodoController {

    private TodoRepository todoRepository;

    private List<TodoDto> todoItems = new ArrayList<>();

    private TodoDto todoItem;
    /**
     * Die Klasse TodoPersistence ist zuständig um die todos in einem File zuschreiben und zulesen.
     */
    public TodoPersistence todoPersistance;
    private  TodoApp todoApp;

    /**
     *Das File von der Festplatte wird importiert.
     * @return Das file das aus dem FileChosser eingelesn wird.
     */
    public File getFile() {
        return file;
    }

    /**
     * Setzt den file zu der Variable.
     * @param file der file wird in der GUI gesetzt
     */
    public void setFile(File file) {
        this.file = file;
    }

    private File file;


    /**
     * Konstruktor ohne eigenschaften
     */
    public TodoController() {

    }

    /**
     * Konstruktor der Persistence und Repository Klassen erkennt.
     * @param todoApp dient dazu, wenn es Daten über den Controller zum GUI senden sollte.
     */
    public TodoController(TodoApp todoApp) {
        this.todoPersistance = new TodoPersistence();
        this.todoRepository = new todo.TodoRepository(todoItems);
        this.todoApp = todoApp;
    }


    /**
     * Der TodoItem wird hier in der Repository zwischengespeichert.
     * @param text beschreibung vom TodoItem.
     * @param category kategorie vom TodoItem.
     * @param dueDate ausführdatum vom TodoItem.
     * @param priority priorität vom TodoItem.
     * @throws IOException wird dem Aufrufer weiter gegeben und auf der TodoApp abgefangen.
     */
    public void addTodo(String text, String category, LocalDate dueDate, PriorityDto priority) throws IOException {
        TodoDto newTodo = new TodoDto( text, category, dueDate, priority);
        this.todoRepository.saveTodo(newTodo);

    }


    /**
     * Der TodoItem wird über die bestehende Id editiert.
     * @param id TodoId welches gerade einen Update beekommt.
     ** @param text beschreibung vom TodoItem.
     *  @param category kategorie vom TodoItem.
     *  @param dueDate ausführdatum vom TodoItem.
     *  @param priority priorität vom TodoItem.
     *  @param done setzt den Status des Todoitem.
     */
    public void updateTodo(int id, String text, String category, LocalDate dueDate, PriorityDto priority, boolean done) {
        int index = findTodoIndex(getAllTodos(),id);
        getAllTodos().set(index, new TodoDto(id, text,category,dueDate,priority,done));
    }


    /**
     * TodoItem wird gelöscht.
     * @param id TodoIem welches gelöscht wird
     */
    public void deleteTodo(int id) {
        todoRepository.removeTodo(id);
    }

    /**
     * TodoItem wird der Status geändert auf erledigt.
     * @param id TodoIem welches den Status ändert.
     */
    public void setDoneTodo(int id) {
        todoRepository.setDone(id);
    }

    /**
     * Alle TodoItems werden geholt
     * @return gibt alle TodoItems zurück die in dem Zwischenspeicher drin sind.
     */
    public List<TodoDto> getAllTodos() {
        return this.todoItems;
    }

    /**
     * Die TodoItem werden von der Liste importiert und gespeichert.
     * @param todos Alle TodoItems die bereits in der Liste vorhanden sind.
     * @param filePath der pfad für das CSV File.
     */
    public void readAndSave(List<TodoDto> todos, File filePath) {
        this.todoPersistance.saveTodo(todos, filePath);
    }


    /**
     * Liest alle TodoItems von der Datei die über den FileChooser augeswählt wurde.
     * Es können nur Daein mit dem auserwählten namen importiert werden.
     * @return gibt die gesammte Liste der TodoItems zurück.
     */
    public List<TodoDto> readTodos(){
        if(getFile() == null) {
            return null;
        }
        if(getFile().getName().equals("todos.csv")) {
            todoItems.addAll(this.todoPersistance.readTodo(new File(getFile().getName())));
        }
        return todoItems;
    }


    public int countReadedTodos() {
        return this.todoRepository.getSize();
    }

    /**
     * Findet den Index in der List von dem Todo welches man Editieren will.
     * @param todos die Liste von allem TodoItems.
     * @param id die id von dem ausgewählten TodoItem.
     * @return gibt ein Index zurück oder wenn es nicht findet dan eine -1.
     */
    private  int findTodoIndex(List<TodoDto> todos, int id) {
        for (int i = 0; i < getAllTodos().size(); i++) {
            if (todos.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Setzt den TodoItem Status auf nicht erledigt.
     * @param id TodoItem welches den Status auf unerledigt überkommt.
     */
    public void setUndDoneTodo(int id) {
        todoRepository.setUnDone(id);
    }
}
