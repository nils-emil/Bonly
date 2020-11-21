import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';

type EntityResponseType = HttpResponse<IUserAdvertisementAnswers>;
type EntityArrayResponseType = HttpResponse<IUserAdvertisementAnswers[]>;

@Injectable({ providedIn: 'root' })
export class UserAdvertisementAnswersService {
  public resourceUrl = SERVER_API_URL + 'api/user-advertisement-answers';

  constructor(protected http: HttpClient) {}

  create(userAdvertisementAnswers: IUserAdvertisementAnswers): Observable<EntityResponseType> {
    return this.http.post<IUserAdvertisementAnswers>(this.resourceUrl, userAdvertisementAnswers, { observe: 'response' });
  }

  update(userAdvertisementAnswers: IUserAdvertisementAnswers): Observable<EntityResponseType> {
    return this.http.put<IUserAdvertisementAnswers>(this.resourceUrl, userAdvertisementAnswers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserAdvertisementAnswers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserAdvertisementAnswers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
