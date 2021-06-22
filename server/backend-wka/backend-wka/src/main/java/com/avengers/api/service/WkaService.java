package com.avengers.api.service;


import com.avengers.api.model.LeistungPlz;
import com.avengers.api.model.LeistungTotalTag;
import com.avengers.api.model.Wka;
import com.avengers.api.repository.WkaRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.lang.Math;


@Service
@RequiredArgsConstructor
public class WkaService {
    @Autowired
    private final WkaRepository wkaRepository;
    private  int i;
    String myString;

    public List<Document> getAllWkaRaw()
    {
        //List<JSONObject> retVal = new ArrayList<JSONObject>();
        //retVal.addAll(wkaRepository.findAll());

        return wkaRepository.find();
    }

    public List<Wka> getAllWkaClass()
    {
        return wkaRepository.find_wka();
    }

    public List<Wka> getLastFiveYearsWka() {

        Date dateMax = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(dateMax);
        c.add(Calendar.YEAR,-5);

        Date dateMin = c.getTime();

        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if (((obj.getGenehmigt()!= null) && (dateMin.compareTo(obj.getGenehmigt())<=0) && (obj.getGenehmigt().compareTo(dateMax)<=0)) ||((obj.getInbetriebn()!= null) && (dateMin.compareTo(obj.getInbetriebn())<=0) && (obj.getInbetriebn().compareTo(dateMax)<=0))||((obj.getAlt_an_anz()!= null) && (dateMin.compareTo(obj.getAlt_an_anz())<=0) && (obj.getAlt_an_anz().compareTo(dateMax)<=0)))
            {
                ausgabe.add(obj);
            }
        }

        //2 NKS für Double, außer Koordinaten... Koordinaten lasse ich so!
        for (Wka obj: ausgabe)
        {
            obj.setLeistung(Math.round(obj.getLeistung()*100.0)/100.0);
            obj.setNabenhoehe(Math.round(obj.getNabenhoehe()*100.0)/100.0);
            obj.setRotordurch(Math.round(obj.getRotordurch()*100.0)/100.0);
        }

