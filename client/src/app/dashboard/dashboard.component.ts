import { Component, OnInit, EventEmitter } from '@angular/core';
import * as Chartist from 'chartist';
import { ChartDataSets, ChartOptions } from 'chart.js';
import {LeistungPlzService} from '../LeistungPlz.service'
import {LeistungTotalTagService} from '../LeistungTotalTag.service'
import {FormGroup, FormControl} from '@angular/forms';
import { FilterComponent } from '../filter/filter.component';
import {MapsService} from '../map.service';
import {MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {ThemePalette} from '@angular/material/core';
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import {default as _rollupMoment} from 'moment';

const moment = _rollupMoment || _moment;
// See the Moment.js docs for the meaning of these formats:
// https://momentjs.com/docs/#/displaying/format/
export const MY_FORMATS = {
  parse: {
    dateInput: 'DD.MM.YYY',
  },
  display: {
    dateInput: 'DD.MM.YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
  ]

})
export class DashboardComponent implements OnInit {
  public lineBigDashboardChartType;
  public gradientStroke;
  public chartColor;
  public canvas : any;
  public ctx;
  public gradientFill;
  public lineBigDashboardChartData:Array<any>;
  public lineBigDashboardChartOptions:any;
  public lineBigDashboardChartLabels:Array<any>;
  public lineBigDashboardChartColors:Array<any>

  public barChartData: Array<any>;
  public barChartLabels:Array<any>;
  public barChartOptions: any;

  public gradientChartOptionsConfiguration: any;
  public gradientChartOptionsConfigurationWithNumbersAndGrid: any;

  public lineChartType;
  public lineChartData:Array<any>;
  public lineChartOptions:any;
  public lineChartLabels:Array<any>;
  public lineChartColors:Array<any>
  public lineChartLegend;

  private today = new Date();
  private dateToday = this.today.getDate();
  private month = this.today.getMonth();
  private year = this.today.getFullYear();


  public show:boolean = false;

  public showApproved:boolean = false;
  public buttonName:any = 'Zeiträume';

  approved :boolean= false;
  installed :boolean= false;
  theCheckbox :boolean;
  date = new FormControl(moment());

  range = new FormGroup({
    start: new FormControl(new Date(this.year-5, this.month, this.today.getDate())),
    end: new FormControl(new Date(this.year, this.month, this.today.getDate()))
  });

  toggle() {
    this.show = !this.show;

  }


  checkApproved(e){

    if(e.checked){
      this.approved = true;
    } else {
      this.approved = false;
    }
  }

  checkInstalled(e){
    if(e.checked){
      this.installed = true;
    } else {
      this.installed = false;
    }
  }

  // events
  public chartClicked(e:any):void {
    //console.log(e);
  }

  public chartHovered(e:any):void {
    //console.log(e);
  }
  public hexToRGB(hex, alpha) {
    var r = parseInt(hex.slice(1, 3), 16),
      g = parseInt(hex.slice(3, 5), 16),
      b = parseInt(hex.slice(5, 7), 16);

    if (alpha) {
      return "rgba(" + r + ", " + g + ", " + b + ", " + alpha + ")";
    } else {
      return "rgb(" + r + ", " + g + ", " + b + ")";
    }
  }

  constructor(
    private LeistungPlzService: LeistungPlzService,
    private LeistungTotalTagService: LeistungTotalTagService,
    private MapsService: MapsService
    ) { }

  powerTotalRaw = [];
  powerTotal = [];
  powerDate = [];

  powerTotalPlzRaw = [];
  powerTotalPlz = [];
  powerPlz = [];

