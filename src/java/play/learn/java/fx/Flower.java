package play.learn.java.fx;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;
import static java.lang.Math.random;

public class Flower extends Group {
	
	private final int N = 5;
	private final List<Ellipse> petals = new ArrayList<>(2 * N + 1);
	
	public Flower() {
		Color color = Color.color(1, random() * 0.4 + 0.6, 1);
		final int petalsNum = 2 * N;
		for (int i = 0; i < petalsNum; i++) {
			Ellipse petal = new Ellipse(2, 5);
			
			if (i % 2 == 0) {
				petal.setFill(color);
			} else {
				petal.setFill(color.saturate());
			}
			
			petal.getTransforms().add(new Translate(0, 5));
			petal.setRotate(360 / petalsNum * i);
			getChildren().add(petal);
			petals.add(petal);			
		}
		Ellipse center = new Ellipse(2, 2);
		center.setFill(Color.PINK);
		getChildren().add(center);
		petals.add(center);
		
		for (Ellipse petal : petals) {
			petal.setOpacity(0); // trick to hide flowers
		}
	}
	
	public Flower(final Branch branch) {
		this();
		setTranslateY(branch.length / 2);
	}
	
	public List<Ellipse> getPetals() {
		return petals;
	}
}
