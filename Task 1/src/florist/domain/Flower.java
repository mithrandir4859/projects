package florist.domain;


public interface Flower extends Merchandise{

	Freshness getFreshness();

	void setFreshness(Freshness freshness);

	int getStemLength();

	void setStemLength(int length);

}
