package com.github.twistedpair.questions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

/**
 * Wrapper to allow CLI access
 * 
 * @author Joseph Lust
 */
final class HotelDealRecommenderCli {

	private static final int INPUT_FILENAME_IDX = 0;
	private static final int HOTEL_NAME_IDX = 1;
	private static final int START_DATE_IDX = 2;
	private static final int STAY_LENGTH_IDX = 3;

	public static final void main(final String[] args) {
		if(args.length!=4) {
			System.out.println("4 arguments required: [filename] [hotelName] [startdate YYYY-MM-DD] [stayLenghtInDays int]");
		}

		// TODO use a CLI library to abstract this logic/parsing away
		// load from file
		final String filename = args[INPUT_FILENAME_IDX];

		// parse hotel
		final String hotelName = args[HOTEL_NAME_IDX].trim();

		// parse date
		final String startDateStr = args[START_DATE_IDX];
		final LocalDate startDate = new LocalDate(startDateStr);

		// parse target range
		final String stayLengthStr = args[STAY_LENGTH_IDX];
		final int stayLength = Integer.valueOf(stayLengthStr);

		// get best deal

		// recommend
		final List<String> rawDeals = readRawDealsFromFile(filename);
		final HotelDealRecommender recommender = new HotelDealRecommender(rawDeals);
		final String bestDeal = recommender.recommendDeal(hotelName, startDate, stayLength);

		// display and handle errors
		if (bestDeal.isEmpty()) {
			System.out.println("No deal available");
		}
		else {
			System.out.println(bestDeal);
		}
	}

	private static List<String> readRawDealsFromFile(final String filename) {
		// fetch all lines - could pass reader to recommender, but this makes it more testable
		final List<String> rawDeals = new ArrayList<>(); // TODO preinit to estimated size
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filename)))) {

			String line = reader.readLine();
			while(line!=null) {
				line = reader.readLine();
			}
		}
		catch (final FileNotFoundException e) {
			System.out.println("Could not open file: "+filename);
		}
		catch (final IOException e) {
			System.out.println("Could not read file: " + filename);
		}

		return rawDeals;
	}
}