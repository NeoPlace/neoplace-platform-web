import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable()
export class ApiService {

  public static url: string = 'your url';

  public static token: string;

  constructor(private http: HttpClient) {

  }


  get(endpoint: string, params?: any) {
    let p = new HttpParams();
    if (params) {
      for (let k in params) {
        p = p.append(k, params[k]);
      }
    }
    let headers = new HttpHeaders();

    return this.http.get(ApiService.url + '/' + endpoint, {params: p, headers: headers});
  }

  post(endpoint: string, params?: any) {
    let p = new HttpParams();
    if (params) {
      for (let k in params) {
        p = p.append(k, params[k]);
      }
    }
    let headers = new HttpHeaders();

    return this.http.post(ApiService.url + '/' + endpoint, p, {headers: headers});
  }
}
