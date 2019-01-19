package sv.persistence;

public class CardServiceProvider {
private static CardService cardService=new CardService();

	public static CardService getCardService() {
		return cardService;
	}
}
