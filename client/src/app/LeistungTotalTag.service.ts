import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, filter, map, tap } from 'rxjs/operators';
import { from, throwError } from 'rxjs';
import {LeistungTotalTag} from './LeistungTotalTag';

@Injectable({
  providedIn: 'root'
})
export class LeistungTotalTagService {
  private apiRoot = 'http://127.0.0.1:8080';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient) { }
//inbetrieb Datum
  getLeistungTotalTag(minDate, minMonth, minYear, maxDate, maxMonth, maxYear): Observable<LeistungTotalTag[]>{
    return this.http.get(this.apiRoot + '/api/search/leistung/tag'  + '/'+minDate+'/'+minMonth+'/'+minYear+'/'+maxDate+'/'+maxMonth+'/'+maxYear).pipe(
      map((data:any[]) => data.map((item:any) => {
        const model = new LeistungTotalTag(
          item.datum,
          item.leistung,
          item.datum_s
        );
        return model;
      }))
    )

  }
}
