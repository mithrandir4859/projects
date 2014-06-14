package florist.domain;

import java.util.List;

public interface Bouquet extends Merchandise {

	List<Flower> getFlowers();

	List<BouquetAccessory> getAccessories();

}
