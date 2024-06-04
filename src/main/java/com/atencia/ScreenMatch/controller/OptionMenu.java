package com.atencia.ScreenMatch.controller;

import com.atencia.ScreenMatch.repository.EpisodeRepository;
import com.atencia.ScreenMatch.repository.SeriesRepository;
import com.atencia.ScreenMatch.util.PropertyReaderApiKey;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class OptionMenu {

    private final static Scanner scanner = new Scanner(System.in);

    public static void options(SeriesRepository seriesRepository, PropertyReaderApiKey propertyReaderApiKey) throws JsonProcessingException {

        boolean flag = true;

        while (flag) {

            System.out.println("╔═══════════════════════════╗");
            System.out.println("         Options        ");
            System.out.println("╠═══════════════════════════╣");
            System.out.println(" 1 🔍 Search series ");
            System.out.println(" 2 📜 History search by name ");
            System.out.println(" 3 📺 Search episode by series name ");
            System.out.println(" 4 🔍 Search series by name ");
            System.out.println(" 5 🏆 Top five series ");
            System.out.println(" 6 🔍 Search series by category ");
            System.out.println(" 7 🔍 Search series by MaxSeason And MinRating ");
            System.out.println(" 8 🔍 Search series by title episode ");
            System.out.println(" 9 🏆 Top 5 Episodes for the Series ");
            System.out.println(" 10 🚪 Exit                  ");
            System.out.println("╚═══════════════════════════╝");

            System.out.print("\n👉 Select an option: ");

            try {

                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {

                    case 1:
                        MenuController.viewMenu(seriesRepository, propertyReaderApiKey);
                        break;
                    case 2:
                        MenuController.displaySeriesSearchHistory(seriesRepository);
                        break;
                    case 3:
                        MenuController.searchEpisodesBySeries(seriesRepository, propertyReaderApiKey);
                        break;
                    case 4:
                        MenuController.searchSeriesByTitle(seriesRepository);
                        break;
                    case 5:
                        MenuController.topFiveByOrderByImdbRating(seriesRepository);
                        break;
                    case 6:
                        MenuController.searchSeriesByCategory(seriesRepository);
                        break;
                    case 7:
                        MenuController.findSeriesByMaxSeasonAndMinRating(seriesRepository);
                        break;
                    case 8:
                        MenuController.searchSeriesByEpisodeTitle(seriesRepository);
                        break;
                    case 9:
                        MenuController.findTop5EpisodesBySeries(seriesRepository);
                        break;
                    case 10:
                        flag = false;
                        System.out.println("🚪 Exiting the system...");
                        break;
                    default:
                        System.out.println("❌ Invalid selection. Please choose a valid option.\n");
                        break;

                }

            } catch (NumberFormatException e) {
                System.out.println("\n🔢 Please enter an integer.\n");
            }

        }

    }
}
