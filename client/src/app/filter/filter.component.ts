import { Component, OnInit , Input,  Output, EventEmitter } from '@angular/core';
import {FormGroup, FormControl} from '@angular/forms';
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
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
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
  ],
})

export class FilterComponent implements OnInit {

  constructor() { }

  public show:boolean = false;

  public showApproved:boolean = false;
  public buttonName:any = 'Zeiträume';

  private today = new Date();
  private month = this.today.getMonth();
  private year = this.today.getFullYear();

  approved :boolean= false;
  installed :boolean= false;
  theCheckbox :boolean;
  date = new FormControl(moment());

  range = new FormGroup({
    start: new FormControl(new Date(this.year-5, this.month, this.today.getDate())),
    end: new FormControl(new Date(this.year, this.month, this.today.getDate()))
  });

  /*rangeInb = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });*/

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

  @Output() filterValue = new EventEmitter();



  clickEvent(){
    console.log(this.installed);
    var minDate = new Date(this.range.value.start).getDate();
    var minMonth = new Date(this.range.value.start).getMonth() +1;
    var minYear = new Date(this.range.value.start).getFullYear();

    var maxDate = new Date(this.range.value.end).getDate();
    var maxMonth = new Date(this.range.value.end).getMonth() +1;
    var maxYear = new Date(this.range.value.end).getFullYear();

    var minDateInb = new Date(this.range.value.start).getDate();
    var minMonthInb = new Date(this.range.value.start).getMonth() +1;
    var minYearInb = new Date(this.range.value.start).getFullYear();

    var maxDateInb = new Date(this.range.value.end).getDate();
    var maxMonthInb = new Date(this.range.value.end).getMonth() +1;
    var maxYearInb = new Date(this.range.value.end).getFullYear();

    this.filterValue.emit(
     {
      minDate: minDate,
      minMonth: minMonth,
      minYear: minYear,
      maxDate: maxDate,
      maxMonth: maxMonth,
      maxYear: maxYear,

      minDateInb: minDateInb,
      minMonthInb: minMonthInb,
      minYearInb: minYearInb,
      maxDateInb: maxDateInb,
      maxMonthInb: maxMonthInb,
      maxYearInb: maxYearInb,

      genehmigt:  this.approved,
      inbetrieb: this.installed

      }
    )

  }
  select: string;

  ngOnInit(): void {

  }

}
