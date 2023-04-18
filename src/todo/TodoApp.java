package todo;

import dto.TodoDto;
import enums.PriorityDto;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Muhamed Rustemi
 * Startet die Applikation und baut das GUI zusammen.
 */

public class TodoApp extends Application {

    private final TodoController todoController;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Der Konstruktor instanziert den TodoController
     * TodoApp Klasse hat jetzt vollen zugriff auf den Controller.
     */
    public TodoApp() {
        this.todoController = new TodoController(this);
    }
    private final GridPane gridPane = this.staticGrid();
    private boolean isLoadedData = false;
    private final boolean isLoaded = false;
    private File selectedFile;
    private final Label countLabel = new Label();
    private final Stage primaryStage = new Stage();
    private final TextField nameField = new TextField();
    private final ChoiceBox<PriorityDto> choiceBox = new ChoiceBox<>();
    private final DatePicker datePicker = createValidatedDatePicker();
    private final TextField category = new TextField();
    private Integer id = null;
    private final boolean  hasFieldsError = false;
    private boolean sortByPrio = false;
    private boolean sortByDate = false;


    /**
     * Startet das GUI und stellt einen UI zur Verfügung.
     * @param primaryStage nimmt die Instanz der Klasse als input.
     */
    @Override
    public void start(Stage primaryStage) {

        //Header
        Label titleLabel = new Label("Todo Application");
        titleLabel.setFont(new Font(25));
        titleLabel.setTextFill(Color.WHITE);

        HBox header = new HBox();
        RadioButton btn1 = new RadioButton("Priority");
        btn1.setOnAction(e -> {
            if (btn1.isSelected()) {
                sortByPrio = true;
            } else {
                sortByPrio = false;
            }
            updateGridView();
        });
        RadioButton btn2 = new RadioButton("DueDate");
        btn2.setOnAction(e -> {
            if (btn2.isSelected()) {
                sortByDate = true;
            } else {
                sortByDate = false;
            }
            updateGridView();
        });
        btn1.setTextFill(Color.WHITE);
        btn2.setTextFill(Color.WHITE);
        header.setSpacing(15);

        header.getChildren().addAll(btn1, btn2);
        header.setAlignment(Pos.CENTER_RIGHT);

        VBox vbox = new VBox(10); // spacing between nodes is 10px
        vbox.setAlignment(Pos.CENTER_LEFT); // aligns content in the center both horizontally and vertically

        vbox.setStyle("-fx-background-color: #2a3e72;");
        vbox.setPadding(new Insets(25));
        vbox.getChildren().addAll(titleLabel, header);

        Button loadDataBtn = new Button("Load Data");
        FileChooser fileChooser = new FileChooser();
        Label countLabel = new Label();
        loadDataBtn.setOnAction(e -> {
            this.isLoadedData = true;
            selectedFile = fileChooser.showOpenDialog(null);
            if (!isLoaded) {
                this.todoController.setFile(selectedFile);
                updateGridView();
                countLabel.setText((this.todoController.countReadedTodos() + " Todos eingelesen"));
            }
        });
        countLabel.setTextFill(Color.WHITE);


        //Footer
        HBox footer = new HBox();
        footer.setSpacing(20);
        footer.setPadding(new Insets(25));
        footer.setStyle("-fx-background-color: #2a3e72;");
        Button saveBtn = new Button("Save Data");
        saveBtn.setOnAction(e -> this.todoController.readAndSave(this.todoController.getAllTodos(), selectedFile));

        Button addTodo = new Button("New Todo");

        addTodo.setOnAction(e -> this.showPopUp(primaryStage, false));

        footer.getChildren().addAll(saveBtn, loadDataBtn, countLabel, addTodo);

        VBox centerTodo = new VBox();
        centerTodo.setPadding(new Insets(10));
        centerTodo.getChildren().addAll(populateTodoGridPane(this.todoController.getAllTodos()), gridPane);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vbox);
        borderPane.setBottom(footer);
        borderPane.setCenter(centerTodo);// or any other node that you want to add to the left region
        Pane rightPane = new Pane();
        rightPane.setMinWidth(0); // set the minWidth to 0
        borderPane.setLeft(rightPane);
        Scene scene = new Scene(borderPane, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Todo Application");
        primaryStage.show();
    }


