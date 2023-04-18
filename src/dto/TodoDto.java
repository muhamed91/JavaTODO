package dto;

import enums.PriorityDto;

import java.time.LocalDate;

/**
 * Das TodoItem Objekt und dessen eigenschaften.
 */

public class TodoDto {

    private Integer id;

    public TodoDto(Integer id, String text, String category, LocalDate dueDate, PriorityDto priority, boolean done) {
        this.id = id;
        this.text = text;
        this.category = category;
        this.dueDate = dueDate;
        this.priority = priority;
        this.done = done;

        if (this.id == null) {
            this.id = null; // Setze die ID standardmäßig auf null
        }
    }

    public TodoDto(String text, String category, LocalDate dueDate, PriorityDto priority) {
        this.text = text;
        this.category = category;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    private String text;

    private String category;

    private LocalDate dueDate;

    private PriorityDto priority;

    /**
     * Status des TodoItem.
     * @return gibt eine boolean wer zurück den man prüfen könnte.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Setzt den TodoITem auf erledigt.
     * @param done wird der variable gesetzt.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    private  boolean done;

    /**
     * Holt die ID des TodoItem.
     * @return gibt die ID von dem TodoItem zurück.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setzt die ID auf dem Item.
     * @param id nimmt die Id als input.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Der Beschrieb eines TodoItem.
     * @return gibt den zurück.
     */
    public String getText() {
        return text;
    }

    /**
     * Setzt den Beschrieb eines TodoItem.
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Holt die Kategorie eines TodoItems.
     * @return gibt die Kategorie als einen string zurück.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setzt die Kategorie eines TodoItems.
     * @param category nimmt den text als input und wird es gesetzt.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Holt das Ausführdatum des TodoItems.
     * @return gibt den als Datum zurück.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Setzt das Datum des TodoItem.
     * @param dueDate nimmt dies als parameter und setzt es auf die variable
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Holt den Priorität status des Todoitems.
     * @return gibt den als enum zurück;
     */
    public PriorityDto getPriority() {
        return priority;
    }

    /**
     * Setzt den TodoItem status.
     * @param priority nimmt den enum als parameter entgegen.
     */
    public void setPriority(PriorityDto priority) {
        this.priority = priority;
    }

    public String toString() {
        return "TodoDto{" +
                "id=" + id +
                ", title='" + text + '\'' +
                ", dueDate=" + dueDate +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }


}
