import {Route} from '@angular/router';

import {DashboardComponent} from './dashboard.component';

export const DASHBOARD_ROUTE: Route = {
  path: 'dashboard',
  component: DashboardComponent,
  data: {
    pageTitle: 'dashboard.title',
  },
};
