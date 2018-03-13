import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Dealer } from './dealer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Dealer>;

@Injectable()
export class DealerService {

    private resourceUrl =  SERVER_API_URL + 'dealerservice/api/dealers';
    private resourceSearchUrl = SERVER_API_URL + 'dealerservice/api/_search/dealers';

    constructor(private http: HttpClient) { }

    create(dealer: Dealer): Observable<EntityResponseType> {
        const copy = this.convert(dealer);
        return this.http.post<Dealer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dealer: Dealer): Observable<EntityResponseType> {
        const copy = this.convert(dealer);
        return this.http.put<Dealer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Dealer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Dealer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Dealer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Dealer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Dealer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Dealer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Dealer[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Dealer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Dealer[]>): HttpResponse<Dealer[]> {
        const jsonResponse: Dealer[] = res.body;
        const body: Dealer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Dealer.
     */
    private convertItemFromServer(dealer: Dealer): Dealer {
        const copy: Dealer = Object.assign({}, dealer);
        return copy;
    }

    /**
     * Convert a Dealer to a JSON which can be sent to the server.
     */
    private convert(dealer: Dealer): Dealer {
        const copy: Dealer = Object.assign({}, dealer);
        return copy;
    }
}
