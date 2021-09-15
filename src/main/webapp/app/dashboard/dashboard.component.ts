import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {ChartDataSets, ChartOptions, ChartTitleOptions} from 'chart.js';
import {Color, Label} from 'ng2-charts';

import {DashboardService} from 'app/dashboard/dashboard.service';
import {DataChart} from 'app/dashboard/datachart.model';

import * as moment from 'moment';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})

export class DashboardComponent implements OnInit, OnDestroy {
  dateObj = moment().lang("fr").format('MMMM Do YYYY, h:mm:ss a');
  isLoading = false;
  lineChartData: ChartDataSets[] = [];
  lineChartLabels: Label[] = [];
  lineChartLegend = true;
  lineChartColors: Color[] = [];
  lineStackColors: Color[] = [];
  lineChartOptions: ChartOptions[] = [];
  lineStackData: ChartDataSets[] = [];
  lineStackLabels: Label[] = [];
  lineStackLegend = true;
  lineStackOptions: ChartOptions[] = [];
  pieData: ChartDataSets[] = [];
  pieLabels: Label[] = [];
  pieLegend = true;
  pieOptions: ChartOptions[] = [];
  wordData: ChartDataSets[] = [];
  wordLabels: Label[] = [];
  wordLegend = true;
  wordOptions: ChartOptions[] = [];
  wordTitle: ChartTitleOptions = {text: "Mots plus frecuents dans la navigation"};

  private readonly destroy$ = new Subject<void>();

  constructor(private dashboardService: DashboardService) {
  }

  loadDataChart(): void {
    this.isLoading = true;
    this.dashboardService.dataChartVisitByDay().subscribe(
      (res: DataChart) => {
        this.isLoading = false;

        // eslint-disable-next-line no-console
        console.log(res);
        this.onSuccessDataChart(res);
      },
      () => (this.isLoading = false)
    );
  }

  loadStackChart(): void {
    this.isLoading = true;
    this.dashboardService.stackChartDurationBysite().subscribe(
      (res: DataChart) => {
        this.isLoading = false;

        // eslint-disable-next-line no-console
        console.log(res);
        this.onSuccessStackChart(res);
      },
      () => (this.isLoading = false)
    );
  }

  loadPie(): void {
    this.isLoading = true;
    this.dashboardService.pieVisitsByType().subscribe(
      (res: DataChart) => {
        this.isLoading = false;

        // eslint-disable-next-line no-console
        console.log(res);
        this.onSuccessPie(res);
      },
      () => (this.isLoading = false)
    );
  }

  loadWordCloud(): void {
    this.isLoading = true;
    this.dashboardService.wordCloudResearch().subscribe(
      (res: DataChart) => {
        this.isLoading = false;

        // eslint-disable-next-line no-console
        console.log(res);
        this.onSuccessWordCloud(res);
      },
      () => (this.isLoading = false)
    );
  }

  ngOnInit(): void {
    this.loadDataChart();
    this.loadStackChart();
    this.loadPie();
    this.loadWordCloud();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private onSuccessDataChart(datachart: DataChart): void {
    this.lineChartData = [{
      data: datachart.datasets[0].data,
      label: "Visites par jour",
      steppedLine: true,
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'rgba(255,0,0,0.3)'
    }];
    this.lineChartLabels = datachart.labels;
    this.lineChartColors.push({borderColor: 'rgba(255,0,0,0.3)', backgroundColor: 'rgba(255,0,0,0.3)'});
    this.lineChartOptions.push({responsive: true});
  }

  private onSuccessStackChart(datachart: DataChart): void {
    this.lineStackData = [{
      data: datachart.datasets[0].data,
      label: "Temps de visite moyen par site [s]",
      backgroundColor: [
        '#0D47A1',
        '#1565C0',
        '#1976D2',
        '#1E88E5',
        '#2196F3',
        '#42A5F5',
        '#90CAF9',
        '#BBDEFB',
        '#0D47A1',
        '#1565C0',
        '#1976D2',
        '#1E88E5',
        '#2196F3',
        '#42A5F5',
        '#90CAF9',
        '#BBDEFB',
        '#0D47A1',
        '#1565C0',
        '#1976D2',
        '#1E88E5',
        '#2196F3',
        '#42A5F5',
        '#90CAF9',
        '#BBDEFB',
        '#0D47A1',
        '#1565C0',
        '#1976D2',
        '#1E88E5',
        '#2196F3',
        '#42A5F5',
        '#90CAF9',
        '#BBDEFB'
      ],
      stack: 'combined',
      type: 'bar'
    },
      {
        data: datachart.datasets[1].data,
        label: "Nombre de visites par site",
        stack: 'combined',
        type: 'line'
      }];
    this.lineStackLabels = datachart.labels;
    this.lineStackOptions.push({responsive: true});
  }

  private onSuccessPie(datachart: DataChart): void {
    this.pieData = [{
      data: datachart.datasets[0].data,
      label: "Nombre de visites par type",
      backgroundColor: [
        '#E1BEE7',
        '#4DD0E1',
        '#BBDEFB',
        '#FFF9C4',
        '#F8BBD0',
        '#9CCC65',
        '#F48FB1'
      ],
      borderColor: [
        '#E1BEE7',
        '#4DD0E1',
        '#BBDEFB',
        '#FFF9C4',
        '#F8BBD0',
        '#9CCC65',
        '#F48FB1'
      ]
    }];
    this.pieLabels = datachart.labels;
    this.pieOptions.push({responsive: true});
  }

  private onSuccessWordCloud(datachart: DataChart): void {
    this.wordData = [{
      data: datachart.datasets[0].data,
      backgroundColor: [
        '#E1BEE7',
        '#4DD0E1',
        '#BBDEFB',
        '#FFF9C4',
        '#F8BBD0',
        '#9CCC65',
        '#F48FB1',
        '#2196F3',
        '#42A5F5',
        '#90CAF9',
        '#BBDEFB'
      ],
      borderColor: [
        '#E1BEE7',
        '#4DD0E1',
        '#BBDEFB',
        '#FFF9C4',
        '#F8BBD0',
        '#9CCC65',
        '#F48FB1',
        '#2196F3',
        '#42A5F5',
        '#90CAF9',
        '#BBDEFB'
      ]
    }];
    this.wordLabels = datachart.labels;
    this.wordOptions.push({responsive: true, title: this.wordTitle});
  }
}
