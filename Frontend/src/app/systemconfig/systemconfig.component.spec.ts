import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemconfigComponent } from './systemconfig.component';

describe('SystemconfigComponent', () => {
  let component: SystemconfigComponent;
  let fixture: ComponentFixture<SystemconfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SystemconfigComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SystemconfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