        return  ausgabe;
    }

    public List<Wka> getAllWkaByOrt(String ein)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if (obj.getOrt().equals(ein))
            {
                ausgabe.add(obj);
            }
        }

        return ausgabe;

    }

    public List<Wka> getGenehmigtBefore(Date date)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if ((obj.getGenehmigt()!= null) && (obj.getGenehmigt().compareTo(date)<0))
            {
                ausgabe.add(obj);
            }
        }

        return ausgabe;
    }

    public List<Wka> getGenehmigtOn(Date date)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if ((obj.getGenehmigt()!= null) && (obj.getGenehmigt().compareTo(date)==0))
            {
                ausgabe.add(obj);
            }
        }

        return ausgabe;
    }

    public List<Wka> getGenehmigtAfter(Date date)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if ((obj.getGenehmigt()!= null) && (obj.getGenehmigt().compareTo(date)>0))
            {
                ausgabe.add(obj);
            }
        }

        return ausgabe;
    }

    public List<Wka> getGenehmigtBetween(Date dateMin, Date dateMax)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if ((obj.getGenehmigt()!= null) && (dateMin.compareTo(obj.getGenehmigt())<=0) && (obj.getGenehmigt().compareTo(dateMax)<=0))
            {
                ausgabe.add(obj);
            }
        }
        //2 NKS für Double, außer Koordinaten... Koordinaten lasse ich so!
        for (Wka obj: ausgabe)
        {
            obj.setLeistung(Math.round(obj.getLeistung()*100.0)/100.0);
            obj.setNabenhoehe(Math.round(obj.getNabenhoehe()*100.0)/100.0);
            obj.setRotordurch(Math.round(obj.getRotordurch()*100.0)/100.0);
        }
        return ausgabe;
    }

    public List<Wka> getInbetriebBetween(Date dateMin, Date dateMax)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if ((obj.getInbetriebn()!= null) && (dateMin.compareTo(obj.getInbetriebn())<=0) && (obj.getInbetriebn().compareTo(dateMax)<=0))
            {
                ausgabe.add(obj);
            }
        }

        //2 NKS für Double, außer Koordinaten... Koordinaten lasse ich so!
        for (Wka obj: ausgabe)
        {
            obj.setLeistung(Math.round(obj.getLeistung()*100.0)/100.0);
            obj.setNabenhoehe(Math.round(obj.getNabenhoehe()*100.0)/100.0);
            obj.setRotordurch(Math.round(obj.getRotordurch()*100.0)/100.0);
        }

        return  ausgabe;
    }

    public List<Wka> getGenehmigtInbetriebBetween(Date dateMin, Date dateMax)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> ausgabe = new ArrayList<>();

        for (Wka obj: temp)
        {
            if (((obj.getGenehmigt()!= null) && (dateMin.compareTo(obj.getGenehmigt())<=0) && (obj.getGenehmigt().compareTo(dateMax)<=0)) ||((obj.getInbetriebn()!= null) && (dateMin.compareTo(obj.getInbetriebn())<=0) && (obj.getInbetriebn().compareTo(dateMax)<=0)))
            {
                ausgabe.add(obj);
            }
        }

        //2 NKS für Double, außer Koordinaten... Koordinaten lasse ich so!
        for (Wka obj: ausgabe)
        {
            obj.setLeistung(Math.round(obj.getLeistung()*100.0)/100.0);
            obj.setNabenhoehe(Math.round(obj.getNabenhoehe()*100.0)/100.0);
            obj.setRotordurch(Math.round(obj.getRotordurch()*100.0)/100.0);
        }

        return  ausgabe;
    }
    //Date dateMin, Date dateMax
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public List<LeistungTotalTag> statistikLeistungTag(Date dateMin, Date dateMax)
    {
        List<Wka> alle = wkaRepository.find_wka();
        List<Wka> temp = new ArrayList<>();

        //Einträge mit Inbetriebnahme= null weg!
        for (Wka obj: alle)
        {
            if (obj.getInbetriebn()!= null)
            {
                temp.add(obj);
            }
        }
        //Alle Einträge ordnen nach Date
        Collections.sort(temp, new Comparator<Wka>() {
            @Override
            public int compare(Wka o1, Wka o2) {
                return o1.getInbetriebn().compareTo(o2.getInbetriebn());
            }
        });

        //Distinct nach deutschem Datum, Uhrzeit vernachlässigt
        List<Wka> distinct = temp.stream().filter(distinctByKey(p ->p.getInbetriebn_s())).collect(Collectors.toList());

        List<LeistungTotalTag> vorhanden = new ArrayList<>();


        //Get all distinct dates
        for (Wka obj: distinct)
        {
            LeistungTotalTag lei = new LeistungTotalTag();
            lei.setDatum(obj.getInbetriebn());
            lei.setDatum_s(obj.getInbetriebn_s());
            vorhanden.add(lei);
        }


        //Alle Leistung in einem Tag addieren (sicherstellen!)
        for (Wka tem: temp)
        {
            for (LeistungTotalTag vorh: vorhanden)
            {
                if (tem.getInbetriebn_s().equals(vorh.getDatum_s()))
                {
                    vorh.addLeistung(tem.getLeistung());
                }
            }
        }

        //Kummulative Einträge (wächst im Laufe der Zeit)
        Integer max =0;
        List<LeistungTotalTag> cummulative = new ArrayList<>();
        for (LeistungTotalTag vorh : vorhanden)
        {
            LeistungTotalTag einzel = new LeistungTotalTag();

            einzel.setDatum(vorh.getDatum());
            einzel.setDatum_s(vorh.getDatum_s());

            Double total = 0.0;
            for (Integer i = 0; i<= max; i++)
            {
                total = total + vorhanden.get(i).getLeistung();
            }
            einzel.setLeistung(total);


            cummulative.add(einzel);
            max = max +1;
        }

        //Index cummulative maximal
        max = max -1;

        List<LeistungTotalTag> daily = new ArrayList<>();

        //Fehlende Tage in cummulative ergänzen
        Integer i =0;
        for (LeistungTotalTag cu : cummulative)
        {
            Date datum = cu.getDatum();
            if (i <max)
            {
                //WkaRepository.convertDateString(datum)!=cummulative.get(i+1).getDatum_s()
                while (!WkaRepository.convertDateString(datum).equals(cummulative.get(i+1).getDatum_s()))
                {
                    LeistungTotalTag neu = new LeistungTotalTag();

                    neu.setDatum(datum);
                    neu.setDatum_s(WkaRepository.convertDateString(datum));
                    neu.setLeistung(cu.getLeistung());

                    daily.add(neu);

                    datum = new Date(datum.getTime()+ (1000 * 60 * 60 * 24));
                }
            }
            else
            {
                LeistungTotalTag neu = new LeistungTotalTag();

                neu.setDatum(datum);
                neu.setDatum_s(WkaRepository.convertDateString(datum));
                neu.setLeistung(cu.getLeistung());

                daily.add(neu);
            }


            i++;
        }

        //Filter nach Zeitraum
        List<LeistungTotalTag> ausgabe = new ArrayList<>();

        for (LeistungTotalTag obj: daily)
        {
            if ((dateMin.compareTo(obj.getDatum())<=0) && (obj.getDatum().compareTo(dateMax)<=0))
            {
                ausgabe.add(obj);
            }
        }

        //2 NKS in ausgabe
        for (LeistungTotalTag obj : ausgabe)
        {
            obj.setLeistung(Math.round(obj.getLeistung()*100.0)/100.0);
        }

        return  ausgabe;

        //Täglich = ausgabe
        //Einige Punkte => ändere Filter mit cummulative!

    }

    public List<LeistungPlz> statistikLeistungPlz(Date dateMin, Date dateMax)
    {
        List <Wka> temp = wkaRepository.find_wka();
        List<Wka> filter = new ArrayList<>();

        //Wka filtern nach Zeitraum (!)
        for (Wka obj: temp)
        {
            if ((obj.getInbetriebn()!= null) && (dateMin.compareTo(obj.getInbetriebn())<=0) && (obj.getInbetriebn().compareTo(dateMax)<=0))
            {
                filter.add(obj);
            }
        }

        //Wka distinct nach PLZ
        List<Wka> distinct = filter.stream().filter(distinctByKey(p->p.getPLZ())).collect(Collectors.toList());

        //distinct nach LeistungPlz anpassen (!)
        List<LeistungPlz> unique = new ArrayList<>();

        for (Wka obj : distinct)
        {
            LeistungPlz lei = new LeistungPlz();
            lei.setPLZ(obj.getPLZ());
            lei.setPLZ_s(obj.getPLZ_s());
            unique.add(lei);
        }

        //Kummulativ berechnen filter Wka -> unique LeistungPlz
        for(Wka obj: filter)
        {
            for (LeistungPlz obj2: unique)
            {
                if (obj.getPLZ()==obj2.getPLZ())
                {
                    obj2.addLeistung(obj.getLeistung());
                }
            }
        }

        //unique nach Leistung ordnen descending
        Collections.sort(unique, new Comparator<LeistungPlz>() {
            @Override
            public int compare(LeistungPlz o1, LeistungPlz o2) {
                return o2.getLeistung().compareTo(o1.getLeistung());
            }
        });

        List<LeistungPlz> ausgabe = new ArrayList<>();
        //Größe unique >10 -> verkürzen
        if (unique.size()>10)
        {
            //List<LeistungPlz> ausgabe = new ArrayList<>();

            for (int i =0; i<=9;i++)
            {
                ausgabe.add(unique.get(i));
            }
            //return ausgabe;
        }
        else
        {
            ausgabe = unique;
            //return  unique;
        }

        //2 NKS in ausgabe
        for (LeistungPlz obj : ausgabe)
        {
            obj.setLeistung(Math.round(obj.getLeistung()*100.0)/100.0);
        }

        return ausgabe;

    }

}

