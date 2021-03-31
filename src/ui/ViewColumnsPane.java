package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ViewColumnsPane<T> extends GridPane {
    private final ComboBox<ComboBoxItemWrap<T>> displayColumnNames;
    private String tableName;
    private final ObservableList<ComboBoxItemWrap<T>> cells = FXCollections.observableArrayList();
    private final T defaultItem;

    public ViewColumnsPane(Consumer<List<T>> getCheckedItems, T defaultItem) {
        displayColumnNames = new ComboBox<>();
        this.defaultItem = defaultItem;

        VBox labelAndColumn = new VBox();
        labelAndColumn.setSpacing(7);

        Label filterLabel = new Label("Display Columns: ");
        filterLabel.setFont(Font.font("Times New Roman", 16));
        filterLabel.setWrapText(true);
        labelAndColumn.getChildren().addAll(filterLabel, displayColumnNames);
        add(labelAndColumn, 0, 0);

        displayColumnNames.prefWidthProperty().bind(this.widthProperty());
        displayColumnNames.setMinHeight(25);
        displayColumnNames.setCellFactory(c -> {
            ListCell<ComboBoxItemWrap<T>> cell = new ListCell<ComboBoxItemWrap<T>>() {
                @Override
                protected void updateItem(ComboBoxItemWrap<T> item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        final CheckBox cb = new CheckBox(item.toString());
                        cb.selectedProperty().bind(item.checkProperty());
                        setGraphic(cb);
                    }
                }
            };

            cell.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
                cell.getItem().checkProperty().set(!cell.getItem().checkProperty().get());
                StringBuilder sb = new StringBuilder();
                checkSelection(cell);
                List<T> items = new ArrayList<>();

                displayColumnNames.getItems()
                        .filtered(Objects::nonNull)
                        .filtered(ComboBoxItemWrap::getCheck)
                        .forEach((p) -> {
                            sb.append("; ").append(p.getItem());
                            items.add(p.getItem());
                        });
                final String string = sb.toString();
                getCheckedItems.accept(items);

                displayColumnNames.setPromptText(string.substring(Integer.min(2, string.length())));
            });

            return cell;
        });
    }

    public List<T> getSelectedColumns() {
        List<T> items = new ArrayList<>();
        displayColumnNames.getItems()
                .filtered(Objects::nonNull)
                .filtered(ComboBoxItemWrap::getCheck)
                .forEach((p) -> items.add(p.getItem()));

        if (items.size() == 0) {
            items.add(defaultItem);
            displayColumnNames.getItems().get(0).setCheck(true);
            displayColumnNames.setPromptText(defaultItem.toString());
        }


        return items;
    }

    private void checkSelection(Cell<ComboBoxItemWrap<T>> cellCalled) {
        if (cellCalled.getItem().getItem() == defaultItem) {
            displayColumnNames.getItems().forEach(item -> item.setCheck(false));
            cellCalled.getItem().setCheck(true);
        } else {
            displayColumnNames.getItems().get(0).setCheck(false);
        }

    }

    public void updateFilterList(List<T> columns, String tableName) {
        if (this.tableName == null || !this.tableName.equals(tableName)) {
            this.tableName = tableName;

            cells.clear();
            ComboBoxItemWrap<T> cb = new ComboBoxItemWrap<>(defaultItem);
            cb.setCheck(true);
            cells.add(cb);
            columns.forEach(item -> cells.add(new ComboBoxItemWrap<>(item)));
            displayColumnNames.setItems(cells);
            displayColumnNames.setPromptText(defaultItem.toString());
        }
    }
}
