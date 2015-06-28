package play.learn.java.fx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.Light.Distant;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloEffects extends Application {
	
	Stage stage;
	Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
		
		scene = new Scene(new Group(), 840, 680);
		ObservableList<Node> content = ((Group)scene.getRoot()).getChildren();
		
		// perspective
		//content.add(perspective());
		
		// drop shadow
		//content.add(dropShadow());
		
		// blend mode
		//content.add(blendMode());
		
        /// Bloom
        //content.add(bloom());
        
        /// BoxBlur
        //content.add(boxBlur());
        
        /// DisplacementMap
        //content.add(displacementMap());
        
        /// InnerShadow
        //content.add(innerShadow());
        
        /// Lighting
        //content.add(lighting());
        
        /// MotionBlur
        //content.add(motionBlur());
        
        /// Reflection
        //content.add(reflection());
        
        /// GaussianBlur
        //content.add(gaussianBlur());
        
        /// DistantLight
        content.add(distantLight());
        
		stage.setScene(scene);
	}

	private Node distantLight() {
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-135.0f);
		light.setElevation(30.0f);
		
		Lighting l = new Lighting();
		l.setLight(light);
		l.setSurfaceScale(5.0f);
		
		final Text t = new Text();
		t.setText("Distant Light");
		t.setFill(Color.RED);
		t.setFont(Font.font("null", FontWeight.BOLD, 70));
		t.setX(10.0f);
		t.setY(50.0f);
		t.setTextOrigin(VPos.TOP);
		
		t.setEffect(l);
		
		final Rectangle r = new Rectangle();
		r.setFill(Color.BLACK);
		
		Group g = new Group();
		g.getChildren().add(r);
		g.getChildren().add(t);
		
		g.setTranslateY(460);
		
		return g;
	}

	private Node gaussianBlur() {
		Text t2 = new Text();
		t2.setX(10.0f);
		t2.setY(140.0f);
		t2.setCache(true);
		t2.setText("Gaussian Blur");
		t2.setFill(Color.RED);
		t2.setFont(Font.font("null", FontWeight.BOLD, 36));
		return t2;
	}

	private Node reflection() {
		Text t = new Text();
		t.setX(10.0f);
		t.setY(50.0f);
		t.setCache(true);
		t.setText("reflections on javafx...");
		t.setFill(Color.RED);
		t.setFont(Font.font("null", FontWeight.BOLD, 30));
		
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		t.setEffect(r);
		
		t.setTranslateY(400);
		
		return t;
		
	}

	private Node motionBlur() {
		Text t = new Text();
		t.setX(20.0f);
		t.setY(80.0f);
		t.setText("Motion Blur");
		t.setFill(Color.RED);
		t.setFont(Font.font("null", FontWeight.BOLD, 60));
		
		MotionBlur mb = new MotionBlur();
		mb.setRadius(15.0f);
		mb.setAngle(45.0f);
		
		t.setEffect(mb);
		
		t.setTranslateX(530);
		t.setTranslateY(100);
		
		return t;
		
	}

	private Node lighting() {
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-135.0f);
		
		Lighting l = new Lighting();
		l.setLight(light);
		l.setSurfaceScale(5.0f);
		
		Text t = new Text();
		t.setText("JavaFX\nLighting!");
		t.setFill(Color.RED);
		t.setFont(Font.font("null", FontWeight.BOLD, 70));
		t.setX(500.0f);
		t.setY(100.0f);
		t.setTextOrigin(VPos.TOP);
		
		t.setEffect(l);
		
		t.setTranslateX(0);
		t.setTranslateY(320);
		return t;
	}

	private Node innerShadow() {
		InnerShadow is = new InnerShadow();
		is.setOffsetX(2.0f);
		is.setOffsetY(2.0f);
		
		Text t = new Text();
		t.setEffect(is);
		t.setX(20);
		t.setY(100);
		t.setText("Inner Shadow");
		t.setFill(Color.RED);
		t.setFont(Font.font("null", FontWeight.BOLD, 80));
		
		t.setTranslateX(300);
		t.setTranslateY(300);
		
		return t;
	}

	private Node displacementMap() {
		int w = 220;
		int h = 100;
		FloatMap map = new FloatMap();
		map.setWidth(w);
		map.setHeight(h);
		
		for (int i = 0; i < w; i++) {
			double v = (Math.sin(i / 50.0 * Math.PI) - 0.5 ) / 40.0;
			for (int j = 0; j < h; j++) {
				map.setSamples(i, j, 0.0f , (float)v);
			}
		}
		Group g = new Group();
		DisplacementMap dm = new DisplacementMap();
		dm.setMapData(map);
		
		Rectangle r = new Rectangle();
		r.setX(20.0f);
		r.setY(20.0f);
		r.setWidth(w);
		r.setHeight(h);
		r.setFill(Color.BLUE);
		
		g.getChildren().add(r);
		
		Text t = new Text();
		t.setX(40.0f);
		t.setY(80.0f);
		t.setText("Wavy Text");
		t.setFill(Color.YELLOW);
		t.setFont(Font.font("null", FontWeight.BOLD, 36));
		
		g.getChildren().add(t);
		
		g.setEffect(dm);
		g.setCache(true);
		
		g.setTranslateX(400);
		g.setTranslateY(200);
		
		return g;
	}

	private Node boxBlur() {
		Text t = new Text();
		t.setText("Blurry Text!");t.setFill(Color.RED);
		t.setFont(Font.font("null", FontWeight.BOLD, 36));
		t.setX(10);
		t.setY(40);
		
		BoxBlur bb = new BoxBlur();
		bb.setWidth(5);
		bb.setHeight(5);
		bb.setIterations(3);
		
		t.setEffect(bb);
		t.setTranslateX(300);
		t.setTranslateY(100);
		return t;
	}

	private Node bloom() {
		Group g = new Group();
		
		Rectangle r = new Rectangle();
		r.setX(10);
		r.setY(10);
		r.setWidth(160);
		r.setHeight(80);
		r.setFill(Color.DARKBLUE);
		
		Text t = new Text();
		t.setText("Bloom!");
		t.setFill(Color.YELLOW);
		t.setFont(Font.font(null, FontWeight.BOLD, 36));
		t.setX(25);
		t.setY(65);
		
		g.setCache(true);
		//g.setEffect(new Bloom());
		Bloom bloom = new Bloom();
		bloom.setThreshold(1.0);
		g.setEffect(bloom);
		g.getChildren().add(r);
		g.getChildren().add(t);
		g.setTranslateX(350);
		return g;
	}

	private Node blendMode() {
		Rectangle r = new Rectangle();
		r.setX(590);
		r.setY(50);
        r.setWidth(50);
        r.setHeight(50);
        r.setFill(Color.BLUE);

        Circle c = new Circle();
        c.setFill(Color.RED);
        c.setCenterX(590);
        c.setCenterY(50);
        c.setRadius(25);
        c.setBlendMode(BlendMode.SRC_ATOP);

        Group g = new Group();
        g.setBlendMode(BlendMode.SRC_OVER);
        g.getChildren().add(r);
        g.getChildren().add(c);
        return g;
	}

	private Node perspective() {
		Group g = new Group();
		PerspectiveTransform pt = new PerspectiveTransform();
		pt.setUlx(10.0f);
        pt.setUly(10.0f);
        pt.setUrx(210.0f);
        pt.setUry(40.0f);
        pt.setLrx(210.0f);
        pt.setLry(60.0f);
        pt.setLlx(10.0f);
        pt.setLly(90.0f);
        
        g.setEffect(pt);
        g.setCache(true);

        Rectangle r = new Rectangle();
        r.setX(10.0f);
        r.setY(10.0f);
        r.setWidth(280.0f);
        r.setHeight(80.0f);
        r.setFill(Color.DARKBLUE);

        Text t = new Text();
        t.setX(20.0f);
        t.setY(65.0f);
        t.setText("Perspective");
        t.setFill(Color.RED);
        t.setFont(Font.font("null", FontWeight.BOLD, 36));

        g.getChildren().add(r);
        g.getChildren().add(t);
        return g;
		
	}

	private Node dropShadow() {
		Group g = new Group();
		
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0);
		ds.setOffsetY(3.0);
		ds.setColor(Color.GRAY);
		
		Text t = new Text();
		t.setEffect(ds);
		t.setCache(true);
		t.setX(20.0f);
		t.setY(70.0f);
		t.setFill(Color.RED);
		t.setText("JavaFX drop shadow effect");
		t.setFont(Font.font("null", FontWeight.BOLD, 32));
		
		DropShadow ds1 = new DropShadow();
		ds1.setOffsetX(4.0f);
		ds1.setOffsetY(4.0f);
		ds1.setColor(Color.CORAL);
		
		Circle c = new Circle();
		c.setEffect(ds1);
		c.setCenterX(50.0f);
		c.setCenterY(325.0f);
		c.setRadius(30.0f);
		c.setFill(Color.RED);
		c.setCache(true);
		
		g.getChildren().add(t);
		g.getChildren().add(c);
				
		return g;
		
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
