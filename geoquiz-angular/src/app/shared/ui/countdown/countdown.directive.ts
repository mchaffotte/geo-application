import { Directive, Input, Output, EventEmitter, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription, timer } from 'rxjs';
import { switchMap, take, tap } from 'rxjs/operators';

export enum Action {
  START,
  STOP,
}

interface CountDown {
  count: number;
  interval: number;
}

@Directive({
  selector: '[geoCountdown]',
})
export class CountdownDirective implements OnInit, OnDestroy {
  private countdown$ = new Subject<CountDown>();
  private subscription = Subscription.EMPTY;
  private actionSubscription = Subscription.EMPTY;

  @Input() countdown: number;
  @Input() interval: number;
  @Input() actions: Subject<Action>;
  @Output() value = new EventEmitter<number>();

  constructor() {
    this.countdown$ = new Subject<CountDown>();
    this.subscription = Subscription.EMPTY;
    this.actionSubscription = Subscription.EMPTY;
    this.countdown = 10 * 1000;
    this.interval = 5;
    this.actions = new Subject<Action>();
  }

  ngOnInit(): void {
    this.subscription = this.countdown$
      .pipe(
        switchMap(({ count, interval }) =>
          timer(0, interval).pipe(
            take(count / interval),
            tap(() => {
              count -= interval;
              this.value.emit(count);
            })
          )
        )
      )
      .subscribe();

    this.actionSubscription = this.actions.subscribe((action) => {
      if (action === Action.START) {
        this.countdown$.next({ count: this.countdown, interval: this.interval });
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.actionSubscription.unsubscribe();
  }
}
