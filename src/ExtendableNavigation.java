import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExtendableNavigation extends Application {

	private static final int deltaXNavButton1 = 20;
	private static final int deltaXNavButton2 = 10;
	private static final int deltaXNavButton3 = -10;
	private static final int deltaXNavButton4 = -20;

	@FXML
	private AnchorPane extendableNavigationPane;

	@FXML
	private Button navButton1;

	@FXML
	private Button navButton2;

	@FXML
	private Button navButton3;

	@FXML
	private Button navButton4;

	private Rectangle clipRect;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("extendableNavigation.fxml"));

		Scene scene = new Scene(root, 600, 400);

		stage.setTitle("Extendable navigation pane demo");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void initialize() {
		clipRect = new Rectangle();
		clipRect.setWidth(extendableNavigationPane.getPrefWidth());

		Image imageNavButton1 = new Image(getClass().getResourceAsStream("16px-Asclepius_staff.svg.png"));
		navButton1.setGraphic(new ImageView(imageNavButton1));
		navButton1.setContentDisplay(ContentDisplay.TOP);

		Image imageNavButton2 = new Image(getClass().getResourceAsStream("32px-Simple_crossed_circle.svg.png"));
		navButton2.setGraphic(new ImageView(imageNavButton2));
		navButton2.setContentDisplay(ContentDisplay.TOP);

		Image imageNavButton3 = new Image(getClass().getResourceAsStream("32px-Sinnbild_Autobahnkreuz.svg.png"));
		navButton3.setGraphic(new ImageView(imageNavButton3));
		navButton3.setContentDisplay(ContentDisplay.TOP);

		Image imageNavButton4 = new Image(getClass().getResourceAsStream("32px-Yin_yang.svg.png"));
		navButton4.setGraphic(new ImageView(imageNavButton4));
		navButton4.setContentDisplay(ContentDisplay.TOP);

		hidePane();
	}

	@FXML
	private void showPane() {
		System.out.println("Showing pane ... ");

		// Animation for showing the pane completely
		Timeline timelineDown = new Timeline();

		final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), extendableNavigationPane.getHeight());
		final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
		final KeyValue kvDwn3 = new KeyValue(extendableNavigationPane.translateYProperty(), 0);
		final KeyFrame kfDwn = new KeyFrame(Duration.millis(100), createBouncingEffect(extendableNavigationPane.getHeight()), kvDwn1, kvDwn2,
				kvDwn3);

		// Animation for moving button 1
		final KeyValue kvB1 = new KeyValue(navButton1.translateXProperty(), -deltaXNavButton1);
		final KeyFrame kfB1 = new KeyFrame(Duration.millis(200), kvB1);

		// Animation for moving button 2
		final KeyValue kvB2 = new KeyValue(navButton2.translateXProperty(), -deltaXNavButton2);
		final KeyFrame kfB2 = new KeyFrame(Duration.millis(200), kvB2);

		// Animation for moving button 3
		final KeyValue kvB3 = new KeyValue(navButton3.translateXProperty(), -deltaXNavButton3);
		final KeyFrame kfB3 = new KeyFrame(Duration.millis(200), kvB3);

		// Animation for moving button 1
		final KeyValue kvB4 = new KeyValue(navButton4.translateXProperty(), -deltaXNavButton4);
		final KeyFrame kfB4 = new KeyFrame(Duration.millis(200), kvB4);

		timelineDown.getKeyFrames().addAll(kfDwn, kfB1, kfB2, kfB3, kfB4);
		timelineDown.play();
	}

	@FXML
	private void hidePane() {
		System.out.println("Hding pane ... ");

		// Animation for hiding the pane..
		Timeline timelineUp = new Timeline();

		final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 55);
		final KeyValue kvUp2 = new KeyValue(extendableNavigationPane.translateYProperty(), 10);
		final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2);

		// Animation for moving button 1
		final KeyValue kvB1 = new KeyValue(navButton1.translateXProperty(), deltaXNavButton1);
		final KeyFrame kfB1 = new KeyFrame(Duration.millis(200), kvB1);

		final KeyValue kvB2 = new KeyValue(navButton2.translateXProperty(), deltaXNavButton2);
		final KeyFrame kfB2 = new KeyFrame(Duration.millis(200), kvB2);

		final KeyValue kvB3 = new KeyValue(navButton3.translateXProperty(), deltaXNavButton3);
		final KeyFrame kfB3 = new KeyFrame(Duration.millis(200), kvB3);

		final KeyValue kvB4 = new KeyValue(navButton4.translateXProperty(), deltaXNavButton4);
		final KeyFrame kfB4 = new KeyFrame(Duration.millis(200), kvB4);

		timelineUp.getKeyFrames().addAll(kfUp, kfB1, kfB2, kfB3, kfB4);
		timelineUp.play();
	}

	private EventHandler<ActionEvent> createBouncingEffect(double height) {
		final Timeline timelineBounce = new Timeline();
		timelineBounce.setCycleCount(2);
		timelineBounce.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (height - 15));
		final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		final KeyValue kv3 = new KeyValue(extendableNavigationPane.translateYProperty(), -15);
		final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		timelineBounce.getKeyFrames().add(kf1);

		// Event handler to call bouncing effect after the scroll down is
		// finished.
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timelineBounce.play();
			}
		};
		return handler;
	}
}
