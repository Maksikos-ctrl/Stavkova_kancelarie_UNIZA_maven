package com.stavkova.kancelarie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Controller {
    @FXML
    private Label welcomeText;
    @FXML
    private Spinner<Integer> numOfSelectedNumsInput;
    @FXML
    private Spinner<Double> betPerPlayAndDrawInput;
    @FXML
    private VBox resultBox;
    @FXML
    private GridPane gameboard;
    @FXML
    private GridPane selectedNumsGridPane;
    @FXML
    private TextField rowsInput;
    @FXML
    private TextField colsInput;
    @FXML
    private VBox ticketBox;
    @FXML
    private Button startButton;
    @FXML
    private ImageView qrCodeImageView;

    private Loterei loterei;
    private TicketManager ticketManager;
    private List<Integer> selectedNumbers = new ArrayList<>();
    private Stage newWindowStage;

    public void initialize() {
        numOfSelectedNumsInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        betPerPlayAndDrawInput.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, Double.MAX_VALUE, 1, 0.1));

        int numRows = 8;
        int numCols = 10;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int number = row * numCols + col + 1;
                Button button = new Button(String.valueOf(number));
                button.setOnAction(event -> handleButtonClick(button, number));
                gameboard.add(button, col, row);
            }
        }

        rowsInput.setVisible(false);
        colsInput.setVisible(false);

        // Initialize Loterei
        loterei = new Loterei(numRows, numCols);
        ticketManager = new TicketManager(new ArrayList<>());
    }

    private void handleButtonClick(Button button, int number) {
        boolean inWinningCombination = checkIfInWinningCombination(number);
        if (inWinningCombination) {
            button.setStyle("-fx-background-color: lightgreen;");
        } else {
            button.setStyle("-fx-background-color: lightcoral;");
        }

        int numOfSelectedNums = numOfSelectedNumsInput.getValue();
        int numOfCells = selectedNumsGridPane.getChildren().size();
        if (numOfCells < numOfSelectedNums) {
            Button cell = new Button(String.valueOf(number));
            cell.setOnAction(event -> handleSelectedNumberClick(cell, number));
            selectedNumsGridPane.add(cell, numOfCells, 0);
            selectedNumbers.add(number);
        }
    }

    private void handleSelectedNumberClick(Button button, int number) {
        selectedNumsGridPane.getChildren().remove(button);
        selectedNumbers.remove(Integer.valueOf(number));
    }

    private boolean checkIfInWinningCombination(int number) {
        int winningNumber = loterei.getWinningNumber();
        return number == winningNumber;
    }

    public void onStartButtonClick(ActionEvent event) {
        generateQRCode();
        addingTicketToTicketBox();
        openNewWindow();
    }

    private void openNewWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/new_window.fxml"));
            Parent root = fxmlLoader.load();
            NewWindowController newWindowController = fxmlLoader.getController();
            newWindowController.setTicketId(ticketManager.getTickets().size());
            newWindowController.addSelectedNumbers(selectedNumbers);

            newWindowStage = new Stage();
            newWindowStage.setTitle("New Window");
            Scene scene = new Scene(root);
            newWindowStage.setScene(scene);
            newWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addingTicketToTicketBox() {
        int id = ticketManager.getTickets().size() + 1;
        double betAmount = betPerPlayAndDrawInput.getValue();
        Ticket ticket = new Ticket(id, new ArrayList<>(selectedNumbers), betAmount);
        ticketManager.addTicket(ticket);
        ticketManager.printTickets();
    }

    private void generateQRCode() {
        String selectedNumbers = getSelectedNumbersAsString();
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(selectedNumbers, BarcodeFormat.QR_CODE, 200, 200);
            Image qrCodeImage = createImageFromBitMatrix(bitMatrix);
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private Image createImageFromBitMatrix(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        WritableImage image = new WritableImage(width, height);

        // Fill image with bit matrix data
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setArgb(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return image;
    }

    private String getSelectedNumbersAsString() {
        StringBuilder numbersBuilder = new StringBuilder();
        for (javafx.scene.Node node : selectedNumsGridPane.getChildren()) {
            if (node instanceof Button) {
                numbersBuilder.append(((Button) node).getText()).append(",");
            }
        }

        if (numbersBuilder.length() > 0) {
            numbersBuilder.deleteCharAt(numbersBuilder.length() - 1);
        }
        return numbersBuilder.toString();
    }
}
