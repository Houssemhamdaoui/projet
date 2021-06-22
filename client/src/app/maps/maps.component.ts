import { Component, OnInit,  Output, EventEmitter  } from '@angular/core';
import MarkerClusterer from '@google/markerclustererplus';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {WkaService} from '../wka.service';
declare const google: any;
import {MapsService} from '../map.service';
@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css'],
 /* providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ],*/
})
export class MapsComponent implements OnInit {



  constructor(public http: HttpClient, private wkaService: WkaService,
    private MapsService: MapsService
    ) { }

  private today = new Date();
  private month = this.today.getMonth();
  private year = this.today.getFullYear();

  public myLatlng = new google.maps.LatLng(52.516266, 13.377775);
  public mapOptions = {
    zoom: 8,
    center: this.myLatlng,
    scrollwheel: false, //we disable de scroll over the map, it is a really annoing when you scroll through page
    styles: [{
        "featureType": "water",
        "stylers": [{
            "saturation": 43
        }, {
            "lightness": -11
        }, {
            "hue": "#0088ff"
        }]
    }, {
        "featureType": "road",
        "elementType": "geometry.fill",
        "stylers": [{
            "hue": "#ff0000"
        }, {
            "saturation": -100
        }, {
            "lightness": 99
        }]
    }, {
        "featureType": "road",
        "elementType": "geometry.stroke",
        "stylers": [{
            "color": "#808080"
        }, {
            "lightness": 54
        }]
    }, {
        "featureType": "landscape.man_made",
        "elementType": "geometry.fill",
        "stylers": [{
            "color": "#ece2d9"
        }]
    }, {
        "featureType": "poi.park",
        "elementType": "geometry.fill",
        "stylers": [{
            "color": "#ccdca1"
        }]
    }, {
        "featureType": "road",
        "elementType": "labels.text.fill",
        "stylers": [{
            "color": "#767676"
        }]
    }, {
        "featureType": "road",
        "elementType": "labels.text.stroke",
        "stylers": [{
            "color": "#ffffff"
        }]
    }, {
        "featureType": "poi",
        "stylers": [{
            "visibility": "off"
        }]
    }, {
        "featureType": "landscape.natural",
        "elementType": "geometry.fill",
        "stylers": [{
            "visibility": "on"
        }, {
            "color": "#b8cb93"
        }]
    }, {
        "featureType": "poi.park",
        "stylers": [{
            "visibility": "on"
        }]
    }, {
        "featureType": "poi.sports_complex",
        "stylers": [{
            "visibility": "on"
        }]
    }, {
        "featureType": "poi.medical",
        "stylers": [{
            "visibility": "on"
        }]
    }, {
        "featureType": "poi.business",
        "stylers": [{
            "visibility": "simplified"
        }]
    }]

};



  wkas = [];
  clusterMarkers = [];

