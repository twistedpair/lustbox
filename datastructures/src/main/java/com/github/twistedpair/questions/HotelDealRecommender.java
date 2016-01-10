package com.github.twistedpair.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.Interval;
import org.joda.time.LocalDate;


final class HotelDealRecommender {

	private final Map<String, TreeSet<HotelDeal>> deals;

	public enum HotelDealType {
		REBATE, REBATE_3PLUS, PCT
	}

	private class HotelDeal implements Comparable<HotelDeal> {

		private final String hotelName;
		private final HotelDealType type;
		private final int dealAmount;
		private final int rateCents;
		private final String promoTxt;
		private final Interval effectiveInterval;

		public HotelDeal(final String hotelName, final HotelDealType type,
				final int dealAmount, final int rateCents,
				final String promoTxt, final Interval effectiveInterval) {
			super();
			this.hotelName = hotelName; // TODO remove, redundant with key
			this.type = type;
			this.dealAmount = dealAmount;
			this.rateCents = rateCents;
			this.promoTxt = promoTxt;
			this.effectiveInterval = effectiveInterval;
		}

		/**
		 * Empty deal used for tree lookup
		 * @param lookupInterval
		 */
		public HotelDeal(final LocalDate startDate) {
			super();
			hotelName = null;
			type = null;
			dealAmount = 0;
			rateCents = 0;
			promoTxt = null;
			effectiveInterval = startDate.toInterval();
		}

		@Override
		public int compareTo(final HotelDeal o) {
			// sort based on start date
			return o.effectiveInterval.getStart().compareTo(effectiveInterval.getStart());
		}
	}

	public HotelDealRecommender(final Collection<String> rawDeals) {

		deals = new HashMap<>(rawDeals.size()); // preallocate

		for(final String rawDeal : rawDeals) {
			storeDeal(parseRawReal(rawDeal));
		}
	}

	private void storeDeal(final HotelDeal deal) {
		// persist deal
		final String key = deal.hotelName.toLowerCase();
		// get deal, init if blank
		SortedSet<HotelDeal> dealSet = deals.get(key);
		if (dealSet == null) {
			dealSet = new TreeSet<>();
		}
		dealSet.add(deal);
	}

	// TODO use a CSV parsing library for cases like escapes, line terms, commas inside quotes, etc
	// simple parser used here for sake of simplicity and dev speed
	private HotelDeal parseRawReal(final String rawDeal) {
		final String[] parts = rawDeal.split(",");
		assert parts.length == 7 : "Invalid input, 7 columsn required!";

		// TODO move magic number index values to constants
		final String hotelName = parts[0];
		final int rateCents = (int) Double.valueOf(parts[1]).doubleValue() * 100;
		final String promoTxt = parts[2];
		final int dealAmount = (int) Double.valueOf(parts[3]).doubleValue() * 100;
		final HotelDealType type = HotelDealType
				.valueOf(parts[4].toUpperCase());
		final LocalDate start = new LocalDate(parts[5]);
		final LocalDate end = new LocalDate(parts[6]);
		final Interval effectiveInterval = new Interval(start.toDateMidnight(),
				end.toDateMidnight());

		final HotelDeal deal = new HotelDeal(hotelName, type, dealAmount,
				rateCents, promoTxt, effectiveInterval);

		return deal;
	}

	/*
	 * write an app to find the best applicable deal for the user and return the promo_txt for the
	 * deal. You will need to consider the type of deal, valid dates, hotel name, and edge cases. If
	 * no deal applies you will return “no deals available”
	 */
	/**
	 * Find the temporally relevant deal which is the best of all applicable deals for the given
	 * unique hotel name.
	 * 
	 * @param hotelName
	 *            IllgalArgumentException thrown if no information exists for hotel
	 * @param startDate
	 * @param stayLength
	 * @return best hotel deal or EMPTY string if no deals apply
	 */
	public String recommendDeal(final String hotelName,
			final LocalDate startDate, final int stayLength) {

		// (1) find deal for this hotel
		final TreeSet<HotelDeal> hotelDeals = findDealsForHotelName(hotelName);

		// (2) find overlapping ranges
		final Collection<HotelDeal> overlappingDeals =
				findTemporallyRelevantDeals(hotelDeals, startDate, stayLength);

		// (3) apply diff types and determine best deal
		final HotelDeal bestDeal = findBestDeal(overlappingDeals);

		// TODO could throw exception here as well, depends if this is common case
		if (bestDeal == null) { return ""; }
		return bestDeal.promoTxt;
	}

	private HotelDeal findBestDeal(final Collection<HotelDeal> hotelDeals) {
		// consideration
		// date within range?
		// discount for given range overlap?
		// overlapping deals?
		// simple percent discounts
		// deal combinations
		return hotelDeals.iterator().next();
	}

	private TreeSet<HotelDeal> findDealsForHotelName(final String hotelName) {
		// (0) handle invalid input
		if (hotelName == null) {
			throw new IllegalArgumentException("No such hotel for name: "+hotelName);
		}

		// (1) get deals for hotel, handle missing case
		final TreeSet<HotelDeal> hotelDeals = deals.get(hotelName.toLowerCase());
		if (hotelDeals == null) {
			throw new IllegalArgumentException("No such hotel for name: "+hotelName);
		}
		return hotelDeals;
	}

	private Collection<HotelDeal> findTemporallyRelevantDeals(
			final TreeSet<HotelDeal> hotelDeals, final LocalDate startDate,
			final int stayLength) {

		// TODO use a Segment Tree to get range overlap directly
		final Interval targetInterval = new Interval(startDate.toDateMidnight(),
				startDate.plusDays(stayLength-1).toDateMidnight());
		final Collection<HotelDeal> applicableDeals = new ArrayList<>(hotelDeals.size());

		final HotelDeal lookupDeal = new HotelDeal(startDate);
		for (final HotelDeal deal : hotelDeals.headSet(lookupDeal, true)) {
			if (deal.effectiveInterval.contains(targetInterval)) {
				applicableDeals.add(deal);
			}
		}

		return applicableDeals;
	}

}