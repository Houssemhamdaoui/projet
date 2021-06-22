package com.avengers.api.repository;

import com.avengers.api.model.Wka;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Repository //?
public class WkaRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public static List<Character>
    convertStringToCharList(String str)
    {
        List<Character> chars = new ArrayList<>();
        for (char ch : str.toCharArray()) {
            chars.add(ch);
        }
        return chars;
    }

    public static Double convertNksToDouble(String ein)
    {
        String str = ein;
        List<Character>
                chars = convertStringToCharList(str);
        int kom = -1;
        for (int i=0;i<chars.size(); i++)
        {
            if (chars.get(i)==',')
            {
                kom = i;
            }
        }

        Double zahl = 0.0;

        //Ohne NKS
        if (kom==-1)
        {
            for (int i = chars.size()-1; i>=0; i--)
            {
                zahl = zahl + (chars.get(i)-48)*Math.pow(10,chars.size()-1-i);

            }
        }
        //NKS
        else
        {
            for (int i = kom-1; i>=0; i--)
            {
                zahl = zahl + (chars.get(i)-48)*Math.pow(10,kom-i-1);

            }
            for (int i = kom+1; i<chars.size(); i++)
            {
                zahl = zahl + (chars.get(i)-48)*Math.pow(10,kom-i);

            }
        }

        return zahl;
    }

    public static Date convertStringDate(String ein)
    {
        try {
            String sDate1=ein;
            Date date1=new SimpleDateFormat("dd.MM.yyyy").parse(sDate1);
            //+1 Tag -> beim konvertieren ist der Tag um 1 Tag vermindert.
            Date richtig = new Date(date1.getTime()+ (1000 * 60 * 60 * 24));
            return richtig;
            //return date1;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String convertDateString (Date ein)
    {
        Date temp = new Date(ein.getTime()- (1000 * 60 * 60 * 24));
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = dateFormat.format(temp);


        return strDate;

    }


    public List<Document> find()
    {
        MongoClient mongoClient = MongoClients.create("mongodb://wka:JeKxj1OJ0nU8@www.robert-magnus.de:27017/?authSource=wka&readPreference=primary&appname=MongoDB%20Compass&ssl=false");
        MongoDatabase mongoDB = mongoClient.getDatabase("wka");
        MongoCollection<Document> collection = mongoDB.getCollection("wka");
       FindIterable<Document> ite  = collection.find();
       List<Document> liste = new ArrayList<>();

       for (Document document : ite)
       {
           liste.add(document);
       }


        return liste;
    }

    public  List<Wka> find_wka()
    {
        MongoClient mongoClient = MongoClients.create("mongodb://wka:JeKxj1OJ0nU8@www.robert-magnus.de:27017/?authSource=wka&readPreference=primary&appname=MongoDB%20Compass&ssl=false");
        MongoDatabase mongoDB = mongoClient.getDatabase("wka");
        MongoCollection<Document> collection = mongoDB.getCollection("wka");
        FindIterable<Document> ite  = collection.find();

        List<Wka> liste = new ArrayList<>();

        for (Document document : ite)
        {
            Wka objekt = new Wka();
            //TODO: ID?
            //Object id_object = document.get("_id");
            //objekt.set_id(id_object.get);

            //Problemlos
            objekt.setBetreiber(document.getString("Betreiber"));
            objekt.setBst_Nr(document.getLong("Bst_Nr"));
            objekt.setBst_Name(document.getString("Bst_Name"));
            objekt.setOrt(document.getString("Ort"));
            objekt.setOrtsteil(document.getString("Ortsteil"));
            objekt.setAnl_Bez(document.getString("Anl_Bez"));
            objekt.setOstwert(document.getInteger("Ostwert"));
            objekt.setNordwert(document.getInteger("Nordwert"));
            objekt.setLatitude(document.getDouble("Latitude"));
            objekt.setLongitude(document.getDouble("Longitude"));
            objekt.setKreis(document.getString("Kreis"));
            objekt.setGeme_Kenn(document.getInteger("Geme_Kenn"));
            objekt.setStatus(document.getString("Status"));



            //Anl_Nr
            try {
                objekt.setAnl_Nr(document.getString("Anl_Nr"));
            }
            catch (Exception e)
            {
                Integer temp = document.getInteger("Anl_Nr");
                objekt.setAnl_Nr(temp.toString());

            }


            //PLZ
            try {
                objekt.setPLZ(document.getInteger("PLZ"));
            }
            catch (Exception e)
            {
                String temp = document.getString("PLZ");
                objekt.setPLZ(Integer.parseInt(temp));
            }

            //PLZ_s
            try {
                objekt.setPLZ_s(document.getString("PLZ"));
            }
            catch (Exception e)
            {
                Integer plz_temp = document.getInteger("PLZ");

                //5 Digit
                if (plz_temp>=10000)
                {
                    objekt.setPLZ_s(plz_temp.toString());
                }
                //"0" + 4 Digit
                else
                {
                    objekt.setPLZ_s("0"+plz_temp.toString());
                }

            }

            //WKA_ID
            try {
                objekt.setWka_ID(document.getString("Wka_ID"));
            }
            catch (Exception e)
            {
                Long temp = document.getLong("Wka_ID");
                objekt.setWka_ID(temp.toString());
            }

            //Leistung
            try {
                //String to Double
                String temp = document.getString("Leistung");
                Double temp_d = convertNksToDouble(temp);
                objekt.setLeistung(temp_d);
            }
            catch (Exception e)
            {
                //Integer to Double
                Integer temp = document.getInteger("Leistung");
                double temp2 = (double) temp;
                Double temp3 = temp2;
                objekt.setLeistung(temp3);
            }

            //Nabenhoehe
            try {
                //String to Double
                String temp = document.getString("Nabenhoehe");
                Double temp_d = convertNksToDouble(temp);
                objekt.setNabenhoehe(temp_d);
            }
            catch (Exception e)
            {
                //Integer to Double
                Integer temp = document.getInteger("Nabenhoehe");
                double temp2 = (double) temp;
                Double temp3 = temp2;
                objekt.setNabenhoehe(temp3);
            }


            //Rotordurch
            try {
                //String to Double
                String temp = document.getString("Rotordurch");
                Double temp_d = convertNksToDouble(temp);
                objekt.setRotordurch(temp_d);
            }
            catch (Exception e)
            {
                //Integer to Double
                Integer temp = document.getInteger("Rotordurch");
                double temp2 = (double) temp;
                Double temp3 = temp2;
                objekt.setRotordurch(temp3);
            }


            //LW_TAG
            try {
                //String
                String temp = document.getString("LW_TAG");
                if (temp.equals("-99"))
                {
                    //if -99
                    objekt.setLW_TAG("unbekannt");
                }
                else
                {
                    //ansonsten
                    objekt.setLW_TAG(temp);
                }
            }
            catch (Exception e)
            {
                //Integer to String
                Integer temp = document.getInteger("LW_TAG");

                if(temp ==-99)
                {
                    //if -99
                    objekt.setLW_TAG("unbekannt");
                }
                else
                {
                    //ansonsten
                    objekt.setLW_TAG(temp.toString());
                }
            }

            //LW_Nacht
            try {
                //String
                String temp = document.getString("LW_Nacht");
                if (temp.equals("-99"))
                {
                    //if -99
                    objekt.setLW_Nacht("unbekannt");
                }
                else
                {
                    //ansonsten
                    objekt.setLW_Nacht(temp);
                }
            }
            catch (Exception e)
            {
                //Integer to String
                Integer temp = document.getInteger("LW_Nacht");

                if(temp ==-99)
                {
                    //if -99
                    objekt.setLW_Nacht("unbekannt");
                }
                else
                {
                    //ansonsten
                    objekt.setLW_Nacht(temp.toString());
                }
            }


            //Genehmigt
            objekt.setGenehmigt(convertStringDate(document.getString("Genehmigt")));

            //Inbetriebn
            objekt.setInbetriebn(convertStringDate(document.getString("Inbetriebn")));

            //Alt_an_anz
            objekt.setAlt_an_anz(convertStringDate(document.getString("Alt_an_anz")));

            //Genehmigt_s -> Deutsches Datumformat
            objekt.setGenehmigt_s(document.getString("Genehmigt"));
            //Inbetriebn_s -> Deutsches Datumformat
            objekt.setInbetriebn_s(document.getString("Inbetriebn"));
            //Alt_an_anz_s -> Deutsches Datumformat
            objekt.setAlt_an_anz_s(document.getString("Alt_an_anz"));

            liste.add(objekt);

        }
        return liste;
    }






}
