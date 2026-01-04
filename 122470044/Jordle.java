import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import java.util.Random;
import java.util.ArrayList;

/**
 * @author Umamah Uddin
 * @version 1.0.0
 */
public class Jordle extends Application {
    private static int rowsCount = 0;
    private static int columnsCount = 0;
    private static String secretWord;
    private static ArrayList<String> correct = new ArrayList<>();
    private static ArrayList<String> incorrect = new ArrayList<>();
    private static String right1;
    private static String wrong1;

    /**
     * Start in JavaFX.
     *
     * @param primaryStage Main stage for everything
     * @Override
     */
    public void start(Stage primaryStage) {

        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane, 500, 500);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(650);

        //// Top Pane: Title + Buttons
        // The title
        Label label = new Label("Jordle");
        Font nytFont = Font.loadFont("file:KarnakPro-CondensedBlack.ttf", 40);
        label.setFont(nytFont);
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);

        topBox.setPadding(new Insets(15, 10, 10, 10));
        topBox.setStyle("-fx-border-color: rgb(58,58,60)");

        // Background rectangle.
        Rectangle rect = new Rectangle();
        rect.widthProperty().bind(scene.widthProperty());
        rect.heightProperty().bind(scene.heightProperty());
        rect.setFill(Color.rgb(255, 255, 255));
        mainPane.getChildren().add(rect);

        // Adding toggle buttons.
        ToggleGroup group = new ToggleGroup();
        ImageView light = new ImageView("./Light.png");
        ImageView dark = new ImageView("./Dark.png");
        light.setFitHeight(30);
        light.setFitWidth(50);
        dark.setFitHeight(30);
        dark.setFitWidth(50);
        ToggleButton button1 = new ToggleButton("", light);
        button1.setStyle("-fx-background-color: transparent;");
        button1.setPadding(new Insets(10, 10, 10, 10));
        ToggleButton button2 = new ToggleButton("", dark);
        button2.setStyle("-fx-background-color: transparent;");
        button2.setPadding(new Insets(13, 10, 10, 10));

        // Center Pane: Grid
        GridPane gridPane = new GridPane();
        mainPane.setCenter(gridPane);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(20, 30, 30, 30));

        // Setting dimensions.
        int rows = 5;
        int columns = 6;

        // Adding squares.
        Label[][] labelPane = new Label[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                labelPane[i][j] = new Label();
                labelPane[i][j].setStyle("-fx-background-color: transparent; -fx-border-color: rgb(58, 58, 60);"
                        + "-fx-border-width: 2; -fx-font-family: 'Verdana'; "
                        + "-fx-font-size: 27px; -fx-font-weight: 800");
                labelPane[i][j].setPrefSize(62, 62);
                labelPane[columnsCount][rowsCount].setText(null);
                gridPane.add(labelPane[i][j], i, j);

            }
        }

        // Adding the letters.

        String guessString = "";
        Text messageBottom = new Text(50, 50, "");
        messageBottom.setFill(Color.rgb(58, 58, 60));
        messageBottom.setText("Try guessing a word.");
        Random rand = new Random();

        secretWord = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();

        VBox eh = new VBox();
        Text right = new Text(50, 50, "Words Guessed:");
        right.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        eh.getChildren().add(right);

        mainPane.setOnKeyPressed((KeyEvent e) -> {
            String charLeft = "";
            String checkedLeft = "";
            int green = 0;
            if (e.getCode().isLetterKey()) {
                if (rowsCount < (rows - 1) || rowsCount >= 0) {
                    if (columnsCount < (columns - 1) && columnsCount >= 0) {
                        labelPane[columnsCount][rowsCount].setAlignment(Pos.CENTER);
                        labelPane[columnsCount][rowsCount].setText(e.getText().toUpperCase());
                        ++columnsCount;
                    }
                }
            } else if (e.getCode() == KeyCode.BACK_SPACE) {
                if (columnsCount < columns && columnsCount > 0) {
                    --columnsCount;
                    labelPane[columnsCount][rowsCount].setText(null);
                    labelPane[columnsCount][rowsCount]
                            .setStyle("-fx-background-color: transparent; -fx-border-color: rgb(58, 58, 60);"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800;");
                }
            } else if ((e.getCode() == KeyCode.ENTER)) {
                if (columnsCount == (5)) {
                    for (int k = 0; k < 5; k++) {
                        if (labelPane[k][rowsCount].getText().equals(String.valueOf(secretWord.charAt(k)))) {
                            labelPane[k][rowsCount].setStyle(
                                    "-fx-background-color: rgb(121, 168, 107); -fx-border-color: rgb(121, 168, 107);"
                                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                            + "-fx-font-weight: 800; -fx-text-fill: white");
                            if (labelPane[k][rowsCount].getStyle()
                                    .contains("-fx-background-color: rgb(121, 168, 107)")) {
                                ++green;
                                if (green == 5) {
                                    messageBottom.setText("Congrats! You guessed it.");
                                    Text rightWords = new Text(secretWord);
                                    eh.getChildren().add(rightWords);
                                    eh.setPadding(new Insets(30, 20, 20, 30));
                                }
                            }
                        } else {
                            charLeft += secretWord.charAt(k);
                            checkedLeft += labelPane[k][rowsCount].getText();

                            if (secretWord.contains(String.valueOf(checkedLeft.charAt(checkedLeft.length() - 1)))) {
                                labelPane[k][rowsCount].setStyle("-fx-background-color: rgb(198, 180, 102);"
                                        + " -fx-border-color: rgb(198, 180, 102);"
                                        + "-fx-border-width: 2; -fx-font-family: 'Verdana';" + " -fx-font-size: 27px;"
                                        + "-fx-font-weight: 800; -fx-text-fill: white");
                            } else {
                                labelPane[k][rowsCount].setStyle(
                                        "-fx-background-color: rgb(58, 58, 60); -fx-border-color: rgb(58, 58, 60); "
                                                + "-fx-border-width: 2; -fx-font-family: 'Verdana';"
                                                + " -fx-font-size: 27px; "
                                                + "-fx-font-weight: 800; -fx-text-fill: white");
                            }
                        }
                    }
                    columnsCount = 0;
                    ++rowsCount;
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Please enter a word with five letters.");
                    error.showAndWait();
                }
            }
        });

        // Setting buttons to do actions.

        Button restart = new Button("Restart");
        restart.setOnAction(event -> {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    labelPane[i][j].setText(null);
                    labelPane[i][j].setStyle("-fx-background-color: transparent; -fx-border-color: rgb(58, 58, 60); "
                            + "-fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px; "
                            + "-fx-font-weight: 800; -fx-text-fill: black");
                }
            }
            columnsCount = 0;
            rowsCount = 0;
            secretWord = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();

        });

        StackPane stack = new StackPane();
        stack.setAlignment(Pos.BASELINE_RIGHT);
        button1.setToggleGroup(group);
        button2.setToggleGroup(group);
        button1.setOnAction(e -> {
            stack.getChildren().remove(button1);
            rect.setFill(Color.rgb(255, 255, 255));
            stack.getChildren().add(button2);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    labelPane[i][j].setTextFill(Color.BLACK);
                }
            }
        });
        button2.setOnAction(e -> {
            stack.getChildren().remove(button2);
            rect.setFill(Color.rgb(18, 18, 19));
            stack.getChildren().add(button1);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    labelPane[i][j].setTextFill(Color.WHITE);
                }
            }
        });
        button1.fire();
        ImageView check = new ImageView("./check.png");
        check.setFitHeight(30);
        check.setFitWidth(30);
        Button stats = new Button("", check);
        stats.setStyle("-fx-background-color: transparent;");

        BorderPane statsPane = new BorderPane();

        Label b = new Label("PLAYER STATS");
        b.setFont(Font.font("Helvetica", 15));
        b.setFont(Font.font("Verdana", FontWeight.BOLD, 25));

        HBox stBox = new HBox();
        stBox.setAlignment(Pos.CENTER);
        stBox.getChildren().add(b);
        stBox.setPadding(new Insets(20, 10, 10, 20));
        stBox.setStyle("-fx-border-color: rgb(58,58,60)");
        statsPane.setTop(stBox);

        statsPane.setCenter(eh);

        Scene stat = new Scene(statsPane);
        Stage stage3 = new Stage();
        stage3.setTitle("My Stats");
        stage3.setScene(stat);

        stats.setOnAction(e -> {
            stage3.show();

        });

        // Adding buttons to topbox.

        BorderPane toggle = new BorderPane();
        toggle.setRight(stack);
        stack.setAlignment(Pos.BASELINE_RIGHT);

        topBox.getChildren().addAll(stats, label, toggle);

        mainPane.setTop(topBox);

        // Bottom Pane: Buttons

        Button inst = new Button("My Instructions");
        BorderPane instPane = new BorderPane();
        Label t = new Label("HOW TO PLAY");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        t.setStyle("-fx-font-weight: 900 ");
        HBox tBox = new HBox();
        tBox.setAlignment(Pos.CENTER);
        tBox.getChildren().add(t);
        tBox.setPadding(new Insets(10, 15, 15, 15));
        tBox.setStyle("-fx-border-color: rgb(58,58,60)");
        instPane.setTop(tBox);

        Text instructions = new Text(50, 50,
                "Guess the JORDLE in six tries. \nEach guess doesn't have to be a valid five-letter word. "
                        + "Hit the enter button to submit."
                        + "\nAfter each guess, the color of the tiles will change to show how "
                        + "close your guess was to the word. \nGreen squares mean that letter is correct "
                        + "and is in the right spot."
                        + "\nYellow squares mean that letter is in the word but not in the right spot.\n"
                        + "Grey squares mean that letter is not in the word and is not in the correct spot."
                        + "\n\nPress restart to get a new word and start over."
                        + "\n\n**You can toggle between light and dark mode."
                        + "\n**You can view the words you have guessed by clicking the checkmark button."
                        + "\n**If you need help click the Hint button.");
        instPane.setCenter(instructions);
        instPane.setMargin(instructions, new Insets(30, 90, 30, 90));

        Scene popUp = new Scene(instPane);
        Stage stage2 = new Stage();
        stage2.setTitle("Instructions");
        stage2.setScene(popUp);
        inst.setOnAction(e -> {

            stage2.show();

        });

        Button word = new Button("Hint");
        BorderPane thePane = new BorderPane();
        Label m = new Label();

        m.setText("The first letter is: " + secretWord.charAt(0));

        m.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        m.setStyle("-fx-font-weight: 900 ");

        thePane.setPadding(new Insets(10, 15, 15, 15));
        thePane.setCenter(m);

        Scene po = new Scene(thePane);
        Stage stage4 = new Stage();
        stage4.setTitle("Hint");
        stage4.setScene(po);
        word.setOnAction(e -> {

            stage4.showAndWait();

        });

        HBox foot = new HBox();
        foot.getChildren().addAll(messageBottom, restart, inst, word);
        foot.setPadding(new Insets(10, 30, 30, 30));
        foot.setAlignment(Pos.CENTER);
        mainPane.setBottom(foot);

        // Idk stuff
        gridPane.setAlignment(Pos.CENTER);
        mainPane.setCenter(gridPane);
        mainPane.setAlignment(label, Pos.CENTER);
        mainPane.setAlignment(button1, Pos.TOP_RIGHT);
        mainPane.setAlignment(button2, Pos.TOP_RIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}