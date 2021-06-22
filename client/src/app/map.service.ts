import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {filterBody} from './filterBody'
import { Observable, of } from 'rxjs';
import { map } from 'jquery';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
@Injectable({
  providedIn: 'root'
})
export class MapsService {
  constructor(){}
  public minDate: number;
  public minMonth: number;
  public minYear: number;
  public maxDate: number;
  public maxMonth: number;
  public maxYear: number;

  public minDateInb: number;
  public minMonthInb: number;
  public minYearInb: number;
  public maxDateInb: number;
  public maxMonthInb: number;
  public maxYearInb: number;

  public genehmigt: boolean;
  public inbetrieb: boolean

  setFilterBody(dayMinGen, monthMinGen, yearMinGen, dayMaxGen, monthMaxGen, yearMaxGen, dayMinInb, monthMinInb, yearMinInb, dayMaxInb, monthMaxInb, yearMaxInb, genehmigt, inbetrieb){
  this.genehmigt = genehmigt;
  this.inbetrieb = inbetrieb;

  this.minDate = dayMinGen;
  this.minMonth = monthMinGen;
  this.minYearInb = yearMinGen;
  this.maxDate = dayMaxGen;
  this.maxMonth = monthMaxGen;
  this.maxYear = yearMaxGen;

  this.minDateInb = dayMinInb;
  this.minMonthInb = monthMinInb;
  this.minYearInb = yearMinInb;
  this.maxDateInb = dayMaxInb;
  this.maxMonthInb = monthMaxInb;
  this.maxYearInb = yearMaxInb;
  }

  getMinDateInb(): number {
    return this.minDateInb;
  }

  getMinMonthInb(): number{
    return this.minMonthInb;
  }

  getMinYearInb(): number{
    return this.maxYearInb;
  }

  getMaxDateInb(): number{
    return this.maxDateInb;
  }

  getMaxMonthInb(): number{
    return this.maxMonthInb;
  }

  getMaxYearInb(): number{
    return this.maxYearInb;
  }

  getInbetrieb(): boolean{
    return this.inbetrieb;
  }

  getGenehmigt(): boolean{
    return this.genehmigt;
  }

  setUndefined(){
  this.genehmigt = undefined;
  this.inbetrieb = undefined;

  this.minDate = undefined;
  this.minMonth = undefined;
  this.minYearInb = undefined;
  this.maxDate = undefined;
  this.maxMonth = undefined;
  this.maxYear = undefined;

  this.minDateInb = undefined;
  this.minMonthInb = undefined;
  this.minYearInb = undefined;
  this.maxDateInb = undefined;
  this.maxMonthInb = undefined;
  this.maxYearInb = undefined;
  }



}

