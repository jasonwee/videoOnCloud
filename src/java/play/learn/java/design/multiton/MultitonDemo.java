package play.learn.java.design.multiton;

// https://java-design-patterns.com/patterns/multiton/
public class MultitonDemo {

	public static void main(String[] args) {
		// eagerly initialized multiton
		System.out.format("KHAMUL=%s", Nazgul.getInstance(NazgulName.KHAMUL));
		System.out.format("MURAZOR=%s", Nazgul.getInstance(NazgulName.MURAZOR));
		System.out.format("DWAR=%s", Nazgul.getInstance(NazgulName.DWAR));
		System.out.format("JI_INDUR=%s", Nazgul.getInstance(NazgulName.JI_INDUR));
		System.out.format("AKHORAHIL=%s", Nazgul.getInstance(NazgulName.AKHORAHIL));
		System.out.format("HOARMURATH=%s", Nazgul.getInstance(NazgulName.HOARMURATH));
		System.out.format("ADUNAPHEL=%s", Nazgul.getInstance(NazgulName.ADUNAPHEL));
		System.out.format("REN=%s", Nazgul.getInstance(NazgulName.REN));
		System.out.format("UVATHA=%s", Nazgul.getInstance(NazgulName.UVATHA));

		// enum multiton
		System.out.format("KHAMUL=%s", NazgulEnum.KHAMUL);
		System.out.format("MURAZOR=%s", NazgulEnum.MURAZOR);
		System.out.format("DWAR=%s", NazgulEnum.DWAR);
		System.out.format("JI_INDUR=%s", NazgulEnum.JI_INDUR);
		System.out.format("AKHORAHIL=%s", NazgulEnum.AKHORAHIL);
		System.out.format("HOARMURATH=%s", NazgulEnum.HOARMURATH);
		System.out.format("ADUNAPHEL=%s", NazgulEnum.ADUNAPHEL);
		System.out.format("REN=%s", NazgulEnum.REN);
		System.out.format("UVATHA=%s", NazgulEnum.UVATHA);

	}
}
