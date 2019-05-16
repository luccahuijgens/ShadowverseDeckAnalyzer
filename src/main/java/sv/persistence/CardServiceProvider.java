package sv.persistence;

public class CardServiceProvider {
	private static CardService cardService = CardService.getInstance();

	private CardServiceProvider() {
	}

	public static CardService getCardService() {
		return cardService;
	}
}
