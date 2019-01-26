package play.learn.java.design.bridge1;

public interface Weapon {

	void wield();

	void swing();

	void unwield();

	Enchantment getEnchantment();
}
