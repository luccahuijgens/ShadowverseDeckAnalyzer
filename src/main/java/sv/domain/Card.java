package sv.domain;

public class Card {
private int card_id;
private int foil_card_id;
private int card_set_id;
private String card_name;
private int is_foil;
private int char_type;
private int clan;
private String tribe_name;
private String skill_disc;
private String evo_skill_disc;
private int cost;
private int atk;
private int life;
private int evo_atk;
private int evo_life;
private int rarity;
private int get_red_ether;
private int use_red_ether;
private String description;
private String evo_description;
private String cv;
private int base_card_id;
private String tokens;
private int normal_card_id;
private int format_type;
private int restricted_count;

public Card() {}

public Card(int card_id, int foil_card_id, int card_set_id, String card_name, int is_foil, int char_type, int clan,
		String tribe_name, String skill_disc, String evo_skill_disc, int cost, int atk, int life, int evo_atk,
		int evo_life, int rarity, int get_red_ether, int use_red_ether, String description, String evo_description,
		String cv, int base_card_id, String tokens, int normal_card_id, int format_type, int restricted_count) {
	super();
	this.card_id = card_id;
	this.foil_card_id = foil_card_id;
	this.card_set_id = card_set_id;
	this.card_name = card_name;
	this.is_foil = is_foil;
	this.char_type = char_type;
	this.clan = clan;
	this.tribe_name = tribe_name;
	this.skill_disc = skill_disc;
	this.evo_skill_disc = evo_skill_disc;
	this.cost = cost;
	this.atk = atk;
	this.life = life;
	this.evo_atk = evo_atk;
	this.evo_life = evo_life;
	this.rarity = rarity;
	this.get_red_ether = get_red_ether;
	this.use_red_ether = use_red_ether;
	this.description = description;
	this.evo_description = evo_description;
	this.cv = cv;
	this.base_card_id = base_card_id;
	this.tokens = tokens;
	this.normal_card_id = normal_card_id;
	this.format_type = format_type;
	this.restricted_count = restricted_count;
}

public int getCard_id() {
	return card_id;
}

public void setCard_id(int card_id) {
	this.card_id = card_id;
}

public int getFoil_card_id() {
	return foil_card_id;
}

public void setFoil_card_id(int foil_card_id) {
	this.foil_card_id = foil_card_id;
}

public int getCard_set_id() {
	return card_set_id;
}

public void setCard_set_id(int card_set_id) {
	this.card_set_id = card_set_id;
}

public String getCard_name() {
	return card_name;
}

public void setCard_name(String card_name) {
	this.card_name = card_name;
}

public int getIs_foil() {
	return is_foil;
}

public void setIs_foil(int is_foil) {
	this.is_foil = is_foil;
}

public int getChar_type() {
	return char_type;
}

public void setChar_type(int char_type) {
	this.char_type = char_type;
}

public int getClan() {
	return clan;
}

public void setClan(int clan) {
	this.clan = clan;
}

public String getTribe_name() {
	return tribe_name;
}

public void setTribe_name(String tribe_name) {
	this.tribe_name = tribe_name;
}

public String getSkill_disc() {
	return skill_disc;
}

public void setSkill_disc(String skill_disc) {
	this.skill_disc = skill_disc;
}

public String getEvo_skill_disc() {
	return evo_skill_disc;
}

public void setEvo_skill_disc(String evo_skill_disc) {
	this.evo_skill_disc = evo_skill_disc;
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

public int getEvo_atk() {
	return evo_atk;
}

public void setEvo_atk(int evo_atk) {
	this.evo_atk = evo_atk;
}

public int getEvo_life() {
	return evo_life;
}

public void setEvo_life(int evo_life) {
	this.evo_life = evo_life;
}

public int getRarity() {
	return rarity;
}

public void setRarity(int rarity) {
	this.rarity = rarity;
}

public int getGet_red_ether() {
	return get_red_ether;
}

public void setGet_red_ether(int get_red_ether) {
	this.get_red_ether = get_red_ether;
}

public int getUse_red_ether() {
	return use_red_ether;
}

public void setUse_red_ether(int use_red_ether) {
	this.use_red_ether = use_red_ether;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getEvo_description() {
	return evo_description;
}

public void setEvo_description(String evo_description) {
	this.evo_description = evo_description;
}

public String getCv() {
	return cv;
}

public void setCv(String cv) {
	this.cv = cv;
}

public int getBase_card_id() {
	return base_card_id;
}

public void setBase_card_id(int base_card_id) {
	this.base_card_id = base_card_id;
}

public String getTokens() {
	return tokens;
}

public void setTokens(String tokens) {
	this.tokens = tokens;
}

public int getNormal_card_id() {
	return normal_card_id;
}

public void setNormal_card_id(int normal_card_id) {
	this.normal_card_id = normal_card_id;
}

public int getFormat_type() {
	return format_type;
}

public void setFormat_type(int format_type) {
	this.format_type = format_type;
}

public int getRestricted_count() {
	return restricted_count;
}

public void setRestricted_count(int restricted_count) {
	this.restricted_count = restricted_count;
}


}
