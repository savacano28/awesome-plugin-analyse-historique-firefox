export interface IDataSet {
  label?: string;

  data?: number[];

  backgroundColor?: string;

  borderColor?: string;

  borderWidth?: number;

  stepped?: boolean;

  fill?: boolean;
}

export class DataSet implements IDataSet {
  constructor(public label: string,
              public data: number[],
              public backgroundColor: string,
              public borderColor: string,
              public borderWidth: number,
              public stepped: boolean,
              public fill: boolean) {
  }
}

