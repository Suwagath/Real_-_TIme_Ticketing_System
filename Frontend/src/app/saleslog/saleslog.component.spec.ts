import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaleslogComponent } from './saleslog.component';

describe('SaleslogComponent', () => {
  let component: SaleslogComponent;
  let fixture: ComponentFixture<SaleslogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaleslogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaleslogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
