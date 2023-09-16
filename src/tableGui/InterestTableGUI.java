package tableGui;

import java.text.NumberFormat;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tableModel.Interest;
//collaborated with Parthav Poudel

public class InterestTableGUI extends Application {

	TextArea displayArea;
	Button simpInt, compInt, bothInt;
	TextField principal, rate;
	Slider numYears;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//scene params
		int sceneWidth = 600, sceneHeight = 320;
		int verSpaceBetweenNodes = 8, horSpaceBetweenNodes = 8;
		int paneBorderTop = 20, paneBorderRight = 20;
		int paneBorderBottom = 20, paneBorderLeft = 20;
		
		displayArea = new TextArea();
		displayArea.setEditable(false);
		displayArea.setWrapText(true);
		
		//top half of display / layout
		ScrollPane scrollPane = new ScrollPane(displayArea);
		scrollPane.setPrefViewportHeight(sceneHeight / 2);
		scrollPane.setFitToWidth(true);
		
		//bottom half of display / layout
		GridPane gridPane = new GridPane();  
		gridPane.setHgap(horSpaceBetweenNodes);
		gridPane.setVgap(verSpaceBetweenNodes);
		gridPane.setPadding(new Insets(paneBorderTop, paneBorderRight,
				paneBorderBottom, paneBorderLeft));
		
		//buttons for interest
		simpInt = new Button("Simple Interest");
		compInt = new Button("Compound Interest");
		bothInt = new Button("Both Interests");
		
		//adding buttons to gridpane
		gridPane.add(simpInt, 0, 2);
		gridPane.add(compInt, 1, 2);
		gridPane.add(bothInt, 2, 2);
		GridPane.setHalignment(simpInt, HPos.CENTER); 
		GridPane.setHalignment(compInt, HPos.CENTER); 
		GridPane.setHalignment(bothInt, HPos.CENTER); 
		
		//textfields + labels for principal and rate
		principal = new TextField();
		rate = new TextField();
		Label principalLab = new Label("Principal:");
		Label rateLab = new Label("Rate (Percentage):");
		
		//adding above to grid
		gridPane.add(principalLab, 0, 0);
		gridPane.add(principal, 1, 0);
		gridPane.add(rateLab, 2, 0);
		gridPane.add(rate, 3, 0);
		GridPane.setHalignment(principalLab, HPos.RIGHT);
		GridPane.setHalignment(rateLab, HPos.RIGHT);
		
		//slider + label for years
		numYears = new Slider();
		numYears.setMin(1);
		numYears.setMax(25);
		numYears.setSnapToTicks(true);
		numYears.setMajorTickUnit(4); 
		numYears.setBlockIncrement(1);
		numYears.setShowTickMarks(true);
		numYears.setShowTickLabels(true);
		Label numYearsLab = new Label("Number of Years:");
		
		//adding above to grid
		gridPane.add(numYearsLab, 0, 1);
		gridPane.add(numYears, 1, 1, 2, 1);
		
		//calculating simple interest with a lambda expression then adding to
		//scrollpane display area
		simpInt.setOnAction(e -> {
			String principalStr = String.format("%.2f", Double.valueOf(principal.getText()));
			double principalDub = Double.parseDouble(principalStr);
			double rateDub = Double.valueOf(rate.getText());
			if (principalDub < 0 || rateDub < 0) {
				displayArea.setText("Invalid input");
			} else {
				int years = (int)numYears.getValue();
				String toPrint = "Principal: $" + principalStr + ", Rate: " + rateDub
						+ "\nYear, Simple Interest Amount";
				for (int i = 0; i < years; i++) {
					toPrint += "\n" + (i + 1) + "-->" + 
							NumberFormat.getCurrencyInstance().format(Interest.simpleCalc(principalDub, rateDub, (i + 1)));
				}
				displayArea.setText(toPrint);
			}
		});
		
		//compound interest calculation handled by private inner class (below)
		compInt.setOnAction(new compoundCalculation());
		
		//both interests calculation handled by anonymous inner class
		bothInt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String principalStr = String.format("%.2f", Double.valueOf(principal.getText()));
				double principalDub = Double.parseDouble(principalStr);
				double rateDub = Double.valueOf(rate.getText());
				if (principalDub < 0 || rateDub < 0) {
					displayArea.setText("Invalid input");
				} else {
					int years = (int)numYears.getValue();
					String toPrint = "Principal: $" + principalStr + ", Rate: " + rateDub
							+ "\nYear, Simple Interest Amount, Compound Interest Amount";
					for (int i = 0; i < years; i++) {
						toPrint += "\n" + (i + 1) + "-->" + 
								NumberFormat.getCurrencyInstance().format(Interest.simpleCalc(principalDub, rateDub, (i + 1)))
								+ "-->" + NumberFormat.getCurrencyInstance().format(Interest.compoundCalc(principalDub, rateDub, (i + 1)));
					}
					displayArea.setText(toPrint);
				}
			}
		});
		
		//adding scrollpane and gridpane to a VBox to put into scene
		VBox display = new VBox(scrollPane, gridPane);
		
		//creating scene with vbox layout
		Scene sc = new Scene(display, sceneWidth, sceneHeight);
		primaryStage.setScene(sc);
		primaryStage.setTitle("Interest Table Calculator");
		primaryStage.show();
		
	}
	
	//private inner class that handles the computation of the compound interest
	//and adds it to the scrollpane display area
	private class compoundCalculation implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			String principalStr = String.format("%.2f", Double.valueOf(principal.getText()));
			double principalDub = Double.parseDouble(principalStr);
			double rateDub = Double.valueOf(rate.getText());
			if (principalDub < 0 || rateDub < 0) {
				displayArea.setText("Invalid input");
			} else {
				int years = (int)numYears.getValue();
				String toPrint = "Principal: $" + principalStr + ", Rate: " + rateDub
						+ "\nYear, Compound Interest Amount";
				for (int i = 0; i < years; i++) {
					toPrint += "\n" + (i + 1) + "-->" + 
							NumberFormat.getCurrencyInstance().format(Interest.compoundCalc(principalDub, rateDub, (i + 1)));
				}
				displayArea.setText(toPrint);
			}
		}
	}
	
	public static void main(String[] args) {
		launch();
		//Application.launch(args);
	}

}
