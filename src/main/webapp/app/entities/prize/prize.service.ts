import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPrize } from 'app/shared/model/prize.model';

type EntityResponseType = HttpResponse<IPrize>;
type EntityArrayResponseType = HttpResponse<IPrize[]>;

@Injectable({ providedIn: 'root' })
export class PrizeService {
  public resourceUrl = SERVER_API_URL + 'api/prizes';
  public clientResourceUrl = SERVER_API_URL + 'api/client/prizes';

  constructor(protected http: HttpClient) {}

  create(prize: IPrize): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prize);
    return this.http
      .post<IPrize>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(prize: IPrize): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prize);
    return this.http
      .put<IPrize>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPrize>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPrize[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(prize: IPrize): IPrize {
    const copy: IPrize = Object.assign({}, prize, {
      registationStops: prize.registationStops && prize.registationStops.isValid() ? prize.registationStops.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registationStops = res.body.registationStops ? moment(res.body.registationStops) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((prize: IPrize) => {
        prize.registationStops = prize.registationStops ? moment(prize.registationStops) : undefined;
      });
    }
    return res;
  }
}
