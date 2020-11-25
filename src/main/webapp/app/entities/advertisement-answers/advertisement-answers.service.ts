import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

type EntityResponseType = HttpResponse<IAdvertisementAnswers>;
type EntityArrayResponseType = HttpResponse<IAdvertisementAnswers[]>;

@Injectable({ providedIn: 'root' })
export class AdvertisementAnswersService {
  public resourceUrl = SERVER_API_URL + 'api/content-answers';

  constructor(protected http: HttpClient) {}

  create(advertisementAnswers: IAdvertisementAnswers): Observable<EntityResponseType> {
    return this.http.post<IAdvertisementAnswers>(this.resourceUrl, advertisementAnswers, { observe: 'response' });
  }

  update(advertisementAnswers: IAdvertisementAnswers): Observable<EntityResponseType> {
    return this.http.put<IAdvertisementAnswers>(this.resourceUrl, advertisementAnswers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdvertisementAnswers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdvertisementAnswers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