  empty() {
    //empty your array
    this.clusterMarkers = [];
}

getInstalledAndApprovedWkaList(map, dayMinGen, monthMinGen, yearMinGen, dayMaxGen, monthMaxGen, yearMaxGen){ //Liste aller genehmigten WKAs
  this.wkaService
  .getInstalledAndApprovedWkas(dayMinGen, monthMinGen, yearMinGen, dayMaxGen, monthMaxGen, yearMaxGen)
  .subscribe((data:any) => {

   this.wkas = data;
   var clusterMarker = this.clusterMarkers[i];

   var infoWin = new google.maps.InfoWindow();

   for (var i = 0; i < this.wkas.length; i++){
     var obj = this.wkas[i];

     var marker = new google.maps.Marker({
       position: new google.maps.LatLng(obj.latitude,obj.longitude),
       map: map,
     })
     this.clusterMarkers.push(marker);

     const wkaInfo =
     '<div id="content">' +
     '<div id="siteNotice">' +
     "</div>" +
     '<h2 id="firstHeading" class="secondHeading">Windkraftanlage</h2>' +
     '<div id="bodyContent">' +

     '<table>' +
     '<tr> <td>Betreiber </td> <td>' + obj.betreiber + '</td> </tr>'+
     '<tr> <td>bst_Nr</td> <td>' + obj.bst_Nr + '</td> </tr>'+
     '<tr> <td>bst_Name</td> <td>' + obj.bst_Name + '</td> </tr>'+
     '<tr> <td>Ort</td> <td>' + obj.ort + '</td> </tr>'+
     '<tr> <td>Ortsteil</td> <td>' + obj.ortsteil + '</td> </tr>'+
     '<tr> <td>Genehmigt seit</td> <td>' + obj.genehmigt_s + '</td> </tr>'+
     '<tr> <td>kreis</td> <td>' + obj.kreis + '</td> </tr>'+
     '<tr> <td>geme_Kenn</td> <td>' + obj.geme_Kenn + '</td> </tr>'+
     '<tr> <td>Postleitzahl</td> <td>' + obj.plz_s + '</td> </tr>'+
     '<tr> <td>Inbetriebnahme seit</td> <td>' + obj.inbetriebn_s + '</td> </tr>'+
     '<tr> <td>alt_an_anz</td> <td>' + obj.alt_an_anz_s + '</td> </tr>'+
     '<tr> <td>Leistung</td> <td>' + obj.leistung + '</td> </tr>'+
     '<tr> <td>Status</td> <td>' + obj.status + '</td> </tr>'+
     '<tr> <td>Nabenhoehe</td> <td>' + obj.nabenhoehe + '</td> </tr>'+
     '<tr> <td>Rotordurchmesser</td> <td>' + obj.rotordurch + '</td> </tr>'+
     '<tr> <td>lw_TAG</td> <td>' + obj.lw_TAG + '</td> </tr>'+
     '<tr> <td>lw_Nacht</td> <td>' + obj.lw_Nacht + '</td> </tr>' +
     '</table>';

     google.maps.event.addListener( this.clusterMarkers[i], 'click', function(evt) {
       infoWin.setContent(wkaInfo);
       infoWin.open(map, this);
     })
   }

   var markerCluster = new MarkerClusterer(map, this.clusterMarkers ,
     {imagePath: `https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m`});

  })

}

getInstalledWkaList(map, minDate, minMonth, minYear, maxDate, maxMonth, maxYear){ //Liste aller genehmigten WKAs
  this.wkaService
  .getInstalledWkas(minDate, minMonth, minYear, maxDate, maxMonth, maxYear)
  .subscribe((data:any) => {

   this.wkas = data;
   var clusterMarker = this.clusterMarkers[i];

   var infoWin = new google.maps.InfoWindow();

   for (var i = 0; i < this.wkas.length; i++){
     var obj = this.wkas[i];

     var marker = new google.maps.Marker({
       position: new google.maps.LatLng(obj.latitude,obj.longitude),
       map: map,
     })
     this.clusterMarkers.push(marker);

     const wkaInfo =
     '<div id="content">' +
     '<div id="siteNotice">' +
     "</div>" +
     '<h2 id="firstHeading" class="secondHeading">Windkraftanlage</h2>' +
     '<div id="bodyContent">' +

     '<table>' +
     '<tr> <td>Betreiber </td> <td>' + obj.betreiber + '</td> </tr>'+
     '<tr> <td>bst_Nr</td> <td>' + obj.bst_Nr + '</td> </tr>'+
     '<tr> <td>bst_Name</td> <td>' + obj.bst_Name + '</td> </tr>'+
     '<tr> <td>Ort</td> <td>' + obj.ort + '</td> </tr>'+
     '<tr> <td>Ortsteil</td> <td>' + obj.ortsteil + '</td> </tr>'+
     '<tr> <td>Genehmigt seit</td> <td>' + obj.genehmigt_s + '</td> </tr>'+
     '<tr> <td>kreis</td> <td>' + obj.kreis + '</td> </tr>'+
     '<tr> <td>geme_Kenn</td> <td>' + obj.geme_Kenn + '</td> </tr>'+
     '<tr> <td>Postleitzahl</td> <td>' + obj.plz_s + '</td> </tr>'+
     '<tr> <td>Inbetriebnahme seit</td> <td>' + obj.inbetriebn_s + '</td> </tr>'+
     '<tr> <td>alt_an_anz</td> <td>' + obj.alt_an_anz_s + '</td> </tr>'+
     '<tr> <td>Leistung</td> <td>' + obj.leistung + '</td> </tr>'+
     '<tr> <td>Status</td> <td>' + obj.status + '</td> </tr>'+
     '<tr> <td>Nabenhoehe</td> <td>' + obj.nabenhoehe + '</td> </tr>'+
     '<tr> <td>Rotordurchmesser</td> <td>' + obj.rotordurch + '</td> </tr>'+
     '<tr> <td>lw_TAG</td> <td>' + obj.lw_TAG + '</td> </tr>'+
     '<tr> <td>lw_Nacht</td> <td>' + obj.lw_Nacht + '</td> </tr>' +
     '</table>';

     google.maps.event.addListener( this.clusterMarkers[i], 'click', function(evt) {
       infoWin.setContent(wkaInfo);
       infoWin.open(map, this);
     })
   }

   var markerCluster = new MarkerClusterer(map, this.clusterMarkers ,
     {imagePath: `https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m`});

  })

}



