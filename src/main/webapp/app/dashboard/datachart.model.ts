import {DataSet} from "./dataset.model";

export interface IDataChart {
  labels?: string[];
  datasets?: DataSet[];
}

export class DataChart implements IDataChart {
  constructor(public labels: string[], public datasets: DataSet[]) {
  }
}
