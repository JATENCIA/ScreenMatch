package com.atencia.ScreenMatch;

import com.atencia.ScreenMatch.controller.OptionMenu;
import com.atencia.ScreenMatch.repository.EpisodeRepository;
import com.atencia.ScreenMatch.repository.SeriesRepository;
import com.atencia.ScreenMatch.util.PropertyReaderApiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.transform.sax.SAXResult;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenMatchApplication {


    public static void main(String[] args) {
        SpringApplication.run(ScreenMatchApplication.class, args);
    }


}
