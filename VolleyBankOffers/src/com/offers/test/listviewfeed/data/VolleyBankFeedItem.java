package com.offers.test.listviewfeed.data;

public class VolleyBankFeedItem {
	private int id;
	private String title, image, description, validdate, count;

	public VolleyBankFeedItem() {
	}

	public VolleyBankFeedItem(int id, String title, String image, String description,
			String validdate, String count) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.description = description;
		this.validdate = validdate;
		this.count = count;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDecsription() {
		return description;
	}

	public void setDecsription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getValiddate() {
		return validdate;
	}

	public void setValiddate(String validdate) {
		this.validdate = validdate;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

}
