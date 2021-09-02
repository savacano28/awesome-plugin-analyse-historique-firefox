import {Route} from '@angular/router';

import {DatawarehouseComponent} from './datawarehouse.component';

export const DATAWAREHOUSE_ROUTE: Route = {
  path: 'datawarehouse',
  component: DatawarehouseComponent,
  data: {
    pageTitle: 'datawarehouse.title',
  },
};