   getApprovedWkaList(map, minDate, minMonth, minYear, maxDate, maxMonth, maxYear){ //Liste aller genehmigten WKAs
     this.wkaService
     .getApprovedWkas(minDate, minMonth, minYear, maxDate, maxMonth, maxYear)
     .subscribe((data:any) => {

      this.wkas = data;
      var clusterMarker = this.clusterMarkers[i];

      var infoWin = new google.maps.InfoWindow();

      for (var i = 0; i < this.wkas.length; i++){
        var obj = this.wkas[i];

        var marker = new google.maps.Marker({
          position: new google.maps.LatLng(obj.latitude,obj.longitude),
          map: map,
        })
        this.clusterMarkers.push(marker);

        const wkaInfo =
        '<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h2 id="firstHeading" class="secondHeading">Windkraftanlage</h2>' +
        '<div id="bodyContent">' +

        '<table>' +
     '<tr> <td>Betreiber </td> <td>' + obj.betreiber + '</td> </tr>'+
     '<tr> <td>bst_Nr</td> <td>' + obj.bst_Nr + '</td> </tr>'+
     '<tr> <td>bst_Name</td> <td>' + obj.bst_Name + '</td> </tr>'+
     '<tr> <td>Ort</td> <td>' + obj.ort + '</td> </tr>'+
     '<tr> <td>Ortsteil</td> <td>' + obj.ortsteil + '</td> </tr>'+
     '<tr> <td>Genehmigt seit</td> <td>' + obj.genehmigt_s + '</td> </tr>'+
     '<tr> <td>kreis</td> <td>' + obj.kreis + '</td> </tr>'+
     '<tr> <td>geme_Kenn</td> <td>' + obj.geme_Kenn + '</td> </tr>'+
     '<tr> <td>Postleitzahl</td> <td>' + obj.plz_s + '</td> </tr>'+
     '<tr> <td>Inbetriebnahme seit</td> <td>' + obj.inbetriebn_s + '</td> </tr>'+
     '<tr> <td>alt_an_anz</td> <td>' + obj.alt_an_anz_s + '</td> </tr>'+
     '<tr> <td>Leistung</td> <td>' + obj.leistung + '</td> </tr>'+
     '<tr> <td>Status</td> <td>' + obj.status + '</td> </tr>'+
     '<tr> <td>Nabenhoehe</td> <td>' + obj.nabenhoehe + '</td> </tr>'+
     '<tr> <td>Rotordurchmesser</td> <td>' + obj.rotordurch + '</td> </tr>'+
     '<tr> <td>lw_TAG</td> <td>' + obj.lw_TAG + '</td> </tr>'+
     '<tr> <td>lw_Nacht</td> <td>' + obj.lw_Nacht + '</td> </tr>' +
     '</table>';

        google.maps.event.addListener( this.clusterMarkers[i], 'click', function(evt) {
          infoWin.setContent(wkaInfo);
          infoWin.open(map, this);
        })
      }

      var markerCluster = new MarkerClusterer(map, this.clusterMarkers ,
        {imagePath: `https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m`});

     })

   }

