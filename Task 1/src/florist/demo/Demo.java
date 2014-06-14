package florist.demo;

import static florist.domain.Freshness.BAD;
import static florist.domain.Freshness.GOOD;
import static florist.domain.Freshness.MIDDLE;
import static florist.domain.Freshness.PERFECT;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import florist.domain.Bouquet;
import florist.domain.BouquetAccessory;
import florist.domain.Flower;
import florist.domain.Freshness;
import florist.domain.Utils;
import florist.domain.impl.BouquetAccessoryImpl;
import florist.domain.impl.BouquetImpl;
import florist.domain.impl.FlowerImpl;

public class Demo {
	private static final int SIZE = 10;
	private static final String[] FLOWER_NAMES = { "Frangipani", "Sweet Alyssum", "Chocolate Cosmos", "Wisteria", "Sweet Pea",
			"Lily-of-the-valley", "Gardenia", "Four o'clocks", "Jasmine", "Rose" };
	private static final int[] FLOWER_STEM_LENGTHS = { 20, 30, 20, 20, 25, 35, 40, 41, 37, 31 };
	private static final Freshness[] FLOWER_FRESHNESSES = { BAD, PERFECT, MIDDLE, MIDDLE, GOOD, PERFECT, PERFECT, GOOD, BAD, PERFECT };
	private static final String[] FLOWER_PRICES_STRING = { "50.50", "20.00", "25.39", "89.50", "101.30", "22.75", "23.58", "32.89", "99.99",
			"89.90" };
	private static final BigDecimal[] FLOWER_PRICES;

	static {
		FLOWER_PRICES = new BigDecimal[SIZE];
		for (int i = 0; i < SIZE; i++)
			FLOWER_PRICES[i] = new BigDecimal(FLOWER_PRICES_STRING[i]);
	}

	private static Bouquet bouquet;

	@BeforeClass
	public static void createBouquetWithFlowersAndAccessories() {
		List<Flower> flowerList = new ArrayList<Flower>(SIZE);
		// 1. Цветочница. Создать несколько объектов-цветов
		for (int i = 0; i < SIZE; i++) {
			Flower flower = new FlowerImpl(FLOWER_PRICES[i], FLOWER_NAMES[i], FLOWER_FRESHNESSES[i], FLOWER_STEM_LENGTHS[i]);
			flowerList.add(flower);
		}

		// 2. Собрать букет (используя аксессуары) с определением его стоимости.
		BouquetAccessory baStrasses = new BouquetAccessoryImpl(new BigDecimal("20.43"), "Strasses");
		BouquetAccessory baGlamorousWrapper = new BouquetAccessoryImpl(new BigDecimal("10.99"), "Glamorous Wrapper");
		List<BouquetAccessory> accessories = new ArrayList<BouquetAccessory>();
		accessories.add(baGlamorousWrapper);
		accessories.add(baStrasses);

		bouquet = new BouquetImpl("St. Valentine Day Bouquet", flowerList, accessories);
	}

	@Test
	public void testPriceOfBouquet() {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println("\nAutomatically defined price is: " + numberFormat.format(bouquet.getPrice()));
	}
	
	@Test
	public void testSortByFreshness(){
		// 3. Провести сортировку цветов в букете на основе уровня свежести.
		List<Flower> flowerList = Utils.sortByFreshness(bouquet);
		System.out.println("\nFlowers after sort by freshness:");
		print(flowerList);
	}
	
	@Test
	public void testFindFlowerByLengthInerval(){
		// 4. Найти цветок в букете, соответствующий заданному диапазону длин стеблей.
		List<Flower> flowerList = Utils.getFlowersWithStemLengthBetween(bouquet, 30, 35);
		System.out.println("\nFlowers with stem lengths 30-35: ");
		print(flowerList);
	}
	
	public static <T> void print(List<T> list){
		for (T	t: list)
			System.out.println(t);
	}

}
