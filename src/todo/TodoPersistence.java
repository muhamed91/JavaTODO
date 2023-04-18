package todo;

import dto.TodoDto;
import enums.PriorityDto;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Zuständig für das lesen und schreiben einer CSV Datei.
 */
public class TodoPersistence implements TodoPersistenceInterface {
    TodoController todoController = new TodoController();

    @Override
    public void saveTodo(List<TodoDto> todos, File file) {
        try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter("todos.csv"))) {
            fileWriter.write("Todo Saved: " + LocalDate.now()+"\n");

            for (TodoDto todo : todos) {
                String data = todo.getId() + ";" + todo.getText()+ ";" + todo.getDueDate().toString()+ ";" + todo.getCategory()+ ";" + todo.getPriority()+ ";" + todo.isDone() + "\n";
                fileWriter.write(data);
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TodoDto> readTodo(File file) {
        if(this.todoController == null) {
            return null;
        }

        try( BufferedReader bufReader = new BufferedReader(new FileReader(file))){
            List<TodoDto> todos = this.todoController.getAllTodos();
            // Read and discard the header row
            bufReader.readLine();
            String line;

            while ((line = bufReader.readLine()) != null) {
                String[] fields = line.split(";");
                Integer id;
                if(fields[0] == null || fields[0].equals("null")) {
                    id = null;
                } else {
                    id = Integer.valueOf(fields[0]);
                }
                String text = fields[1];
                LocalDate dueDate = LocalDate.parse(fields[2]);
                String category = fields[3];
                PriorityDto priority = PriorityDto.valueOf(fields[4]);
                Boolean todoState = Boolean.valueOf(fields[5]);
                TodoDto todo = new TodoDto( id,text, category, dueDate, priority, todoState);
                todos.add(todo);
            }

            return todos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
