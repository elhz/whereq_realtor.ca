import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Listing } from './listing.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ListingService {

    private resourceUrl = 'api/listings';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(listing: Listing): Observable<Listing> {
        let copy: Listing = Object.assign({}, listing);
        copy.inputDate = this.dateUtils
            .convertLocalDateToServer(listing.inputDate);
        copy.modifiedDate = this.dateUtils
            .convertLocalDateToServer(listing.modifiedDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(listing: Listing): Observable<Listing> {
        let copy: Listing = Object.assign({}, listing);
        copy.inputDate = this.dateUtils
            .convertLocalDateToServer(listing.inputDate);
        copy.modifiedDate = this.dateUtils
            .convertLocalDateToServer(listing.modifiedDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Listing> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.inputDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.inputDate);
            jsonResponse.modifiedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.modifiedDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].inputDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].inputDate);
            jsonResponse[i].modifiedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].modifiedDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