    getWkaList(map){
    this.wkaService
    .getWkas()
    .subscribe((data:any) =>{
      this.wkas = data;
      var clusterMarker = this.clusterMarkers[i];

      var infoWin = new google.maps.InfoWindow();

      for (var i = 0; i < this.wkas.length; i++){
        var obj = this.wkas[i];



        var marker = new google.maps.Marker({
          position: new google.maps.LatLng(obj.latitude,obj.longitude),
          map: map,
        })
        this.clusterMarkers.push(marker);

        const wkaInfo =
        '<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h2 id="firstHeading" class="secondHeading">Windkraftanlage</h2>' +
        '<div id="bodyContent">' +

        '<table>' +
     '<tr> <td>Betreiber </td> <td>' + obj.betreiber + '</td> </tr>'+
     '<tr> <td>bst_Nr</td> <td>' + obj.bst_Nr + '</td> </tr>'+
     '<tr> <td>bst_Name</td> <td>' + obj.bst_Name + '</td> </tr>'+
     '<tr> <td>Ort</td> <td>' + obj.ort + '</td> </tr>'+
     '<tr> <td>Ortsteil</td> <td>' + obj.ortsteil + '</td> </tr>'+
     '<tr> <td>Genehmigt seit</td> <td>' + obj.genehmigt_s + '</td> </tr>'+
     '<tr> <td>kreis</td> <td>' + obj.kreis + '</td> </tr>'+
     '<tr> <td>geme_Kenn</td> <td>' + obj.geme_Kenn + '</td> </tr>'+
     '<tr> <td>Postleitzahl</td> <td>' + obj.plz_s + '</td> </tr>'+
     '<tr> <td>Inbetriebnahme seit</td> <td>' + obj.inbetriebn_s + '</td> </tr>'+
     '<tr> <td>alt_an_anz</td> <td>' + obj.alt_an_anz_s + '</td> </tr>'+
     '<tr> <td>Leistung</td> <td>' + obj.leistung + '</td> </tr>'+
     '<tr> <td>Status</td> <td>' + obj.status + '</td> </tr>'+
     '<tr> <td>Nabenhoehe</td> <td>' + obj.nabenhoehe + '</td> </tr>'+
     '<tr> <td>Rotordurchmesser</td> <td>' + obj.rotordurch + '</td> </tr>'+
     '<tr> <td>lw_TAG</td> <td>' + obj.lw_TAG + '</td> </tr>'+
     '<tr> <td>lw_Nacht</td> <td>' + obj.lw_Nacht + '</td> </tr>' +
     '</table>';

        google.maps.event.addListener( this.clusterMarkers[i], 'click', function(evt) {
          infoWin.setContent(wkaInfo);
          infoWin.open(map, this);
        })
      }

      var markerCluster = new MarkerClusterer(map, this.clusterMarkers ,
        {imagePath: `https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m`});
    })



  }

  getWkaLastFiveYears(map){
    this.wkaService
    .getLastFiveYears()
    .subscribe((data:any) =>{
      this.wkas = data;
      var clusterMarker = this.clusterMarkers[i];

      var infoWin = new google.maps.InfoWindow();

      for (var i = 0; i < this.wkas.length; i++){
        var obj = this.wkas[i];



        var marker = new google.maps.Marker({
          position: new google.maps.LatLng(obj.latitude,obj.longitude),
          map: map,
        })
        this.clusterMarkers.push(marker);

        const wkaInfo =
        '<div id="content">' +
        '<div id="siteNotice">' +
        "</div>" +
        '<h2 id="firstHeading" class="secondHeading">Windkraftanlage</h2>' +
        '<div id="bodyContent">' +

        '<table>' +
     '<tr> <td>Betreiber </td> <td>' + obj.betreiber + '</td> </tr>'+
     '<tr> <td>bst_Nr</td> <td>' + obj.bst_Nr + '</td> </tr>'+
     '<tr> <td>bst_Name</td> <td>' + obj.bst_Name + '</td> </tr>'+
     '<tr> <td>Ort</td> <td>' + obj.ort + '</td> </tr>'+
     '<tr> <td>Ortsteil</td> <td>' + obj.ortsteil + '</td> </tr>'+
     '<tr> <td>Genehmigt seit</td> <td>' + obj.genehmigt_s + '</td> </tr>'+
     '<tr> <td>kreis</td> <td>' + obj.kreis + '</td> </tr>'+
     '<tr> <td>geme_Kenn</td> <td>' + obj.geme_Kenn + '</td> </tr>'+
     '<tr> <td>Postleitzahl</td> <td>' + obj.plz_s + '</td> </tr>'+
     '<tr> <td>Inbetriebnahme seit</td> <td>' + obj.inbetriebn_s + '</td> </tr>'+
     '<tr> <td>alt_an_anz</td> <td>' + obj.alt_an_anz_s + '</td> </tr>'+
     '<tr> <td>Leistung</td> <td>' + obj.leistung + '</td> </tr>'+
     '<tr> <td>Status</td> <td>' + obj.status + '</td> </tr>'+
     '<tr> <td>Nabenhoehe</td> <td>' + obj.nabenhoehe + '</td> </tr>'+
     '<tr> <td>Rotordurchmesser</td> <td>' + obj.rotordurch + '</td> </tr>'+
     '<tr> <td>lw_TAG</td> <td>' + obj.lw_TAG + '</td> </tr>'+
     '<tr> <td>lw_Nacht</td> <td>' + obj.lw_Nacht + '</td> </tr>' +
     '</table>';

        google.maps.event.addListener( this.clusterMarkers[i], 'click', function(evt) {
          infoWin.setContent(wkaInfo);
          infoWin.open(map, this);
        })
      }

      var markerCluster = new MarkerClusterer(map, this.clusterMarkers ,
        {imagePath: `https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m`});
    })



  }

