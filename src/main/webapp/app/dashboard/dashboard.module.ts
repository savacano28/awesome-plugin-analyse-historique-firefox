import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {DASHBOARD_ROUTE} from './dashboard.route';
import {DashboardComponent} from './dashboard.component';
import {ChartsModule} from "ng2-charts";

@NgModule({
  imports: [SharedModule, RouterModule.forChild([DASHBOARD_ROUTE]), ChartsModule],
  declarations: [DashboardComponent],
})
export class DashboardModule {
}
