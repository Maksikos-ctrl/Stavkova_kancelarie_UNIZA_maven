package com.stavkova.kancelarie;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class NewWindowController {
    @FXML
    private Label ticketIdLabel;
    
    @FXML
    private VBox selectedNumbersBox;
    
    @FXML
    private Spinner<Double> betAmountSpinner;
    
    private int ticketId;
    private List<Integer> selectedNumbers = new ArrayList<>();

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
        ticketIdLabel.setText("Ticket ID: " + ticketId);
    }
    

    public void initialize() {
        betAmountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.1, Double.MAX_VALUE, 1, 0.1));
    }

    public void addSelectedNumber(int number) {
        selectedNumbers.add(number);
        updateSelectedNumbersLabel();
    }
    

    private void updateSelectedNumbersLabel() {
        StringBuilder sb = new StringBuilder();
        for (int number : selectedNumbers) {
            sb.append(number).append(", ");
        }
        // Remove the trailing comma and space
        String selectedNumsString = sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        Label selectedNumbersLabel = new Label(selectedNumsString);
        selectedNumbersBox.getChildren().add(selectedNumbersLabel);
    }

    @FXML
    private void addBet() {
        double betAmount = betAmountSpinner.getValue();
        if (betAmount <= 0) {
            showAlert(Alert.AlertType.ERROR, "Invalid Bet", "Bet amount must be greater than 0.");
            return;
        }
      
        System.out.println("Bet added to ticket " + ticketId + ": " + betAmount);
    }

    @FXML
    private void play() {
        System.out.println("Selected numbers for ticket " + ticketId + ": " + selectedNumbers);
        double betAmount = betAmountSpinner.getValue();
        if (betAmount <= 0) {
            showAlert(Alert.AlertType.ERROR, "Invalid Bet", "Bet amount must be greater than 0.");
            return;
        }
        
        boolean win = checkWinningCondition(selectedNumbers); 
        if (win) {
            showAlert(Alert.AlertType.INFORMATION, "Result", "Congratulations! You've won!");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Result", "Sorry, you didn't win this time.");
        }
    }

    private boolean checkWinningCondition(List<Integer> selectedNumbers) {
        Loterei loterei = new Loterei(8, 10); 
        int winningNumber = loterei.getWinningNumber(); 
        int[] winningBets = loterei.checkWinningBets(winningNumber); 
        for (int num : selectedNumbers) {
            if (num < 1 || num > winningBets.length || winningBets[num - 1] == 0) { 
                return false;   
            }
        }
        return true;
    }
    
    
    

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) ticketIdLabel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void addSelectedNumbers(List<Integer> selectedNumbers2) {
        selectedNumbers.addAll(selectedNumbers2);
        updateSelectedNumbersLabel();
    }
}
