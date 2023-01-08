package com.foxminded.university.models;

import java.util.Objects;

public class Audience {

	private int audienceNumber;

	public Audience(int audienceNumber) {
		super();
		this.audienceNumber = audienceNumber;
	}

	public int getAudienceNumber() {
		return audienceNumber;
	}

	public void setAudienceNumber(int audienceNumber) {
		this.audienceNumber = audienceNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(audienceNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Audience other = (Audience) obj;
		return audienceNumber == other.audienceNumber;
	}

	@Override
	public String toString() {
		return "Audience [audienceNumber=" + audienceNumber + "]";
	}

}
