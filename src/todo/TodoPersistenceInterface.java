package todo;

import dto.TodoDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Es schreibt vor welche Methoden in der Klasse, in welcher die Implementation stattfindet.
 */
public interface TodoPersistenceInterface {
    /**
     * Die Liste der TodoItems wird in einem CSV geschrieben.
     * @param todos Liste von allem TodoItems die bereits eingetragen wurde oder eventuell auch importiert.
     * @param file die Datei im welchen die Liste eingeschrieben wird.
     * @throws IOException wird in der Klasse abgefangen und behandelt.
     */
    void saveTodo(List<TodoDto> todos, File file) throws IOException;

    /**
     *
     * @param file die Datei vom welchen die TodoItems eingelesen werden..
     * @return gibt alle gelesene TodoItems in einer Liste zurück.
     * @throws IOException wird in der Klasse abgefangen und behandelt.
     */
    List<TodoDto> readTodo(File file) throws IOException;

}
