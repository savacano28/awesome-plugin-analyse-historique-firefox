import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

import {AccountService} from 'app/core/auth/account.service';
import {Account} from 'app/core/auth/account.model';

@Component({
  selector: 'jhi-datawarehouse',
  templateUrl: './datawarehouse.component.html',
  styleUrls: ['./datawarehouse.component.scss'],
})

export class DatawarehouseComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  //table

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router) {
  }

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
