package com.avengers.api.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class LeistungTotalTag {
    private Date datum;
    private Double leistung=0.0;
    private String datum_s;

    public void addLeistung(Double eingabe)
    {
        this.leistung = this.leistung + eingabe;
    }
}
