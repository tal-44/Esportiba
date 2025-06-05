import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Data } from '@angular/router';
import { UrlPaypalResponse } from '../common/url-paypal-response';
import { Observable } from 'rxjs';
import { HeaderService } from './header.service';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl:string = 'http://localhost:8085/api/v1/payments';  

  constructor(private http: HttpClient, private headerService: HeaderService) { }

  getUrlPayment(DataPayment: Data): Observable<UrlPaypalResponse> {
    return this.http.post<UrlPaypalResponse>(this.apiUrl, DataPayment, { headers: this.headerService.headers });
  }

}