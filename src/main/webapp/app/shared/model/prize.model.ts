import { Moment } from 'moment';

export interface IPrize {
  id?: number;
  registationStops?: Moment;
  image?: any;
  imageId?: any;
  imageContentType?: string;
  type?: string;
  creditsRequired?: number;
  winner?: string;
  title?: string;
}

export class Prize implements IPrize {
  constructor(
    public id?: number,
    public registationStops?: Moment,
    public image?: any,
    public imageId?: any,
    public imageContentType?: string,
    public type?: string,
    public creditsRequired?: number,
    public winner?: string,
    public title?: string
  ) {}
}
