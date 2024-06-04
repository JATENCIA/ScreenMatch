package com.atencia.ScreenMatch.controller;

import com.atencia.ScreenMatch.models.*;
import com.atencia.ScreenMatch.repository.SeriesRepository;
import com.atencia.ScreenMatch.service.ApiService;
import com.atencia.ScreenMatch.service.ConvertData;
import com.atencia.ScreenMatch.service.ConvertDataImpl;
import com.atencia.ScreenMatch.util.PropertyReaderApiKey;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.*;
import java.util.stream.Collectors;

public class MenuController {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ApiService apiService = new ApiService();
    private static final String URL_BASE = "https://www.omdbapi.com/?t=";
    private static String API_KEY_URL;
    private static final ConvertData convertData = new ConvertDataImpl();
    private static final List<DataSeries> seriesSearchHistory = new ArrayList<>();

    public static void viewMenu(SeriesRepository seriesRepository, PropertyReaderApiKey propertyReaderApiKey) throws JsonProcessingException {
        API_KEY_URL = propertyReaderApiKey.getApiKeyUrl();
        String nameSeries = promptForSeriesName();
        DataSeries dataSeries = fetchDataSeries(nameSeries);

        if (dataSeries == null || !isValidTotalSeasons(dataSeries.totalSeasons())) {
            System.out.println("No data found for the given series name.");
            return;
        }

        Series series = new Series(dataSeries, propertyReaderApiKey);
        seriesRepository.save(series);

        List<DataSeasons> dataSeasons = fetchSeasonsData(dataSeries);
        displaySeriesData(dataSeries, dataSeasons);
    }

    public static void searchEpisodesBySeries(SeriesRepository seriesRepository, PropertyReaderApiKey propertyReaderApiKey) throws JsonProcessingException {
        API_KEY_URL = propertyReaderApiKey.getApiKeyUrl();
        displaySeriesSearchHistory(seriesRepository);

        System.out.print("📺 Enter series name to view episodes: ");
        String inputName = scanner.nextLine();

        Optional<Series> matchingSeries = findMatchingSeries(seriesRepository, inputName);

        if (matchingSeries.isEmpty()) {
            System.out.println("No data found for the given series name.");
            return;
        }

        Series series = matchingSeries.get();
        DataSeries dataSeries = createDataSeriesFromSeries(series);
        List<DataSeasons> dataSeasons = fetchSeasonsData(dataSeries);
        List<Episode> episodes = extractEpisodesFromSeasons(dataSeasons);

        series.setEpisodes(episodes);
        seriesRepository.save(series);
        displaySeriesData(dataSeries, dataSeasons);
    }

    private static Optional<Series> findMatchingSeries(SeriesRepository seriesRepository, String inputName) {
        return seriesRepository.findAll()
                .stream()
                .filter(series -> series.getTitle().toUpperCase().contains(inputName.toUpperCase()))
                .findFirst();
    }

    private static DataSeries createDataSeriesFromSeries(Series series) {
        return new DataSeries(
                series.getTitle(),
                series.getTotalSeasons().toString(),
                series.getImdbRating().toString(),
                series.getYear(),
                series.getGenre().toString(),
                series.getLanguage(),
                series.getPoster(),
                series.getCountry(),
                series.getAwards(),
                series.getPlot()
        );
    }

    private static List<DataSeasons> fetchSeasonsData(DataSeries dataSeries) throws JsonProcessingException {
        List<DataSeasons> dataSeasons = new ArrayList<>();
        int totalSeasons = Integer.parseInt(dataSeries.totalSeasons());

        for (int seasonNumber = 1; seasonNumber <= totalSeasons; seasonNumber++) {
            DataSeasons seasonData = fetchSeasonData(dataSeries.title(), seasonNumber);
            if (seasonData != null) {
                dataSeasons.add(seasonData);
            }
        }

        return dataSeasons;
    }

    private static DataSeasons fetchSeasonData(String seriesTitle, int seasonNumber) throws JsonProcessingException {
        String url = String.format(URL_BASE + "%s&Season=%d" + "&apikey=" + API_KEY_URL, seriesTitle.replace(" ", "%20"), seasonNumber);
        String dataSeasonApi = apiService.getDataApi(url);
        return convertData.getData(dataSeasonApi, DataSeasons.class);
    }

    private static List<Episode> extractEpisodesFromSeasons(List<DataSeasons> dataSeasons) {
        return dataSeasons.stream()
                .flatMap(s -> s.episodes().stream().map(e ->
                        new Episode(Integer.valueOf(s.season()), e)
                ))
                .collect(Collectors.toList());
    }

    private static String promptForSeriesName() {
        System.out.print("🔍 Searching for series by name... : ");
        return scanner.nextLine().replace(" ", "%20");
    }

    private static DataSeries fetchDataSeries(String nameSeries) throws JsonProcessingException {
        String dataSeriesApi = apiService.getDataApi(URL_BASE + nameSeries + "&apikey=" + API_KEY_URL);
        return convertData.getData(dataSeriesApi, DataSeries.class);
    }

