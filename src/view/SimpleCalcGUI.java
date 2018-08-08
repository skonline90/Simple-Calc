package view;

import java.awt.Font;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimpleCalcGUI extends Application
{
    Label interactionArea;
    Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    Button bPlus, bMinus, bMulti, bDivide, bEquals;
    double accumulator;
    boolean isAccuFull = false, isReseted = false;
    String selectedOperator = null;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Simple Calc");

        initLabel();
        initButtons();
        createLayout(primaryStage);
    }

    private void initLabel()
    {
        interactionArea = new Label();
        interactionArea.setPadding(new Insets(10));
        interactionArea.setMinWidth(230);
        interactionArea.setMinHeight(50);
        javafx.scene.text.Font font = new javafx.scene.text.Font(20);
        interactionArea.setFont(font);
        interactionArea.setStyle(
                "-fx-background-color: #a0aabf; -fx-padding: 8px; -fx-background-insets: 8px;");
    }

    private void initButtons()
    {
        b0 = new Button("0");
        b1 = new Button("1");
        b2 = new Button("2");
        b3 = new Button("3");
        b4 = new Button("4");
        b5 = new Button("5");
        b6 = new Button("6");
        b7 = new Button("7");
        b8 = new Button("8");
        b9 = new Button("9");
        bPlus = new Button("+");
        bMinus = new Button("-");
        bMulti = new Button("*");
        bDivide = new Button("/");
        bEquals = new Button("=");
        Button[] numberButtons = {b0, b1, b2, b3, b4, b5, b6, b7, b8, b9};
        Button[] operatorButtons = {bPlus, bMinus, bMulti, bDivide};
        for (Button button : new Button[] {b1, b2, b3, b4, b5, b6, b7, b8, b9,
                bPlus, bMinus, bMulti, bDivide, bEquals})
        {
            button.setMinWidth(50);
            button.setMinHeight(50);
        }
        b0.setMinWidth(110);
        b0.setMinHeight(50);

        for (Button button : numberButtons)
            button.setOnAction(e -> {
                if (!isReseted)
                {
                    interactionArea.setText("");
                    isReseted = true;
                }
                String text = interactionArea.getText();
                if (text.length() + 1 < 12)
                {
                    interactionArea.setText(text + button.getText());
                }
            });

        for (Button button : operatorButtons)
        {
            button.setOnAction(e -> {
                if (selectedOperator == null)
                {
                    String text = interactionArea.getText();
                    if (text.length() == 0)
                    {
                        accumulator = 0;
                    }
                    else
                    {
                        accumulator = Double.parseDouble(text);
                    }
                    interactionArea.setText("");
                    isAccuFull = true;
                }
                selectedOperator = button.getText();
            });
        }
        bEquals.setOnAction(e -> {
            String text = interactionArea.getText();
            if (text.length() > 0)
            {
                double operand2 = Double.parseDouble(text);
                if (isAccuFull)
                {
                    double equals = applyOpperand(selectedOperator, operand2);
                    interactionArea.setText(Double.toString(equals));
                    isAccuFull = false;
                    isReseted = false;
                    selectedOperator = null;
                }
            }
        });
    }

    private void createLayout(Stage primaryStage)
    {
        HBox row1, row2, row3, row4;
        row1 = new HBox();
        row2 = new HBox();
        row3 = new HBox();
        row4 = new HBox();
        HBox[] rows = {row1, row2, row3, row4};
        for (HBox row : rows)
        {
            row.setSpacing(10);
            row.setPadding(new Insets(10));
        }

        row1.getChildren()
            .addAll(b7, b8, b9, bPlus);
        row2.getChildren()
            .addAll(b4, b5, b6, bMinus);
        row3.getChildren()
            .addAll(b1, b2, b3, bMulti);
        row4.getChildren()
            .addAll(b0, bEquals, bDivide);

        VBox vbox = new VBox();
        vbox.getChildren()
            .addAll(interactionArea, row1, row2, row3, row4);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double applyOpperand(String operator, double operand)
    {
        switch (operator)
        {
        case "+":
            return accumulator + operand;
        case "-":
            return accumulator - operand;
        case "*":
            return accumulator * operand;
        case "/":
            if (operand == 0)
            {
                return Double.NaN;
            }
            return accumulator / operand;
        default:
            return Double.POSITIVE_INFINITY;
        }
    }
}
