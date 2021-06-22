import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, filter, map, tap } from 'rxjs/operators';
import { from, throwError } from 'rxjs';
import {LeistungPlz} from './LeistungPlz';

@Injectable({
  providedIn: 'root'
})
export class LeistungPlzService {
  private apiRoot = 'http://127.0.0.1:8080';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient) { }
//inbetrieb Datum
  getLeistungPlz(minDate, minMonth, minYear, maxDate, maxMonth, maxYear): Observable<LeistungPlz[]>{
    return this.http.get(this.apiRoot + '/api/search/leistung/plz'  + '/'+minDate+'/'+minMonth+'/'+minYear+'/'+maxDate+'/'+maxMonth+'/'+maxYear).pipe(
      map((data:any[]) => data.map((item:any) => {
        const model = new LeistungPlz(
          item.leistung,
          item.plz,
          item.plz_s
        );
        return model;
      }))
    )

  }
}
