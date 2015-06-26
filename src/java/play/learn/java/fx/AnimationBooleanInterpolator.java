package play.learn.java.fx;

import javafx.animation.Interpolator;

public class AnimationBooleanInterpolator extends Interpolator {

	@Override
	protected double curve(double t) {
		return Math.abs(0.5 - t ) * 2;
	}

}
