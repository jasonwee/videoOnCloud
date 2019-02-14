package play.learn.java.design.hexagonal;

import com.google.inject.Guice;
import com.google.inject.Injector;

// https://java-design-patterns.com/patterns/hexagonal/
public class HexagonalDemo {

	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new LotteryTestingModule());

		// start new lottery round
		LotteryAdministration administration = injector.getInstance(LotteryAdministration.class);
		administration.resetLottery();

		// submit some lottery tickets
		LotteryService service = injector.getInstance(LotteryService.class);
		SampleData.submitTickets(service, 20);

		// perform lottery
		administration.performLottery();
	}

}
