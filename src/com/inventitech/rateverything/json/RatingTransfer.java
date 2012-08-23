package com.inventitech.rateverything.json;


public class RatingTransfer {
	public enum RATING {
		RATING_10, RATING_9, RATING_8, RATING_7, RATING_6, RATING_5, RATING_XD;
	}

	public RATING rating = RATING.RATING_XD;

	public RatingTransfer(RATING rating) {
		this.rating = rating;
	}
}
