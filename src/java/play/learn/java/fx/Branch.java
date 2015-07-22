package play.learn.java.fx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.transform.Rotate;

import static java.lang.Math.*;
import static play.learn.java.fx.Util.addChildToParent;
import static play.learn.java.fx.RandomUtil.*;

public class Branch extends Group {
	
	public final Line base;
	public double length;
	public double globalH = 0; // used for change color of top leaves
	
	public enum Type {
		TOP, LEFT, RIGHT
	}
 	
	public Branch() { // default constructor for root branch
		base = new Line();
		getChildren().add(base);
		setTranslateY(30);
		length = 150;
		globalH = 0;
		setBranchStyle(0);
	}
	
	public Branch(Branch parentBranch, Type type, int depth) {
		this();
		SimpleDoubleProperty locAngle = new SimpleDoubleProperty(0);
		SimpleDoubleProperty globalAngle = new SimpleDoubleProperty(90);
		globalAngle.bind(locAngle.add(globalAngle.get()));
		double transY = 0;  // place of beginning child branch
		switch (type) {
		case TOP:  // creates top branch
			transY = parentBranch.length;
			length = parentBranch.length * 0.8;
			locAngle.set(getRandom(10));
			break;
		case LEFT: // create left branch
		case RIGHT:  // create right branch
			transY = parentBranch.length - getGaussianRandom(0, parentBranch.length, parentBranch.length / 10, parentBranch.length / 10);
			locAngle.set(getGaussianRandom(35, 10) * (Type.LEFT == type ? 1 : -1));
			if ((0 > globalAngle.get() || globalAngle.get() > 180) && depth < 4 ) {
				length = parentBranch.length * getGaussianRandom(0.3, 0.1); // branches pointed down are shorter
			} else {
				length = parentBranch.length * 0.6;
			}
			break;
		}
		setTranslateY(transY);
		getTransforms().add(new Rotate(locAngle.get(), 0 , 0)); // rotate branch to local angle relative to parent branch
		globalH = getTranslateY() * cos(PI / 2 - globalAngle.get() * PI / 180) + parentBranch.globalH;
		setBranchStyle(depth);
		addChildToParent(parentBranch, this);
	}
	
	private void setBranchStyle(int depth) {
		base.setStroke(Color.color(0.4, 0.1, 0.1, 1));
		
		if (depth < 5) { // line rendering optimization
			base.setStrokeLineJoin(StrokeLineJoin.ROUND);
			base.setStrokeLineCap(StrokeLineCap.ROUND);
		}
		base.setStrokeWidth(0); // trick to hide lines
	}

}
