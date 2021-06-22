import { TestBed, async, inject } from '@angular/core/testing';
import {LeistungPlzService } from './LeistungPlz.service';


describe('Service: LeistungPlz', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LeistungPlzService]
    });
  });

  it('should ...', inject([LeistungPlzService], (service: LeistungPlzService) => {
    expect(service).toBeTruthy();
  }));
});
