package play.learn.java.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class Time {
	
	public static void NotHourOffsetTimezone() {
		Instant instant = Instant.now();
		ZonedDateTime current = instant.atZone(ZoneId.systemDefault());
		System.out.printf("Current time is %s%n%n", current);
		
		System.out.printf("%10s %20s %13s%n", "offset", "ZoneId", "Time");
		ZoneId.getAvailableZoneIds().stream()
		.map(ZoneId::of)
		.filter(zoneId -> { 
			ZoneOffset offset = instant.atZone(zoneId).getOffset();
			return offset.getTotalSeconds() % (60 * 60) != 0;
		})
		.sorted(comparingInt(zoneId -> instant.atZone(zoneId).getOffset().getTotalSeconds()))
		.forEach(zoneId -> {
			ZonedDateTime zdt = current.withZoneSameInstant(zoneId);
			System.out.printf("%10s %25s %10s%n", zdt.getOffset(), zoneId, zdt.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
		});
	}
	
	public static void AntarticaTZ() {
		Instant now = Instant.now();
		ZoneId.getAvailableZoneIds().stream()
		.filter(id -> id.contains("Antarctica"))
		.map(id -> now.atZone(ZoneId.of(id)))
		.sorted(Comparator.comparingInt(zoneId -> zoneId.getOffset().getTotalSeconds() ) )
		.collect(Collectors.toList())
		.forEach(zdt -> System.out.printf("%s: %s%n", zdt.getOffset(), zdt.getZone()));
		
	}

	public static void main(String[] args) {
		AntarticaTZ();
	}

}
