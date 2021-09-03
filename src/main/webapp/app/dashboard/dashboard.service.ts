import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {DataChart} from "./datachart.model";

@Injectable({providedIn: 'root'})
export class DashboardService {
  private resourceVisitsByDayUrl = this.applicationConfigService.getEndpointFor('api/firy-dashboard/data-chart-visit-by-day');
  private resourceDurationBySiteUrl = this.applicationConfigService.getEndpointFor('api/firy-dashboard/data-chart-sites-with-duration');
  private resourceVisitsBytypeUrl = this.applicationConfigService.getEndpointFor('api/firy-dashboard/data-chart-visits-by-type');
  private resourceWordCloudUrl = this.applicationConfigService.getEndpointFor('api/firy-dashboard/data-chart-search-subject');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  dataChartVisitByDay(): Observable<DataChart> {
    return this.http.get<DataChart>(`${this.resourceVisitsByDayUrl}`);
  }

  stackChartDurationBysite(): Observable<DataChart> {
    return this.http.get<DataChart>(`${this.resourceDurationBySiteUrl}`);
  }

  pieVisitsByType(): Observable<DataChart> {
    return this.http.get<DataChart>(`${this.resourceVisitsBytypeUrl}`);
  }

  wordCloudResearch(): Observable<DataChart> {
    return this.http.get<DataChart>(`${this.resourceWordCloudUrl}`);
  }
}
