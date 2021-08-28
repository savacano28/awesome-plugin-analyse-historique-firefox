import {Directive, EventEmitter, Input, Output} from '@angular/core';

@Directive({
  selector: '[jhiSort]',
})
export class SortDirective<T> {
  @Input() callback?: () => void;
  @Output() predicateChange = new EventEmitter<T>();
  @Output() ascendingChange = new EventEmitter<boolean>();

  private _predicate?: T;

  @Input()
  get predicate(): T | undefined {
    return this._predicate;
  }

  set predicate(predicate: T | undefined) {
    this._predicate = predicate;
    this.predicateChange.emit(predicate);
  }

  private _ascending?: boolean;

  @Input()
  get ascending(): boolean | undefined {
    return this._ascending;
  }

  set ascending(ascending: boolean | undefined) {
    this._ascending = ascending;
    this.ascendingChange.emit(ascending);
  }

  sort(field?: T): void {
    if (String(this.predicate) !== '_score') {
      this.ascending = field !== this.predicate ? true : !this.ascending;
      this.predicate = field;
      this.predicateChange.emit(field);
      this.ascendingChange.emit(this.ascending);
      this.callback?.();
    }
  }
}
