package com.avengers.api.controller;

import com.avengers.api.model.LeistungPlz;
import com.avengers.api.model.LeistungTotalTag;
import com.avengers.api.model.Wka;
import com.avengers.api.repository.WkaRepository;
import com.avengers.api.service.WkaService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class WkaController {
    @Autowired //?
    private final WkaService wkaService;


    @GetMapping("/raw")
    public List<Document> getAllWka(@RequestParam(required = false) String ORT)
    {
        //return ResponseEntity.ok(wkaService.getAllWka());
        return wkaService.getAllWkaRaw();
    }


    @GetMapping("/all")
    public List<Wka> getAllWkaClass(@RequestParam(required = false) String ORT)
    {
        //return ResponseEntity.ok(wkaService.getAllWka());
        return wkaService.getAllWkaClass();
    }

    @GetMapping("/lastfive")
    public List<Wka> getLastFive(@RequestParam(required = false) String ORT)
    {
        return wkaService.getLastFiveYearsWka();
    }


    @GetMapping("/search/ort/{ort}")
    public List<Wka> getAllWkaByOrt(@PathVariable String ort)
    {
        return wkaService.getAllWkaByOrt(ort);
    }


    @GetMapping("/search/genehmigt/before/{day}/{month}/{year}")
    public List<Wka> getGenehmigtBefore(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year)
    {
        String date_str = day.toString() + "."+ month.toString()+"."+ year.toString();
        Date date = WkaRepository.convertStringDate(date_str);

        return wkaService.getGenehmigtBefore(date);

    }


    @GetMapping("/search/genehmigt/on/{day}/{month}/{year}")
    public List<Wka> getGenehmigtOn(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year)
    {
        String date_str = day.toString() + "."+ month.toString()+"."+ year.toString();
        Date date = WkaRepository.convertStringDate(date_str);

        return wkaService.getGenehmigtOn(date);

    }


    @GetMapping("/search/genehmigt/after/{day}/{month}/{year}")
    public List<Wka> getGenehmigtAfter(@PathVariable Integer day, @PathVariable Integer month, @PathVariable Integer year)
    {
        String date_str = day.toString() + "."+ month.toString()+"."+ year.toString();
        Date date = WkaRepository.convertStringDate(date_str);

        return wkaService.getGenehmigtAfter(date);

    }


    @GetMapping("/search/genehmigt/between/{dayMin}/{monthMin}/{yearMin}/{dayMax}/{monthMax}/{yearMax}")
    public List<Wka> getGenehmigtBetween(@PathVariable Integer dayMin, @PathVariable Integer monthMin, @PathVariable Integer yearMin,@PathVariable Integer dayMax, @PathVariable Integer monthMax, @PathVariable Integer yearMax )
    {
        String date_str_min = dayMin.toString() + "."+ monthMin.toString()+"."+ yearMin.toString();
        Date dateMin = WkaRepository.convertStringDate(date_str_min);

        String date_str_max = dayMax.toString() + "."+ monthMax.toString()+"."+ yearMax.toString();
        Date dateMax = WkaRepository.convertStringDate(date_str_max);

        return wkaService.getGenehmigtBetween(dateMin,dateMax);
    }


    @GetMapping("/search/inbetrieb/between/{dayMin}/{monthMin}/{yearMin}/{dayMax}/{monthMax}/{yearMax}")
    public List<Wka> getInbetriebBetween(@PathVariable Integer dayMin, @PathVariable Integer monthMin, @PathVariable Integer yearMin,@PathVariable Integer dayMax, @PathVariable Integer monthMax, @PathVariable Integer yearMax )
    {
        String date_str_min = dayMin.toString() + "."+ monthMin.toString()+"."+ yearMin.toString();
        Date dateMin = WkaRepository.convertStringDate(date_str_min);

        String date_str_max = dayMax.toString() + "."+ monthMax.toString()+"."+ yearMax.toString();
        Date dateMax = WkaRepository.convertStringDate(date_str_max);

        return wkaService.getInbetriebBetween(dateMin,dateMax);
    }

    @GetMapping("/search/geninb/between/{dayMin}/{monthMin}/{yearMin}/{dayMax}/{monthMax}/{yearMax}")
    public List<Wka> getGenehmigtInbetriebBetween(@PathVariable Integer dayMin, @PathVariable Integer monthMin, @PathVariable Integer yearMin,@PathVariable Integer dayMax, @PathVariable Integer monthMax, @PathVariable Integer yearMax )
    {
        String date_str_min = dayMin.toString() + "."+ monthMin.toString()+"."+ yearMin.toString();
        Date dateMin = WkaRepository.convertStringDate(date_str_min);

        String date_str_max = dayMax.toString() + "."+ monthMax.toString()+"."+ yearMax.toString();
        Date dateMax = WkaRepository.convertStringDate(date_str_max);

        return wkaService.getGenehmigtInbetriebBetween(dateMin,dateMax);


    }

    @GetMapping("/search/leistung/tag/{dayMin}/{monthMin}/{yearMin}/{dayMax}/{monthMax}/{yearMax}")
    public List<LeistungTotalTag> getLeistungTaeglich(@PathVariable Integer dayMin, @PathVariable Integer monthMin, @PathVariable Integer yearMin,@PathVariable Integer dayMax, @PathVariable Integer monthMax, @PathVariable Integer yearMax )
    {
        String date_str_min = dayMin.toString() + "."+ monthMin.toString()+"."+ yearMin.toString();
        Date dateMin = WkaRepository.convertStringDate(date_str_min);

        String date_str_max = dayMax.toString() + "."+ monthMax.toString()+"."+ yearMax.toString();
        Date dateMax = WkaRepository.convertStringDate(date_str_max);

        return wkaService.statistikLeistungTag(dateMin,dateMax);
    }

    @GetMapping("/search/leistung/plz/{dayMin}/{monthMin}/{yearMin}/{dayMax}/{monthMax}/{yearMax}")
    public List<LeistungPlz> getLeistungPlz(@PathVariable Integer dayMin, @PathVariable Integer monthMin, @PathVariable Integer yearMin,@PathVariable Integer dayMax, @PathVariable Integer monthMax, @PathVariable Integer yearMax )
    {
        String date_str_min = dayMin.toString() + "."+ monthMin.toString()+"."+ yearMin.toString();
        Date dateMin = WkaRepository.convertStringDate(date_str_min);

        String date_str_max = dayMax.toString() + "."+ monthMax.toString()+"."+ yearMax.toString();
        Date dateMax = WkaRepository.convertStringDate(date_str_max);

        return wkaService.statistikLeistungPlz(dateMin,dateMax);
    }



}



