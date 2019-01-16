package play.learn.java.design.observer1;

public class GWeather extends Observable<GWeather, Race, WeatherType> {

	private WeatherType currentWeather;

	public GWeather() {
		currentWeather = WeatherType.SUNNY;
	}

	/**
	 * Makes time pass for weather
	 */
	public void timePasses() {
		WeatherType[] enumValues = WeatherType.values();
		currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length];
		System.out.printf("The weather changed to %s.", currentWeather);
		notifyObservers(currentWeather);
	}
}
