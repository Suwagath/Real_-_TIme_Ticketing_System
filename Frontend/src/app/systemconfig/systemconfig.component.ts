import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';


@Component({
  selector: 'app-systemconfig',
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './systemconfig.component.html',
  styleUrl: './systemconfig.component.css'
})
export class SystemconfigComponent implements OnInit {

  protected systemStatus: string = 'System Stop';
  protected cliStatus: string = 'CLI Stop';
  private intervalId: any;
  protected totalTickets: number = 0;
  protected ticketReleaseRate: number = 0;
  protected customerRetrievalRate: number = 0;
  protected maxTicketCapacity: number = 0;
  protected showAlertSuccess: boolean = false;
  protected alertMgSuccess: string = '';
  protected showAlertDanger: boolean = false;
  protected alertMgDanger: string = '';
  protected systemStatusColor: string = '';
  protected cliStatusColor: string = '';
  protected statusColorForStop: string = '#d50808';
  protected statusColorForRun: string = '#33a807';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // Call the function every 5 seconds
    this.intervalId = setInterval(() => {
      this.checkSystemStatus();
      this.checkSystemParameters();
    }, 1000);
  }

  checkSystemStatus(): void {
    this.http.get<any>('http://localhost:8080/api/v1/configuresystem/status')
      .subscribe(res => {
        if (res && res.status === 200) {
          this.systemStatus = res.data.system_status === 1 ? "System running" : "System Stop";
          this.systemStatusColor = res.data.system_status === 1 ? this.statusColorForRun : this.statusColorForStop;

          this.cliStatus = res.data.cli_status === 1 ? "CLI running" : "CLI Stop";
          this.cliStatusColor = res.data.cli_status === 1 ? this.statusColorForRun : this.statusColorForStop;
        }
      });
  }

  checkSystemParameters(): void {
    this.http.get<any>('http://localhost:8080/api/v1/configuresystemparameters/status')
      .subscribe(res => {
        if (res && res.status === 200) {
          this.totalTickets = res.data.total_tickets;
          this.ticketReleaseRate = res.data.ticket_release_rate;
          this.customerRetrievalRate = res.data.customer_retrieval_rate;
          this.maxTicketCapacity = res.data.max_ticket_capacity;
        }
      });
  }

  form = new FormGroup({
    totalTickets:new FormControl('', [Validators.required, Validators.max(this.maxTicketCapacity), Validators.min(0)]),
    ticketReleaseRate:new FormControl('', [Validators.required, Validators.max(300), Validators.min(0)]),
    customerRetrievalRate:new FormControl('', [Validators.required, Validators.max(300), Validators.min(0)]),
    maxTicketCapacity:new FormControl('', [Validators.required, Validators.min(0)])
  });

  updateData(): void {
    if (this.cliStatus === 'CLI Stop') {
      this.http.patch<any>('http://localhost:8080/api/v1/configuresystemparameters/update', {
        total_tickets: this.form.get('totalTickets')?.value,
        ticket_release_rate: this.form.get('ticketReleaseRate')?.value,
        customer_retrieval_rate: this.form.get('customerRetrievalRate')?.value,
        max_ticket_capacity: this.form.get('maxTicketCapacity')?.value
      }).subscribe(res => {
        if (res && res.status === 200) {
          this.showSuccessAlert('Save data successfully');
        }
      });

    } else {
      this.showDangerAlert('CLI should be stop for this operation')
    }
  }

  // startSystem(): void {
  //   if (this.cliStatus === 'CLI running') {
  //     this.http.patch<any>('http://localhost:8080/api/v1/configuresystem/update', {
  //       system_status: 2
  //     }).subscribe(res => {
  //       if (res && res.status === 200) {
  //         this.showSuccessAlert('System start successfully');
  //       }
  //     });
  //
  //   } else {
  //     this.showDangerAlert('CLI is not running');
  //   }
  // }
  //
  // stopSystem(): void {
  //   if (this.cliStatus === 'CLI running') {
  //     this.http.patch<any>('http://localhost:8080/api/v1/configuresystem/update', {
  //       system_status: 1
  //     }).subscribe(res => {
  //       if (res && res.status === 200) {
  //         this.showSuccessAlert('System stop successfully');
  //       }
  //     });
  //
  //   } else {
  //     this.showDangerAlert('CLI is not running');
  //   }
  // }

  showDangerAlert(mg: string): void {
    this.alertMgDanger = mg;
    this.showAlertDanger = true; // Show the danger alert
    setTimeout(() => {
      this.showAlertDanger = false; // Hide the alert after 3 seconds
    }, 3000);
  }

  showSuccessAlert(mg: string): void {
    this.alertMgSuccess = mg;
    this.showAlertSuccess = true; // Show the success alert
    setTimeout(() => {
      this.showAlertSuccess = false; // Hide the alert after 3 seconds
    }, 3000);
  }

  ngOnDestroy(): void {
    // Clear the interval when the component is destroyed
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}
