package com.avengers.api;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.junit.jupiter.api.Test;
/*
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.assertj.core.api.Assertions.assertThat;

*/

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendWkaApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception
    {
        this.base = new URL("http://127.0.0.1:"+port+"/api/");
    }

    //Test case 1: All entries can be retreived?
    @Test
    public void allTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("allTest() - Test start");

        String url = base.toString() + "all";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: all body?
        assertThat(n).isEqualTo(4676);

        LOG.info("Number of returned body from allTest(): {} out of 4676", n);
        LOG.info("-------------------------------------------------------------------------------");
    }
    //Test case 2: genehmigt between 1.1.2002 -> 2.1.2002
    @Test
    public void genehmigtTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("genehmigtTest() - Test start");

        String url = base.toString() + "search/genehmigt/between/01/01/2002/02/01/2002";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: nur 1 Body ausgegeben?
        assertThat(n).isEqualTo(1);

        //Checkpoint 3: Inhalt auf Richtigkeit prüfen!
        JSONObject ein = georesp.getJSONObject(0);

        assertThat(ein.getString("status")).isEqualTo("in Betrieb");
        assertThat(ein.getString("anl_Bez")).isEqualTo("WKA Enercon E66/18.70");
        assertThat(ein.getInt("nordwert")).isEqualTo(5868143);
        assertThat(ein.getDouble("longitude")).isEqualTo(14.03948079);
        assertThat(ein.getString("ortsteil")).isEqualTo("Neukünkendorf");
        assertThat(ein.getInt("ostwert")).isEqualTo(435479);
        assertThat(ein.getString("kreis")).isEqualTo("LK Uckermark");
        assertThat(ein.getString("betreiber")).isEqualTo("Energiequelle GmbH");
        assertThat(ein.getLong("bst_Nr")).isEqualTo(20734950000L); //!
        assertThat(ein.getString("bst_Name")).isEqualTo("Energiequelle GmbH");
        assertThat(ein.getDouble("latitude")).isEqualTo(52.95901496);
        assertThat(ein.getString("anl_Nr")).isEqualTo("0001");
        assertThat(ein.getString("lw_Nacht")).isEqualTo("unbekannt");
        assertThat(ein.getString("wka_ID")).isEqualTo("207349500000001");
        assertThat(ein.getString("lw_TAG")).isEqualTo("unbekannt");
        assertThat(ein.getString("alt_an_anz_s")).isEqualTo("");
        assertThat(ein.getInt("geme_Kenn")).isEqualTo(12073008);
        assertThat(ein.isNull("alt_an_anz")).isEqualTo(true); //!
        assertThat(ein.getString("genehmigt_s")).isEqualTo("01.01.2002");
        assertThat(ein.getDouble("leistung")).isEqualTo(1.8);
        assertThat(ein.getDouble("nabenhoehe")).isEqualTo(65.0);
        assertThat(ein.getDouble("rotordurch")).isEqualTo(70.0);
        assertThat(ein.getString("inbetriebn_s")).isEqualTo("07.05.2003");
        assertThat(ein.getString("ort")).isEqualTo("Angermünde");
        assertThat(ein.getInt("plz")).isEqualTo(16278);
        assertThat(ein.getString("plz_s")).isEqualTo("16278");
        assertThat(ein.getString("genehmigt")).isEqualTo("2002-01-01T23:00:00.000+00:00");
        assertThat(ein.getString("inbetriebn")).isEqualTo("2003-05-07T22:00:00.000+00:00");

        LOG.info("Number of returned body from genehmigtTest(): {}", n);
        LOG.info("Returned Body from genehmigtTest(): {}",resp);
        LOG.info("-------------------------------------------------------------------------------");

    }

    //TEST case 3: inbetriebnahme between
    @Test
    public void inbetriebTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("inbetriebTest() - Test start");

        String url = base.toString() + "search/inbetrieb/between/07/05/2003/08/05/2003";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: nur 1 Body ausgegeben?
        assertThat(n).isEqualTo(1);

        //Checkpoint 3: Inhalt auf Richtigkeit prüfen!
        JSONObject ein = georesp.getJSONObject(0);

        assertThat(ein.getString("status")).isEqualTo("in Betrieb");
        assertThat(ein.getString("anl_Bez")).isEqualTo("WKA Enercon E66/18.70");
        assertThat(ein.getInt("nordwert")).isEqualTo(5868143);
        assertThat(ein.getDouble("longitude")).isEqualTo(14.03948079);
        assertThat(ein.getString("ortsteil")).isEqualTo("Neukünkendorf");
        assertThat(ein.getInt("ostwert")).isEqualTo(435479);
        assertThat(ein.getString("kreis")).isEqualTo("LK Uckermark");
        assertThat(ein.getString("betreiber")).isEqualTo("Energiequelle GmbH");
        assertThat(ein.getLong("bst_Nr")).isEqualTo(20734950000L); //!
        assertThat(ein.getString("bst_Name")).isEqualTo("Energiequelle GmbH");
        assertThat(ein.getDouble("latitude")).isEqualTo(52.95901496);
        assertThat(ein.getString("anl_Nr")).isEqualTo("0001");
        assertThat(ein.getString("lw_Nacht")).isEqualTo("unbekannt");
        assertThat(ein.getString("wka_ID")).isEqualTo("207349500000001");
        assertThat(ein.getString("lw_TAG")).isEqualTo("unbekannt");
        assertThat(ein.getString("alt_an_anz_s")).isEqualTo("");
        assertThat(ein.getInt("geme_Kenn")).isEqualTo(12073008);
        assertThat(ein.isNull("alt_an_anz")).isEqualTo(true); //!
        assertThat(ein.getString("genehmigt_s")).isEqualTo("01.01.2002");
        assertThat(ein.getDouble("leistung")).isEqualTo(1.8);
        assertThat(ein.getDouble("nabenhoehe")).isEqualTo(65.0);
        assertThat(ein.getDouble("rotordurch")).isEqualTo(70.0);
        assertThat(ein.getString("inbetriebn_s")).isEqualTo("07.05.2003");
        assertThat(ein.getString("ort")).isEqualTo("Angermünde");
        assertThat(ein.getInt("plz")).isEqualTo(16278);
        assertThat(ein.getString("plz_s")).isEqualTo("16278");
        assertThat(ein.getString("genehmigt")).isEqualTo("2002-01-01T23:00:00.000+00:00");
        assertThat(ein.getString("inbetriebn")).isEqualTo("2003-05-07T22:00:00.000+00:00");

        LOG.info("Number of returned body from inbetriebTest(): {}", n);
        LOG.info("Returned Body from inbetriebTest(): {}",resp);
        LOG.info("-------------------------------------------------------------------------------");
    }


    //Test case 4: genehmigt and inbetriebnahme between
    @Test
    public void genInbTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("genInbTest() - Test start");

        String url = base.toString() + "search/geninb/between/01/04/2014/01/04/2014";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: nur 1 Body ausgegeben?
        assertThat(n).isEqualTo(1);

        //Checkpoint 3: Inhalt auf Richtigkeit prüfen!
        JSONObject ein = georesp.getJSONObject(0);

        assertThat(ein.getString("status")).isEqualTo("in Betrieb");
        assertThat(ein.getString("anl_Bez")).isEqualTo("WEA 1 - ENERCON E-101");
        assertThat(ein.getInt("nordwert")).isEqualTo(5760361);
        assertThat(ein.getDouble("longitude")).isEqualTo(13.2857449);
        assertThat(ein.getString("ortsteil")).isEqualTo("Petkus");
        assertThat(ein.getInt("ostwert")).isEqualTo(382271);
        assertThat(ein.getString("kreis")).isEqualTo("LK Teltow-Fläming");
        assertThat(ein.getString("betreiber")).isEqualTo("pro eko GmbH & Co. KG");
        assertThat(ein.getLong("bst_Nr")).isEqualTo(50720000456L); //!
        assertThat(ein.getString("bst_Name")).isEqualTo("WEA Charlottenfelde");
        assertThat(ein.getDouble("latitude")).isEqualTo(51.98143471);
        assertThat(ein.getString("anl_Nr")).isEqualTo("0001");
        assertThat(ein.getString("lw_Nacht")).isEqualTo("unbekannt");
        assertThat(ein.getString("wka_ID")).isEqualTo("507200004560001");
        assertThat(ein.getString("lw_TAG")).isEqualTo("unbekannt");
        assertThat(ein.getString("alt_an_anz_s")).isEqualTo("");
        assertThat(ein.getInt("geme_Kenn")).isEqualTo(12072014);
        assertThat(ein.isNull("alt_an_anz")).isEqualTo(true); //!
        assertThat(ein.getString("genehmigt_s")).isEqualTo("14.07.2011");
        assertThat(ein.getDouble("leistung")).isEqualTo(3.0);
        assertThat(ein.getDouble("nabenhoehe")).isEqualTo(135.4);
        assertThat(ein.getDouble("rotordurch")).isEqualTo(101.0);
        assertThat(ein.getString("inbetriebn_s")).isEqualTo("01.04.2014");
        assertThat(ein.getString("ort")).isEqualTo("Baruth/Mark");
        assertThat(ein.getInt("plz")).isEqualTo(14913);
        assertThat(ein.getString("plz_s")).isEqualTo("14913");
        assertThat(ein.getString("genehmigt")).isEqualTo("2011-07-14T22:00:00.000+00:00");
        assertThat(ein.getString("inbetriebn")).isEqualTo("2014-04-01T22:00:00.000+00:00");

        LOG.info("Number of returned body from genInbTest(): {}", n);
        LOG.info("Returned Body from genInbTest(): {}",resp);
        LOG.info("-------------------------------------------------------------------------------");
    }


    //Test case 5: cummulative Leistung between
    @Test
    public void statTagTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("statTagTest() - Test start");

        String url = base.toString() + "search/leistung/tag/06/02/2012/10/02/2012";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: kummulative Leistung aus 5 Tagen ausgegeben?
        assertThat(n).isEqualTo(5);

        //Checkpoint 3: Richtigkeit der Inhalte
        assertThat(georesp.getJSONObject(0).getString("datum")).isEqualTo("2012-02-06T23:00:00.000+00:00");
        assertThat(georesp.getJSONObject(0).getDouble("leistung")).isEqualTo(4015.46);
        assertThat(georesp.getJSONObject(0).getString("datum_s")).isEqualTo("06.02.2012");

        assertThat(georesp.getJSONObject(1).getString("datum")).isEqualTo("2012-02-07T23:00:00.000+00:00");
        assertThat(georesp.getJSONObject(1).getDouble("leistung")).isEqualTo(4017.46);
        assertThat(georesp.getJSONObject(1).getString("datum_s")).isEqualTo("07.02.2012");

        assertThat(georesp.getJSONObject(2).getString("datum")).isEqualTo("2012-02-08T23:00:00.000+00:00");
        assertThat(georesp.getJSONObject(2).getDouble("leistung")).isEqualTo(4020.26);
        assertThat(georesp.getJSONObject(2).getString("datum_s")).isEqualTo("08.02.2012");

        assertThat(georesp.getJSONObject(3).getString("datum")).isEqualTo("2012-02-09T23:00:00.000+00:00");
        assertThat(georesp.getJSONObject(3).getDouble("leistung")).isEqualTo(4020.26);
        assertThat(georesp.getJSONObject(3).getString("datum_s")).isEqualTo("09.02.2012");

        assertThat(georesp.getJSONObject(4).getString("datum")).isEqualTo("2012-02-10T23:00:00.000+00:00");
        assertThat(georesp.getJSONObject(4).getDouble("leistung")).isEqualTo(4020.26);
        assertThat(georesp.getJSONObject(4).getString("datum_s")).isEqualTo("10.02.2012");

        LOG.info("Number of returned body from statTagTest(): {}", n);
        LOG.info("Returned Body from statTagTest(): {}",resp);
        LOG.info("-------------------------------------------------------------------------------");
    }

    //Test case 6: Top 10 between inbetriebn
    @Test
    public void plzTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("plzTest() - Test start");

        String url = base.toString() + "search/leistung/plz/01/02/2002/15/02/2003";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: top 10 ausgegeben?
        assertThat(n).isEqualTo(10);

        //Checkpoint 3: Inhalt prüfen
        assertThat(georesp.getJSONObject(0).getDouble("leistung")).isEqualTo(2.0);
        assertThat(georesp.getJSONObject(0).getInt("plz")).isEqualTo(16269);
        assertThat(georesp.getJSONObject(0).getString("plz_s")).isEqualTo("16269");

        assertThat(georesp.getJSONObject(1).getDouble("leistung")).isEqualTo(2.0);
        assertThat(georesp.getJSONObject(1).getInt("plz")).isEqualTo(14913);
        assertThat(georesp.getJSONObject(1).getString("plz_s")).isEqualTo("14913");

        assertThat(georesp.getJSONObject(2).getDouble("leistung")).isEqualTo(1.8);
        assertThat(georesp.getJSONObject(2).getInt("plz")).isEqualTo(16928);
        assertThat(georesp.getJSONObject(2).getString("plz_s")).isEqualTo("16928");

        assertThat(georesp.getJSONObject(3).getDouble("leistung")).isEqualTo(1.8);
        assertThat(georesp.getJSONObject(3).getInt("plz")).isEqualTo(16348);
        assertThat(georesp.getJSONObject(3).getString("plz_s")).isEqualTo("16348");

        assertThat(georesp.getJSONObject(4).getDouble("leistung")).isEqualTo(1.8);
        assertThat(georesp.getJSONObject(4).getInt("plz")).isEqualTo(15324);
        assertThat(georesp.getJSONObject(4).getString("plz_s")).isEqualTo("15324");

        assertThat(georesp.getJSONObject(5).getDouble("leistung")).isEqualTo(1.8);
        assertThat(georesp.getJSONObject(5).getInt("plz")).isEqualTo(15936);
        assertThat(georesp.getJSONObject(5).getString("plz_s")).isEqualTo("15936");

        assertThat(georesp.getJSONObject(6).getDouble("leistung")).isEqualTo(1.5);
        assertThat(georesp.getJSONObject(6).getInt("plz")).isEqualTo(16775);
        assertThat(georesp.getJSONObject(6).getString("plz_s")).isEqualTo("16775");

        assertThat(georesp.getJSONObject(7).getDouble("leistung")).isEqualTo(1.5);
        assertThat(georesp.getJSONObject(7).getInt("plz")).isEqualTo(16356);
        assertThat(georesp.getJSONObject(7).getString("plz_s")).isEqualTo("16356");

        assertThat(georesp.getJSONObject(8).getDouble("leistung")).isEqualTo(1.5);
        assertThat(georesp.getJSONObject(8).getInt("plz")).isEqualTo(16352);
        assertThat(georesp.getJSONObject(8).getString("plz_s")).isEqualTo("16352");

        assertThat(georesp.getJSONObject(9).getDouble("leistung")).isEqualTo(1.5);
        assertThat(georesp.getJSONObject(9).getInt("plz")).isEqualTo(16359);
        assertThat(georesp.getJSONObject(9).getString("plz_s")).isEqualTo("16359");

        LOG.info("Number of returned body from plzTest(): {}", n);
        LOG.info("Returned Body from plzTest(): {}",resp);
        LOG.info("-------------------------------------------------------------------------------");
    }

    //Test case 7: PLZ Angabe less than 10
    @Test
    public void plzLessTest()
    {
        LOG.info("-------------------------------------------------------------------------------");
        LOG.info("plzLessTest() - Test start");

        String url = base.toString() + "search/leistung/plz/01/02/2002/31/02/2002";

        ResponseEntity<String> response = template.getForEntity(url,String.class);

        //Checkpoint 1 : Status code 200?
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        //Response kriegen und als JSON konvertieren (und Länge kriegen)
        String resp = response.getBody();
        String resp2 = "{\"data\": " + resp + "}";
        JSONObject obj = new JSONObject(resp2);
        JSONArray georesp = obj.getJSONArray("data");
        int n = georesp.length();

        //Checkpoint 2: <10 ausgegeben?
        assertThat(n).isLessThan(10);

        //Checkpoint 3: Inhalt prüfen
        assertThat(georesp.getJSONObject(0).getDouble("leistung")).isEqualTo(2.0);
        assertThat(georesp.getJSONObject(0).getInt("plz")).isEqualTo(16269);
        assertThat(georesp.getJSONObject(0).getString("plz_s")).isEqualTo("16269");

        assertThat(georesp.getJSONObject(1).getDouble("leistung")).isEqualTo(1.8);
        assertThat(georesp.getJSONObject(1).getInt("plz")).isEqualTo(15936);
        assertThat(georesp.getJSONObject(1).getString("plz_s")).isEqualTo("15936");

        assertThat(georesp.getJSONObject(2).getDouble("leistung")).isEqualTo(0.6);
        assertThat(georesp.getJSONObject(2).getInt("plz")).isEqualTo(19348);
        assertThat(georesp.getJSONObject(2).getString("plz_s")).isEqualTo("19348");


        LOG.info("Number of returned body from plzLessTest(): {}", n);
        LOG.info("Returned Body from plzLessTest(): {}",resp);
        LOG.info("-------------------------------------------------------------------------------");
    }

}
