import { Component, Input, OnChanges } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';

import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'geo-secured-image',
  templateUrl: './secured-image.component.html',
  styleUrls: ['./secured-image.component.scss'],
})
export class SecuredImageComponent implements OnChanges {
  // This part just creates an rxjs stream from the src
  // this makes sure that we can handle it when the src changes
  // or even when the component gets destroyed
  @Input() private src: string;
  private src$ = new BehaviorSubject(this.src);

  // this stream will contain the actual url that our img tag will load
  // everytime the src changes, the previous call would be canceled and the
  // new resource would be loaded
  dataUrl$ = this.src$.pipe(switchMap(url => this.loadImage(url)));

  ngOnChanges() {
    this.src$.next(this.src);
  }

  // we need HttpClient to load the image and DomSanitizer to trust the url
  constructor(
    private httpClient: HttpClient,
    private domSanitizer: DomSanitizer
  ) {}

  private loadImage(url: string): Observable<any> {
    if (url == null) {
      return of();
    }
    return this.httpClient
      .get(url, { responseType: 'blob' })
      .pipe(
        map(e =>
          this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(e))
        )
      );
  }
}
