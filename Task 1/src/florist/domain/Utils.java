package florist.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
	private Utils() {
	}

	public static List<Flower> sortByFreshness(List<Flower> flowerList) {
		List<Flower> forSort = new ArrayList<Flower>(flowerList);
		Comparator<Flower> comparatorByFreshness = new Comparator<Flower>(){
			@Override
			public int compare(Flower flower0, Flower flower1) {
				return flower0.getFreshness().ordinal() - flower1.getFreshness().ordinal();
			}
		};
		Collections.sort(forSort, comparatorByFreshness);
		return forSort;
	}

	public static List<Flower> sortByFreshness(Bouquet bouquet) {
		return sortByFreshness(bouquet.getFlowers());
	}
	
	public static List<Flower> getFlowersWithStemLengthBetween(List<Flower> flowerList, int min, int max) {
		List<Flower> filteredList = new ArrayList<Flower>();
		for (Flower flower: flowerList)
			if (flower.getStemLength() >= min && flower.getStemLength() <= max)
				filteredList.add(flower);
		return filteredList;
	}

	public static List<Flower> getFlowersWithStemLengthBetween(Bouquet bouquet, int min, int max) {
		return getFlowersWithStemLengthBetween(bouquet.getFlowers(), min, max);
	}
	
	

}