    private void showPopUp(Stage primaryStage, boolean isEditState) {

        HBox todoBox = new HBox();
        todoBox.setPadding(new Insets(25, 0, 0, 0));
        todoBox.setSpacing(15);
        Label nameLabel = new Label("Todo:");
        TextField nameField = this.nameField;
        todoBox.getChildren().addAll(nameLabel, nameField);

        HBox prioBox = new HBox();
        prioBox.setPadding(new Insets(10, 0, 0, 0));
        prioBox.setSpacing(15);

        ChoiceBox<PriorityDto> choiceBox = this.choiceBox;

        for (PriorityDto prio : PriorityDto.values()) {
            choiceBox.getItems().add(prio);
        }
        choiceBox.getSelectionModel().select(0);
        Label prioLabel = new Label("Priorität:");
        prioBox.getChildren().addAll(prioLabel, choiceBox);


        HBox dueBox = new HBox();
        dueBox.setPadding(new Insets(10, 0, 0, 0));
        dueBox.setSpacing(15);
        Label dateLabel = new Label("Datum:");
        DatePicker datePicker = this.datePicker;
        dueBox.getChildren().addAll(dateLabel, datePicker);

        HBox categoryBox = new HBox();
        categoryBox.setPadding(new Insets(15, 0, 0, 0));
        categoryBox.setSpacing(15);
        Label categoryLabel = new Label("Kategorie:");
        TextField categoryField = this.category;
        categoryBox.getChildren().addAll(categoryLabel, categoryField);

        HBox checkboxContainer = this.statusCheckbox();
        CheckBox checkboxDone = new CheckBox();
        if (isEditState) {
            Label checkboxDoneLabel = new Label("Status:");
            checkboxContainer.getChildren().addAll(checkboxDoneLabel, checkboxDone);
        }

        Button saveTodos = new Button("Save Todo");
        saveTodos.setOnAction(e -> {
            this.isLoadedData = false;
            String name = nameField.getText();
            PriorityDto select = choiceBox.getValue();
            LocalDate datepicker = datePicker.getValue();
            String category = categoryField.getText();
            boolean done = checkboxDone.isSelected();


            if (isEditState) {
                this.todoController.updateTodo(this.id, name, category, datepicker, select, done);
                updateGridView();
                countLabel.setText("Todo mit id=" + this.id + " " + "wurde bearbeitet");
                return;
            }

            try {
                this.todoController.addTodo(name, category, datepicker, select);
                updateGridView();
                if (!hasFieldsError) {
                    countLabel.setText("new Todo created");
                    nameField.clear();
                    datePicker.setValue(null);
                    categoryField.clear();
                } else {
                    countLabel.setText("Todo kann nicht gespeichert werden");
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        VBox vContainerBox = new VBox();
        vContainerBox.setPadding(new Insets(0, 0, 0, 25));
        vContainerBox.getChildren().addAll(todoBox, prioBox, dueBox, categoryBox, checkboxContainer, saveTodos);

        String todoState;
        String editTodo = "Todo editieren";
        String createTodo = "Todo erstellen";
        todoState = isEditState ? editTodo : createTodo;

        this.popUp(primaryStage, vContainerBox, todoState, saveTodos);

    }


    private void updateGridView() {

        gridPane.getChildren().clear();
        Font font = Font.font("Helevtica", FontWeight.MEDIUM, 15);

        List<TodoDto> todoItems;

        if (isLoadedData) {

            todoItems = this.todoController.readTodos();
        } else {
            todoItems = this.todoController.getAllTodos();
        }

        if(todoItems == null) {
            return;
        }

        List<TodoDto> sortedTodoItems = new ArrayList<>(todoItems);
        if (sortByPrio) {
            sortedTodoItems.sort(new TodoComparator());
        } else if (sortByDate) {
            sortedTodoItems.sort(new SortItemsComparator());
        }

        if (sortedTodoItems.isEmpty()) {
            todoItems = new ArrayList<>(todoItems);
        } else {
            todoItems = sortedTodoItems;
        }

        int vDistance = 10;

        int rowIndex = 1;
        for (TodoDto todo : todoItems) {
            Label id = new Label((String.valueOf(todo.getId())));
            id.setFont(font);
            id.setPadding(new Insets(0, 0, vDistance, 0));
            Label text = new Label(todo.getText());
            text.setPadding(new Insets(0, 0, vDistance, 0));
            text.setFont(font);
            Label dueDate = null;
            if (todo.getDueDate() != null) {
                dueDate = new Label(todo.getDueDate().toString());
                dueDate.setFont(font);
                dueDate.setPadding(new Insets(0, 0, vDistance, 0));
                dueDate.setFont(font);
            }
            Label category = new Label(todo.getCategory());
            category.setFont(font);
            category.setPadding(new Insets(0, 0, vDistance, 0));
            Label priority = new Label(evaluatePrio(todo.getPriority()));
            priority.setPadding(new Insets(0, 0, vDistance, 0));
            priority.setFont(font);

            gridPane.add(id, 0, rowIndex);
            gridPane.add(text, 1, rowIndex);
            if (dueDate != null) {
                gridPane.add(dueDate, 2, rowIndex);
            }
            gridPane.add(category, 3, rowIndex);
            gridPane.add(priority, 4, rowIndex);
            Button deleteButton = new Button("Delete");

            CheckBox checkbox = new CheckBox();
            if (todo.isDone()) {
                checkbox.setSelected(true);
            }
            int finalRowIndex = rowIndex;
            checkbox.setOnAction(actionEvent -> {
                if (checkbox.isSelected()) {
                    todoController.setDoneTodo(todo.getId());
                    gridPane.add(deleteButton, 7, finalRowIndex);
                } else {
                    todoController.setUndDoneTodo(todo.getId());
                    gridPane.getChildren().remove(deleteButton);
                }
            });

            gridPane.add(checkbox, 5, rowIndex);

            Button editButton = new Button("Edit");
            editButton.setOnAction(event -> {
                this.showPopUp(primaryStage, true);
                this.nameField.setText(todo.getText());
                this.choiceBox.setValue(todo.getPriority());
                this.datePicker.setValue(todo.getDueDate());
                this.category.setText(todo.getCategory());
                this.id = todo.getId();
                countLabel.setText("");
            });
            gridPane.add(editButton, 6, rowIndex);


            List<TodoDto> finalTodoItems = todoItems;
            deleteButton.setOnAction(event -> {
                finalTodoItems.remove(todo);
                this.todoController.deleteTodo(todo.getId());
                this.isLoadedData = false;
                updateGridView();
            });

            if(todo.isDone()) {
                gridPane.add(deleteButton, 7, rowIndex);
            }

            rowIndex++;
        }
    }

    public GridPane populateTodoGridPane(List<TodoDto> allTodos) {

        GridPane todoGridPane = staticGrid();

        Label idLabel = new Label("ID");
        Font font = Font.font("Helevtica", FontWeight.BOLD, 20);
        idLabel.setFont(font);
        Label textLabel = new Label("Text");
        textLabel.setFont(font);
        Label dueDateLabel = new Label("Datum");
        dueDateLabel.setFont(font);
        Label categoryLabel = new Label("Kategorie");
        categoryLabel.setFont(font);
        Label priorityLabel = new Label("Priorität");
        priorityLabel.setFont(font);

        todoGridPane.add(idLabel, 0, 1);
        todoGridPane.add(textLabel, 1, 1);
        todoGridPane.add(dueDateLabel, 2, 1);
        todoGridPane.add(categoryLabel, 3, 1);
        todoGridPane.add(priorityLabel, 4, 1);

        return todoGridPane;
    }


    private GridPane staticGrid() {
        GridPane todoGridPane = new GridPane();

        ColumnConstraints idCol = new ColumnConstraints();
        idCol.setPrefWidth(50);
        todoGridPane.getColumnConstraints().add(idCol);

        ColumnConstraints textCol = new ColumnConstraints();
        textCol.setPrefWidth(300);
        todoGridPane.getColumnConstraints().add(textCol);

        ColumnConstraints dueDateCol = new ColumnConstraints();
        dueDateCol.setPrefWidth(100);
        todoGridPane.getColumnConstraints().add(dueDateCol);

        ColumnConstraints categoryCol = new ColumnConstraints();
        categoryCol.setPrefWidth(140);
        todoGridPane.getColumnConstraints().add(categoryCol);

        ColumnConstraints priorityCol = new ColumnConstraints();
        priorityCol.setPrefWidth(150);
        todoGridPane.getColumnConstraints().add(priorityCol);

        ColumnConstraints checkboxCol = new ColumnConstraints();
        checkboxCol.setPrefWidth(50);
        todoGridPane.getColumnConstraints().add(checkboxCol);

        ColumnConstraints editCol = new ColumnConstraints();
        editCol.setPrefWidth(100);
        todoGridPane.getColumnConstraints().add(editCol);


        ColumnConstraints deleteCol = new ColumnConstraints();
        deleteCol.setPrefWidth(100);
        todoGridPane.getColumnConstraints().add(deleteCol);


        return todoGridPane;
    }

    private Stage popUp(Stage primaryStage, VBox vContainerBox, String title, Button saveTodos) {

        Stage popup = new Stage();
        HBox header = new HBox();
        header.setSpacing(15);
        header.setStyle("-fx-background-color: #2a3e72;");
        header.setPadding(new Insets(15));


        HBox footer = new HBox();
        footer.setSpacing(20);
        footer.setPadding(new Insets(25));
        footer.setStyle("-fx-background-color: #2a3e72;");
        footer.setAlignment(Pos.BOTTOM_RIGHT);


        Button cancelBtn = new Button("cancel");
        cancelBtn.setOnAction(actionEvent -> popup.close());
        Label countLabel = this.countLabel;
        countLabel.setTextFill(Color.WHITE);
        Button addTodo = new Button("Save Todo");

        addTodo.setOnAction(e -> this.showPopUp(primaryStage, false));

        footer.getChildren().addAll(countLabel, cancelBtn, saveTodos);

        BorderPane borderPane = new BorderPane();

        borderPane.setTop(header);
        borderPane.setCenter(vContainerBox);

        Pane left = new Pane();
        left.setPadding(new Insets(0));

        Pane right = new Pane();
        right.setPadding(new Insets(120));

        borderPane.setLeft(left);
        borderPane.setRight(right);
        borderPane.setBottom(footer);

        Scene scene = new Scene(borderPane, 500, 350);
        popup.setScene(scene);
        popup.initOwner(primaryStage);
        popup.setTitle(title);
        popup.show();

        return popup;
    }


    private String evaluatePrio(PriorityDto prioEnum) {

        String output = "";

        switch (prioEnum) {
            case PRIORITY_1:
                output = "Prio 1";
                break;
            case PRIORITY_2:
                output = "Prio 2";
                break;
            case PRIORITY_3:
                output = "Prio 3";
                break;
            case PRIORITY_4:
                output = "Prio 4";
                break;
            case PRIORITY_5:
                output = "Prio 5";
                break;
        }

        return output;

    }

    private HBox statusCheckbox() {
        HBox checkboxContainer = new HBox();
        checkboxContainer.setPadding(new Insets(15, 0, 20, 0));
        checkboxContainer.setSpacing(15);

        return checkboxContainer;
    }


    private DatePicker createValidatedDatePicker() {
        final DatePicker datePicker = new DatePicker();
        final String DATE_FORMAT = "dd/MM/yyyy";

        datePicker.setPromptText(DATE_FORMAT);
        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                LocalDate.parse(newValue);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        });

        return datePicker;
    }


}
