import { Moment } from 'moment';

export interface IAdvertisement {
  id?: number;
  activeFrom?: Moment;
  activeUntill?: Moment;
  imageContentType?: string;
  image?: any;
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
    public question?: string,
    public correctAnswerId?: number
  ) {}
}