  lMinDate:any ;
  lMinMonth: any ;
  lMinYear:any ;
  lMaxDate: any ;
  lMaxMonth: any ;
  lMaxYear: any ;

  lMinDateInb:any;
  lMinMonthInb: any;
  lMinYearInb: any;
  lMaxDateInb: any;
  lMaxMonthInb: any;
  lMaxYearInb: any;

  lGenehmigt: any;
  lInbetrieb: any;


  clickEvent(e){
    console.log(e);
    var googleMap = new google.maps.Map(document.getElementById("map"), this.mapOptions);
    this.lMinDate = e.minDate
    this.lMinMonth= e.minMonth;
    this.lMinYear= e.minYear ;
    this.lMaxDate=e.maxDate;
    this.lMaxMonth=e.maxMonth ;
    this.lMaxYear=e.maxYear ;

    this.lMinDateInb=e.minDateInb;
    this.lMinMonthInb=e.minMonthInb;
    this.lMinYearInb=e.minYearInb;
    this.lMaxDateInb=e.maxDateInb;
    this.lMaxMonthInb=e.maxMonthInb;
    this.lMaxYearInb=e.maxYearInb;

    this.lGenehmigt=e.genehmigt;
    this.lInbetrieb=e.inbetrieb;

    this.MapsService.setFilterBody(
      this.lMinDate,
      this.lMinMonth,
      this.lMinYear,
      this.lMaxDate,
      this.lMaxMonth,
      this.lMaxYear,

      this.lMinDateInb,
      this.lMinMonthInb,
      this.lMinYearInb,
      this.lMaxDateInb,
      this.lMaxMonthInb,
      this.lMaxYearInb,

      this.lGenehmigt,
      this.lInbetrieb);
      console.log( this.MapsService.getMinDateInb());

    if(this.lGenehmigt && this.lInbetrieb){
      this.empty();

      this.getInstalledAndApprovedWkaList(
        googleMap,
        this.lMinDate,
        this.lMinMonth,
        this.lMinYear,
        this.lMaxDate,
        this.lMaxMonth,
        this.lMaxYear);
    } else if (this.lGenehmigt){
      this.empty();
      this.getApprovedWkaList(
        googleMap,
        this.lMinDate,
        this.lMinMonth,
        this.lMinYear,
        this.lMaxDate,
        this.lMaxMonth,
        this.lMaxYear );
    } else if (this.lInbetrieb){
      this.empty();
      this.getInstalledWkaList(googleMap,
        this.lMinDateInb,
        this.lMinMonthInb,
        this.lMinYearInb,
        this.lMaxDateInb,
        this.lMaxMonthInb,
        this.lMaxYearInb);
    } else {
      this.empty();
      this.getWkaList(new google.maps.Map(document.getElementById("map"), this.mapOptions));
    }

  }

  ngOnInit() {
    this.getWkaLastFiveYears(new google.maps.Map(document.getElementById("map"), this.mapOptions));
    this.MapsService.setUndefined();
  }
}
