import { Moment } from 'moment';
import { AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

export interface IAdvertisement {
  id?: number;
  activeFrom?: Moment;
  activeUntill?: Moment;
  imageContentType?: string;
  image?: any;
  imageId?: any;
  advertisementAnswers?: AdvertisementAnswers[];
  creditCount?: number;
  question?: string;
  correctAnswerId?: number;
}

export class Advertisement implements IAdvertisement {
  constructor(
    public id?: number,
    public activeFrom?: Moment,
    public activeUntill?: Moment,
    public imageContentType?: string,
    public image?: any,
    public imageId?: any,
    public advertisementAnswers?: AdvertisementAnswers[],
    public creditCount?: number,
    public question?: string,
    public correctAnswerId?: number
  ) {}
}
