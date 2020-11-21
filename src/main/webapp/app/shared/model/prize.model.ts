import { Moment } from 'moment';

export interface IPrize {
  id?: number;
  registationStops?: Moment;
  winnerChosenAt?: Moment;
  creditsRequired?: number;
  winnerId?: number;
}

export class Prize implements IPrize {
  constructor(
    public id?: number,
    public registationStops?: Moment,
    public winnerChosenAt?: Moment,
    public creditsRequired?: number,
    public winnerId?: number
  ) {}
}
