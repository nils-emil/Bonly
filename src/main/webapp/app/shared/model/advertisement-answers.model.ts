export interface IAdvertisementAnswers {
  id?: number;
  answer?: string;
  city?: string;
  stateProvince?: string;
  advertisementId?: number;
}

export class AdvertisementAnswers implements IAdvertisementAnswers {
  constructor(
    public id?: number,
    public answer?: string,
    public city?: string,
    public stateProvince?: string,
    public advertisementId?: number
  ) {}
}