  empty(){
    this.powerTotal = [];
    this.powerTotalRaw = [];
    this.powerDate = [];
    this.powerTotalPlzRaw = [];
    this.powerTotalPlz = [];
    this.powerPlz = [];
  }
  getLineChartPowerTotal(minDate, minMonth, minYear, maxDate, maxMonth, maxYear){
    this.LeistungTotalTagService
    .getLeistungTotalTag(minDate, minMonth, minYear, maxDate, maxMonth, maxYear)
    .subscribe((data:any) => {
      this.powerTotalRaw = data;

      for (var i = 0; i < this.powerTotalRaw.length; i++){
        var obj = this.powerTotalRaw[i];
        this.powerTotal.push(obj.leistung);
        this.powerDate.push(obj.datum_s);
      }
      console.log(this.powerDate);
      console.log(this.powerTotal);
      this.lineChartData = [
        {
          label: "Leistung in Mega Watt",
          pointBorderWidth: 0,
          pointHoverRadius: 1,
          pointHoverBorderWidth: 1,
          pointBackgroundColor: 'rgba(148,159,177,1)',
          pointBorderColor: '#fff',
          pointHoverBackgroundColor: '#fff',
          pointHoverBorderColor: 'rgba(148,159,177,0.8)',
          pointRadius: 0,
          fill: true,
          borderWidth: 0,
          data: this.powerTotal
        }
      ];

      this.lineChartLabels = this.powerDate;


    })
  }

  getBarChartPowerTotal(minDate, minMonth, minYear, maxDate, maxMonth, maxYear){
    this.LeistungPlzService
    .getLeistungPlz(minDate, minMonth, minYear, maxDate, maxMonth, maxYear)
    .subscribe((leistung:any) => {
      this.powerTotalPlzRaw = leistung;
      for (var j = 0; j < this.powerTotalPlzRaw.length; j++){
        var obj = this.powerTotalPlzRaw[j];
        this.powerTotalPlz.push(obj.leistung);
        this.powerPlz.push(obj.plz_s);
      }

      console.log(this.powerPlz);
      console.log(this.powerTotalPlz)

      this.barChartData = [
        {
          label: "Leistung im Mega Watt",
          data: this.powerTotalPlz

        }
      ];
      this.barChartLabels = this.powerPlz;

    })
  }

