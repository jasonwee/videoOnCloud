package play.learn.java.design.layers;

public class CakeViewImpl implements View {

	private CakeBakingService cakeBakingService;

	public CakeViewImpl(CakeBakingService cakeBakingService) {
		this.cakeBakingService = cakeBakingService;
	}

	public void render() {
		cakeBakingService.getAllCakes().forEach(cake -> System.out.println(cake.toString()));
	}

}