    private static boolean isValidTotalSeasons(String totalSeasons) {
        return totalSeasons != null && totalSeasons.chars().allMatch(Character::isDigit);
    }

    private static void displaySeriesData(DataSeries dataSeries, List<DataSeasons> dataSeasons) {
        System.out.println(" ");
        for (DataSeasons season : dataSeasons) {
            System.out.println("_ Season : # " + season.season() + "\n");
            for (DataEpisode episode : season.episodes()) {
                System.out.printf("- %s (Episode %s) - IMDb Rating: %s - Released: %s%n",
                        episode.title(), episode.episode(), episode.imdbRating(), episode.released());
            }
            System.out.println();
        }
    }

    public static void searchSeriesByTitle(SeriesRepository seriesRepository) {
        System.out.print("Enter the title of the series: ");
        String inputName = scanner.nextLine();

        Optional<Series> optionalSeries = seriesRepository.findByTitleContainsIgnoreCase(inputName);
        if (optionalSeries.isEmpty()) {
            System.out.println("No data found for the given series name.");
            return;
        }

        Series series = optionalSeries.get();
        displaySingleSeriesData(series);
    }

    public static void topFiveByOrderByImdbRating(SeriesRepository seriesRepository) {
        List<Series> seriesList = seriesRepository.findTop5ByOrderByImdbRatingDesc();


        displaySeriesList(seriesList);
    }

    public static void searchSeriesByCategory(SeriesRepository seriesRepository) {

        System.out.print("Enter the category of the series: ");
        String inputName = scanner.nextLine();

        try {

            Category category = Category.fromString(inputName);
            System.out.println(category);
            List<Series> seriesList = seriesRepository.findByGenre(category);

            displaySeriesList(seriesList);

        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please enter a valid category.");
        }


    }

    public static void findSeriesByMaxSeasonAndMinRating(SeriesRepository seriesRepository) {

        try {

            System.out.print("Enter the maximum season number: ");
            int maxSeason = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the minimum IMDb rating: ");
            double minRating = Double.parseDouble(scanner.nextLine());

            List<Series> seriesList = seriesRepository.getSeriesBySeasonAndImdbRating(maxSeason, minRating);
            displaySeriesList(seriesList);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter valid numbers for season and IMDb rating.");
        }

    }

    public static void searchSeriesByEpisodeTitle(SeriesRepository seriesRepository) {

        System.out.print("Enter the title episode of the series: ");
        String inputName = scanner.nextLine();

        List<Series> seriesList = seriesRepository.findByEpisodeTitleContaining(inputName);
        displaySeriesList(seriesList);

    }

    public static void findTop5EpisodesBySeries(SeriesRepository seriesRepository) {
        System.out.print("Enter the title of the series: ");
        String inputName = scanner.nextLine();

        List<Episode> episodes = seriesRepository.findTop5EpisodesBySeries(inputName);

        if (!episodes.isEmpty()) {
            System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println(" 🏆 Top 5 Episodes for the Series: " + inputName);
            System.out.println("╚═════════════════════════════════════════════╝");

            String[] rankings = {"🥇", "🥈", "🥉", "🏅", "🎖"};
            for (int i = 0; i < episodes.size(); i++) {
                Episode episode = episodes.get(i);
                System.out.println("╔═════════════════════════════════════════════╗");
                System.out.println(rankings[i]);
                System.out.println("   Episode Title: " + episode.getTitle());
                System.out.println("   IMDb Rating: " + episode.getImdbRating());
                System.out.println("╚═════════════════════════════════════════════╝");
            }
        } else {
            System.out.println("No episodes found for the series: " + inputName);
        }

    }


    public static void displaySeriesSearchHistory(SeriesRepository seriesRepository) {

        List<Series> seriesList = seriesRepository.findAll();
        displaySeriesList(seriesList);

    }


    private static void displaySingleSeriesData(Series series) {
        System.out.println("\n╔═══════════════════════════╗\n");
        System.out.printf("""
                           📺 Title: %s
                           📆 Total Seasons: %s
                           ⭐ IMDb Rating: %s
                           📅 Year: %s
                           🎭 Genre: %s
                           🌐 Language: %s
                           🖼️ Poster: %s
                           🌍 Country: %s
                           🏆 Awards: %s
                           📝 Plot: %s
                           
                        """,
                series.getTitle(), series.getTotalSeasons(), series.getImdbRating(),
                series.getYear(), series.getGenre(), series.getLanguage(),
                series.getPoster(), series.getCountry(), series.getAwards(), series.getPlot());
        System.out.println("╚═══════════════════════════╝");
    }

    private static void displaySeriesList(List<Series> seriesList) {

        if (seriesList.isEmpty()) {
            System.out.println("\n🔍 No series found matching the specified criteria.\n");
            return;
        }

        seriesList.forEach(MenuController::displaySingleSeriesData);
    }
}
