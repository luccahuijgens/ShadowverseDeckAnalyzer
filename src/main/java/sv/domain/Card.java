package sv.domain;

public class Card {
private int cardID;
private int foilCardID;
private int cardSetID;
private String cardName;
private int isFoil;
private int charType;
private int clan;
private String tribeName;
private String skillDisc;
private String evoSkillDisc;
private int cost;
private int atk;
private int life;
private int evoAtk;
private int evoLife;
private int rarity;
private int getRedEther;
private int useRedEther;
private String description;
private String evoDescription;
private String cv;
private int baseCardID;
private String tokens;
private int normalCardID;
private int formatType;
private int restrictedCount;

public Card() {}

public Card(int cardID, int foilCardID, int cardSetID, String cardName, int isFoil, int charType, int clan,
		String tribeName, String skillDisc, String evoSkillDisc, int cost, int atk, int life, int evoAtk, int evoLife,
		int rarity, int getRedEther, int useRedEther, String description, String evoDescription, String cv,
		int baseCardID, String tokens, int normalCardID, int formatType, int restrictedCount) {
	super();
	this.cardID = cardID;
	this.foilCardID = foilCardID;
	this.cardSetID = cardSetID;
	this.cardName = cardName;
	this.isFoil = isFoil;
	this.charType = charType;
	this.clan = clan;
	this.tribeName = tribeName;
	this.skillDisc = skillDisc;
	this.evoSkillDisc = evoSkillDisc;
	this.cost = cost;
	this.atk = atk;
	this.life = life;
	this.evoAtk = evoAtk;
	this.evoLife = evoLife;
	this.rarity = rarity;
	this.getRedEther = getRedEther;
	this.useRedEther = useRedEther;
	this.description = description;
	this.evoDescription = evoDescription;
	this.cv = cv;
	this.baseCardID = baseCardID;
	this.tokens = tokens;
	this.normalCardID = normalCardID;
	this.formatType = formatType;
	this.restrictedCount = restrictedCount;
}

public int getCardID() {
	return cardID;
}

public void setCardID(int cardID) {
	this.cardID = cardID;
}

public int getFoilCardID() {
	return foilCardID;
}

public void setFoilCardID(int foilCardID) {
	this.foilCardID = foilCardID;
}

public int getCardSetID() {
	return cardSetID;
}

public void setCardSetID(int cardSetID) {
	this.cardSetID = cardSetID;
}

public String getCardName() {
	return cardName;
}

public void setCardName(String cardName) {
	this.cardName = cardName;
}

public int getIsFoil() {
	return isFoil;
}

public void setIsFoil(int isFoil) {
	this.isFoil = isFoil;
}

public int getCharType() {
	return charType;
}

public void setCharType(int charType) {
	this.charType = charType;
}

public int getClan() {
	return clan;
}

public void setClan(int clan) {
	this.clan = clan;
}

public String getTribeName() {
	return tribeName;
}

public void setTribeName(String tribeName) {
	this.tribeName = tribeName;
}

public String getSkillDisc() {
	return skillDisc;
}

public void setSkillDisc(String skillDisc) {
	this.skillDisc = skillDisc;
}

public String getEvoSkillDisc() {
	return evoSkillDisc;
}

public void setEvoSkillDisc(String evoSkillDisc) {
	this.evoSkillDisc = evoSkillDisc;
}

public int getCost() {
	return cost;
}

public void setCost(int cost) {
	this.cost = cost;
}

public int getAtk() {
	return atk;
}

public void setAtk(int atk) {
	this.atk = atk;
}

public int getLife() {
	return life;
}

public void setLife(int life) {
	this.life = life;
}

public int getEvoAtk() {
	return evoAtk;
}

public void setEvoAtk(int evoAtk) {
	this.evoAtk = evoAtk;
}

public int getEvoLife() {
	return evoLife;
}

public void setEvoLife(int evoLife) {
	this.evoLife = evoLife;
}

public int getRarity() {
	return rarity;
}

public void setRarity(int rarity) {
	this.rarity = rarity;
}

public int getGetRedEther() {
	return getRedEther;
}

public void setGetRedEther(int getRedEther) {
	this.getRedEther = getRedEther;
}

public int getUseRedEther() {
	return useRedEther;
}

public void setUseRedEther(int useRedEther) {
	this.useRedEther = useRedEther;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getEvoDescription() {
	return evoDescription;
}

public void setEvoDescription(String evoDescription) {
	this.evoDescription = evoDescription;
}

public String getCv() {
	return cv;
}

public void setCv(String cv) {
	this.cv = cv;
}

public int getBaseCardID() {
	return baseCardID;
}

public void setBaseCardID(int baseCardID) {
	this.baseCardID = baseCardID;
}

public String getTokens() {
	return tokens;
}

public void setTokens(String tokens) {
	this.tokens = tokens;
}

public int getNormalCardID() {
	return normalCardID;
}

public void setNormalCardID(int normalCardID) {
	this.normalCardID = normalCardID;
}

public int getFormatType() {
	return formatType;
}

public void setFormatType(int formatType) {
	this.formatType = formatType;
}

public int getRestrictedCount() {
	return restrictedCount;
}

public void setRestrictedCount(int restrictedCount) {
	this.restrictedCount = restrictedCount;
}


}
