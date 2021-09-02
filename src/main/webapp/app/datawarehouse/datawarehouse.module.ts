import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {DATAWAREHOUSE_ROUTE} from './datawarehouse.route';
import {DatawarehouseComponent} from './datawarehouse.component';
import {ChartsModule} from "ng2-charts";

@NgModule({
  imports: [SharedModule, RouterModule.forChild([DATAWAREHOUSE_ROUTE]), ChartsModule],
  declarations: [DatawarehouseComponent],
})
export class DatawarehouseModule {
}
