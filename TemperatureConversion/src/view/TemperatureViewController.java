package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import model.TemperatureModel;
import time.RunnableClock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TemperatureViewController
{
  @FXML private TextField textInput;

  @FXML private Label labelTimer;

  @FXML private Label labelOutput;

  private TemperatureModel model;
  private Region root;
  public TemperatureViewController()
  {
  }

  public void init(ViewHandler viewHandler, TemperatureModel model, Region root)
  {
    this.model = model;
    this.root = root;
    RunnableClock clock = new RunnableClock(this);
    Thread clockThread = new Thread(clock);
    clockThread.setDaemon(true);
    clockThread.start();
  }

  public void reset()
  {
    textInput.setText("");
  }

  public Region getRoot()
  {
    return root;
  }

  public void showTime(LocalTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    Platform.runLater(() -> labelTimer.setText(formatter.format(time)));
  }

  @FXML private void toCelsius()
  {
    try
    {
      double value = Double.parseDouble(textInput.getText());
      double result = model.toCelsius(value);
      labelOutput.setText(
          textInput.getText() + " fahrenheit = " + result + " celsius");
      reset();
    }
    catch (Exception e)
    {
      labelOutput.setText("Error in input");
    }
  }

  @FXML private void toFahrenheit()
  {
    try
    {
      double value = Double.parseDouble(textInput.getText());
      double result = model.toFahrenheit(value);
      labelOutput.setText(
          textInput.getText() + " celsius = " + result + " fahrenheit");
      reset();
    }
    catch (Exception e)
    {
      labelOutput.setText("Error in input");
    }
  }
}