package todo;

import dto.TodoDto;

import java.util.List;

/**
 * Es schreibt vor welche Methoden in der Klasse, in welcher die Implementation stattfindet.
 */
public interface TodoRepositoryInterface {

    /**
     * Alle TodoItems die in der Applikation vorhanden sind.
     * @return Liste aller TodoItems.
     */
    List<TodoDto> getAllTodos();

    /**
     * Speichert einen TodoItem in der Liste ab.
     * @param todoDto der DatenTyp {@link TodoDto} des Items das gespeichert wird.
     *
     * @See TodoDto TodoDto
     */
    void saveTodo(TodoDto todoDto);

    /**
     * Holt einen TodoItem aus der Liste.
     * @param id TodoItems welches geholt wird.
     * @return gibt den TodoItems auch als wert zurück.
     */
    TodoDto getTodo(int id);

    /**
     *Löscht einen TodoItem aus der Liste
     * @param id das TodoItems welches gelöscht wird.
     * @return gibt einen boolean zurück;
     */
    boolean removeTodo(int id);

    /**
     * Die länge der Liste des TodoItems.
     * @return gibt dies als eine Zahl zurück.
     */
    int getSize();

    /**
     * Setzt den Status eines TodoItems auf erledigt.
     * @param id das Todo welches auf erledigt gesetzt wird.
     */
    void setDone(int id);


    /**
     * TodoItem wird auf unerledigt gesetzt.
     * @param id TodoItem welches den Status ändert.
     */
    void setUnDone(int id);

}
