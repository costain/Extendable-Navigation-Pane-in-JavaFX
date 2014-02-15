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

		stage.setTitle("Extendable search pane demo");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void initialize() {
		double widthInitial = 200;
		double heightInitial = 200;
		clipRect = new Rectangle();
		clipRect.setWidth(widthInitial);
		clipRect.setHeight(0);
		clipRect.translateYProperty().set(heightInitial);
		extendableNavigationPane.setClip(clipRect);
		extendableNavigationPane.translateYProperty().set(heightInitial);
		extendableNavigationPane.prefHeightProperty().set(0);

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

	}

	@FXML
	public void toggleExtendableSearch() {

		clipRect.setWidth(extendableNavigationPane.getWidth());

		if (clipRect.heightProperty().get() != 0) {

			System.out.println("Hding pane ... ");

			// Animation for hiding the pane..
			Timeline timelineUp = new Timeline();

			// Animation of sliding the search pane up, implemented via
			// clipping.
			final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
			final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), -extendableNavigationPane.getHeight());

			// The actual movement of the search pane. This makes the table
			// grow.
			final KeyValue kvUp4 = new KeyValue(extendableNavigationPane.prefHeightProperty(), 0);
			final KeyValue kvUp3 = new KeyValue(extendableNavigationPane.translateYProperty(), extendableNavigationPane.getHeight());

			final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2, kvUp3, kvUp4);
			timelineUp.getKeyFrames().add(kfUp);
			timelineUp.play();
		} else {

			System.out.println("Showing pane ... ");

			// Animation for showing the pane completely
			Timeline timelineDown = new Timeline();

			// Animation for sliding the search pane down. No change in size,
			// just making the visible part of the pane
			// bigger.
			final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), extendableNavigationPane.getHeight());
			final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);

			// Growth of the pane.
			final KeyValue kvDwn4 = new KeyValue(extendableNavigationPane.prefHeightProperty(), extendableNavigationPane.getHeight());
			final KeyValue kvDwn3 = new KeyValue(extendableNavigationPane.translateYProperty(), 0);

			final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), createBouncingEffect(extendableNavigationPane.getHeight()), kvDwn1, kvDwn2,
					kvDwn3, kvDwn4);
			timelineDown.getKeyFrames().add(kfDwn);

			timelineDown.play();
		}
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
