import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecuredImageComponent } from './secured-image.component';

describe('SecuredImageComponent', () => {
  let component: SecuredImageComponent;
  let fixture: ComponentFixture<SecuredImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SecuredImageComponent],
      imports: [HttpClientModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SecuredImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
