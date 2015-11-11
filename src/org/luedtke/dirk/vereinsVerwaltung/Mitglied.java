package org.luedtke.dirk.vereinsVerwaltung;

import org.json.JSONObject;

public class Mitglied {


	// Nr.;Name;Vorname;Beitrag;Monat;Beginn;Mandat;Strasse;PLZ;Stadt;sonstiges
	private int id;
	private String firstName;
	private String lastName;
	private String title="Herr";
	private String street;
	private String city;
	private String phone;
	private String cellPhone;
	private String email;
	private float payment;
	private int paymentMonth;
	private int entryYear;
	private int mandat;
	private String PLZ;
	private String remark=" ";
	private String gender="unbek.";
	private String status="aktiv";

	public Mitglied() {
		mandat=0; 
	}

	public Mitglied(JSONObject jsonObject) {
		firstName=jsonObject.getString("firstName");
		lastName=jsonObject.getString("lastName");;
		title=jsonObject.getString("title");
		street=jsonObject.getString("street");
		city=jsonObject.getString("city");
		phone=jsonObject.getString("phone");
		cellPhone=jsonObject.getString("cellPhone");
		email=jsonObject.getString("email");
		payment=(float) jsonObject.getDouble("payment");
		paymentMonth=jsonObject.getInt("paymentMonth");
		entryYear=jsonObject.getInt("entryYear");
		mandat=0;
		PLZ=jsonObject.getString("PLZ");
		remark=jsonObject.getString("remark");
		status=jsonObject.getString("status");
		gender=jsonObject.getString("geschlecht");
		mandat=jsonObject.getInt("mandat");
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	public int getPaymentMonth() {
		return paymentMonth;
	}

	public void setPaymentMonth(int i) {
		this.paymentMonth = i;
	}

	public int getEntryYear() {
		return entryYear;
	}

	public void setEntryYear(int i) {
		this.entryYear = i;
	}

	public int getMandat() {
		return mandat;
	}

	public void setMandat(int mandat) {
		this.mandat = mandat;
	}

	public String getPLZ() {
		return PLZ;
	}

	public void setPLZ(String pLZ) {
		PLZ = pLZ;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
