import { Directive, ElementRef, Input, OnChanges } from '@angular/core';

@Directive({
  selector: '[inputFocus]',
})
export class InputFocusDirective implements OnChanges {

  @Input() inputFocus = false;

  @Input() focusDelay = 0;

  constructor(private elementRef: ElementRef) { }

  ngOnChanges() {
    this.checkFocus();
  }

  private checkFocus() {
    if (this.inputFocus && document.activeElement !== this.elementRef.nativeElement) {
      let checkFocusTimeoutHandle: number;
      const focus = () => {
        this.elementRef.nativeElement.focus();
      };
      // Even without a delay, we wait for the next JavaScript tick
      // to avoid causing changes on parent components (e.g., the
      // TextInput component) that have already been checked on this
      // change detection cycle.
      checkFocusTimeoutHandle = setTimeout(focus, this.focusDelay) as any;
    }
  }

}
