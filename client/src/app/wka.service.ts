import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// next: https://stackblitz.com/github/codecraft-tv/angular-course/tree/current/11.HTTP/5.jsonp-with-observables/code
import { Observable, of } from 'rxjs';
import { catchError, filter, map, tap } from 'rxjs/operators';
import { Wka } from './wka';
import { from, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WkaService {
  private apiRoot = 'http://127.0.0.1:8080';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
constructor(private http: HttpClient) { }

getWkas(): Observable<Wka[]>{
  return this.http.get(this.apiRoot+'/api/all').pipe(
    map((data: any[]) => data.map((item: any) => {
      const model = new Wka(
          item.betreiber,
          item.st_Nr,
          item.bst_Name,
          item.ort,
          item.ortsteil,
          item.anl_Nr,
          item.anl_Bez,
          item.genehmigt,
          item.ostwert,
          item.nordwert,
          item.latitude,
          item.longitude,
          item.kreis,
          item.geme_Kenn,
          item.plz,
          item.inbetriebn,
          item.alt_an_anz,
          item.leistung,
          item.status,
          item.nabenhoehe,
          item.rotordurch,
          item.lw_tag,
          item.lw_nacht,
          item.wka_ID,
          item.genehmigt_s,
          item.inbetriebn_s,
          item.alt_an_anz_s,
          item.plz_s,
      );
      //Object.assign(model, item);
      return model;
    }))
  )

}

getApprovedWkas(minDate, minMonth, minYear, maxDate, maxMonth, maxYear): Observable<Wka[]>{
  return this.http.get(this.apiRoot+ '/api/search/genehmigt/between' + '/'+minDate+'/'+minMonth+'/'+minYear+'/'+maxDate+'/'+maxMonth+'/'+maxYear).pipe(
    map((data: any[]) => data.map((item: any) => {
      const model = new Wka(
        item.betreiber,
        item.st_Nr,
        item.bst_Name,
        item.ort,
        item.ortsteil,
        item.anl_Nr,
        item.anl_Bez,
        item.genehmigt,
        item.ostwert,
        item.nordwert,
        item.latitude,
        item.longitude,
        item.kreis,
        item.geme_Kenn,
        item.plz,
        item.inbetriebn,
        item.alt_an_anz,
        item.leistung,
        item.status,
        item.nabenhoehe,
        item.rotordurch,
        item.lw_tag,
        item.lw_nacht,
        item.wka_ID,
        item.genehmigt_s,
        item.inbetriebn_s,
        item.alt_an_anz_s,
        item.plz_s,
      );
      //Object.assign(model, item);
      return model;
    }))
  )

}

getInstalledWkas(minDate, minMonth, minYear, maxDate, maxMonth, maxYear): Observable<Wka[]>{
  return this.http.get(this.apiRoot+ '/api/search/inbetrieb/between' + '/'+minDate+'/'+minMonth+'/'+minYear+'/'+maxDate+'/'+maxMonth+'/'+maxYear).pipe(
    map((data: any[]) => data.map((item: any) => {
      const model = new Wka(
        item.betreiber,
        item.st_Nr,
        item.bst_Name,
        item.ort,
        item.ortsteil,
        item.anl_Nr,
        item.anl_Bez,
        item.genehmigt,
        item.ostwert,
        item.nordwert,
        item.latitude,
        item.longitude,
        item.kreis,
        item.geme_Kenn,
        item.plz,
        item.inbetriebn,
        item.alt_an_anz,
        item.leistung,
        item.status,
        item.nabenhoehe,
        item.rotordurch,
        item.lw_tag,
        item.lw_nacht,
        item.wka_ID,
        item.genehmigt_s,
        item.inbetriebn_s,
        item.alt_an_anz_s,
        item.plz_s,
      );
      //Object.assign(model, item);
      return model;
    }))
  )

}


getInstalledAndApprovedWkas(dayMinGen, monthMinGen, yearMinGen, dayMaxGen, monthMaxGen, yearMaxGen): Observable<Wka[]>{
  return this.http.get(this.apiRoot+ '/api/search/geninb/between/' + '/'+dayMinGen+'/'+monthMinGen+'/'+yearMinGen+'/'+dayMaxGen+'/'+monthMaxGen+'/'+yearMaxGen).pipe(
    map((data: any[]) => data.map((item: any) => {
      const model = new Wka(
        item.betreiber,
        item.st_Nr,
        item.bst_Name,
        item.ort,
        item.ortsteil,
        item.anl_Nr,
        item.anl_Bez,
        item.genehmigt,
        item.ostwert,
        item.nordwert,
        item.latitude,
        item.longitude,
        item.kreis,
        item.geme_Kenn,
        item.plz,
        item.inbetriebn,
        item.alt_an_anz,
        item.leistung,
        item.status,
        item.nabenhoehe,
        item.rotordurch,
        item.lw_tag,
        item.lw_nacht,
        item.wka_ID,
        item.genehmigt_s,
        item.inbetriebn_s,
        item.alt_an_anz_s,
        item.plz_s,
      );
      //Object.assign(model, item);
      return model;
    }))
  )

}


getLastFiveYears(): Observable<Wka[]>{
  return this.http.get(this.apiRoot+ '/api/lastfive').pipe(
    map((data: any[]) => data.map((item: any) => {
      const model = new Wka(
        item.betreiber,
        item.st_Nr,
        item.bst_Name,
        item.ort,
        item.ortsteil,
        item.anl_Nr,
        item.anl_Bez,
        item.genehmigt,
        item.ostwert,
        item.nordwert,
        item.latitude,
        item.longitude,
        item.kreis,
        item.geme_Kenn,
        item.plz,
        item.inbetriebn,
        item.alt_an_anz,
        item.leistung,
        item.status,
        item.nabenhoehe,
        item.rotordurch,
        item.lw_tag,
        item.lw_nacht,
        item.wka_ID,
        item.genehmigt_s,
        item.inbetriebn_s,
        item.alt_an_anz_s,
        item.plz_s,
      );
      //Object.assign(model, item);
      return model;
    }))
  )

}

}