  // click on start or search
  clickEvent(e){
    // todo: start initialize chart
    var minDate = new Date(this.range.value.start).getDate();
    var minMonth = new Date(this.range.value.start).getMonth() +1;
    var minYear = new Date(this.range.value.start).getFullYear();

    var maxDate = new Date(this.range.value.end).getDate();
    var maxMonth = new Date(this.range.value.end).getMonth() +1;
    var maxYear = new Date(this.range.value.end).getFullYear();

    //console.log(this.lGenehmigt)

     this.empty();
     this.getLineChartPowerTotal(minDate,minMonth, minYear, maxDate, maxMonth,maxYear);
     this.getBarChartPowerTotal(minDate,minMonth, minYear, maxDate, maxMonth,maxYear);
    }
  ngOnInit() {

    var mapMinDateInb = this.MapsService.getMinDateInb();
    var mapMinMonthInb = this.MapsService.getMinMonthInb();
    var mapMinYearInb = this.MapsService.getMinYearInb();
    var mapMaxDateInb = this.MapsService.getMaxDateInb();
    var mapMaxMonth = this.MapsService.getMaxMonthInb();
    var mapMaxYearInb = this.MapsService.getMaxYearInb();
    var inbetrieb = this.MapsService.getInbetrieb();
    // TODO: not able to get the date from the map
    //console.log(this.MapsService.getInbetrieb());
    if (inbetrieb){
      this.getLineChartPowerTotal(mapMinDateInb,mapMinMonthInb,mapMinYearInb,mapMaxDateInb,mapMaxMonth,mapMaxYearInb);
      this.getBarChartPowerTotal(mapMinDateInb,mapMinMonthInb,mapMinYearInb,mapMaxDateInb,mapMaxMonth,mapMaxYearInb);
    }
    else {
      this.getLineChartPowerTotal(this.dateToday,this.month,this.year-5,this.dateToday,this.month,this.year);
      this.getBarChartPowerTotal(this.dateToday,this.month,this.year-5,this.dateToday,this.month,this.year);
    }



    this.chartColor = "#FFFFFF";
    this.canvas = document.getElementById("bigDashboardChart");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#80b6f4');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 200, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, "rgba(255, 255, 255, 0.24)");

    this.barChartOptions = {
      responsive: true,
      // We use these empty structures as placeholders for dynamic theming.
      scales: { xAxes: [{}], yAxes: [{}] },
      plugins: {
        datalabels: {
          anchor: 'end',
          align: 'end',
        }
      }
    };

      this.lineBigDashboardChartColors = [
       {
         backgroundColor: '#343f56',
         borderColor: '#343f56',
         pointBorderColor: this.chartColor,
         pointBackgroundColor: "#2c2c2c",
         pointHoverBackgroundColor: "#2c2c2c",
         pointHoverBorderColor: this.chartColor,
       }
     ];
    this.lineBigDashboardChartOptions = {

          layout: {
              padding: {
                  left: 20,
                  right: 20,
                  top: 0,
                  bottom: 0
              }
          },
          maintainAspectRatio: false,
          tooltips: {
            backgroundColor: '#fff',
            titleFontColor: '#333',
            bodyFontColor: '#666',
            bodySpacing: 4,
            xPadding: 12,
            mode: "nearest",
            intersect: 0,
            position: "nearest"
          },
          legend: {
              position: "bottom",
              fillStyle: "#FFF",
              display: false
          },
          scales: {
              yAxes: [{


              }],
              xAxes: [{

              }]
          }
    };

    this.lineBigDashboardChartType = 'bar';


    this.gradientChartOptionsConfiguration = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: 1,
      scales: {
        yAxes: [{
          display: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }],
        xAxes: [{
          display: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };



    this.gradientChartOptionsConfigurationWithNumbersAndGrid = {
      maintainAspectRatio: false,
      legend: {
        display: false
      },
      tooltips: {
        bodySpacing: 4,
        mode: "nearest",
        intersect: 0,
        position: "nearest",
        xPadding: 10,
        yPadding: 10,
        caretPadding: 10
      },
      responsive: true,
      scales: {
        yAxes: [{
          gridLines: {
            zeroLineColor: "transparent",
            drawBorder: false
          },
          ticks: {
              //stepSize: 500
          }
        }],
        xAxes: [{
         /* display: 0,
          ticks: {
            display: false
          },
          gridLines: {
            zeroLineColor: "transparent",
            drawTicks: false,
            display: false,
            drawBorder: false
          }*/
        }]
      },
      layout: {
        padding: {
          left: 0,
          right: 0,
          top: 15,
          bottom: 15
        }
      }
    };

    this.canvas = document.getElementById("lineChartExample");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#80b6f4');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, "rgba(249, 99, 59, 0.40)");


// **************************************************************

//barChart

    this.barChartData = [
      { label: "Leistung im Mega Watt",
      data: []
    }
    ]



// Line Chart
    this.lineChartData = [
        {
          label: "Leistung im Mega Watt",
          pointBorderWidth: 2,
          pointHoverRadius: 4,
          pointHoverBorderWidth: 1,
          pointRadius: 4,
          fill: true,
          borderWidth: 2,
          data: []
        }
      ];
      this.lineChartColors = [
       {
         borderColor: "#f96332",
         pointBorderColor: "#FFF",
         pointBackgroundColor: "#f96332",
         backgroundColor: 'rgba(255,0,0,0.3)',
       }
     ];
    this.lineChartLabels = [];
    this.barChartLabels = [];
    this.lineChartOptions = this.gradientChartOptionsConfigurationWithNumbersAndGrid;

    this.lineChartType = 'line';

    this.lineChartLegend = true;

    /*
    this.canvas = document.getElementById("lineChartExampleWithNumbersAndGrid");
    this.ctx = this.canvas.getContext("2d");

    this.gradientStroke = this.ctx.createLinearGradient(500, 0, 100, 0);
    this.gradientStroke.addColorStop(0, '#18ce0f');
    this.gradientStroke.addColorStop(1, this.chartColor);

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, this.hexToRGB('#18ce0f', 0.4));



    this.canvas = document.getElementById("barChartSimpleGradientsNumbers");
    this.ctx = this.canvas.getContext("2d");

    this.gradientFill = this.ctx.createLinearGradient(0, 170, 0, 50);
    this.gradientFill.addColorStop(0, "rgba(128, 182, 244, 0)");
    this.gradientFill.addColorStop(1, this.hexToRGB('#2CA8FF', 0.6));
    */
  }
}
