/*
package com.atencia.ScreenMatch;

import com.atencia.ScreenMatch.controller.OptionMenu;
import com.atencia.ScreenMatch.repository.SeriesRepository;
import com.atencia.ScreenMatch.util.PropertyReaderApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplicationConsole implements CommandLineRunner {
    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private PropertyReaderApiKey propertyReaderApiKey;

    public static void main(String[] args) {
        SpringApplication.run(ScreenMatchApplicationConsole.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        OptionMenu.options(seriesRepository, propertyReaderApiKey);

    }

}
*/
