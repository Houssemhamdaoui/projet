package com.avengers.api.model;


//import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Wka {

    //@Id
    //private String _id; //String?
    private String Betreiber;
    private Long Bst_Nr;
    private String Bst_Name;
    private String Ort;
    private String Ortsteil;
    private String Anl_Nr;
    private String Anl_Bez;
    private Date Genehmigt;
    private Integer Ostwert;
    private Integer Nordwert;
    private Double Latitude;
    private Double Longitude;
    private String Kreis;
    private Integer Geme_Kenn;
    private Integer PLZ;
    private Date Inbetriebn;
    private Date Alt_an_anz;
    private Double Leistung;
    private String Status;
    private Double Nabenhoehe;
    private Double Rotordurch;
    private String LW_TAG;
    private String LW_Nacht;
    //private Integer Stand_Abw;
    private String Wka_ID;

    private String Genehmigt_s;
    private String  Inbetriebn_s;
    private String Alt_an_anz_s;

    private String PLZ_s;


}

