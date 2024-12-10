import {Component, OnInit} from '@angular/core';
import {NgForOf} from '@angular/common';
import {HttpClient} from '@angular/common/http';


@Component({
  selector: 'app-saleslog',
  imports: [
    NgForOf
  ],
  templateUrl: './saleslog.component.html',
  styleUrl: './saleslog.component.css'
})
export class SaleslogComponent implements OnInit {
  list: Array<any> = [];
  private intervalId: any;
  private logCount: number = 0;
  private pageNumber: number = 0;
  protected nextBtn: boolean = true;
  protected previousBtn: boolean = true;


  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    // Call the function every 5 seconds
    this.intervalId = setInterval(() => {
      this.checkLogs();
    }, 1000);
  }

  checkLogs(): void {
    this.http.get<any>('http://localhost:8080/api/v1/saleslog/?page=' + this.pageNumber +'&size=25')
      .subscribe(res => {
        this.list=res.data.data_list;
        this.logCount = res.data.log_count;
        this.list.sort((a, b) => b.id - a.id);
        if (this.logCount > (this.pageNumber + 1) * 25) {
          this.nextBtn = false;
        }
      });
  }

  next(): void {
    if (this.logCount > (this.pageNumber + 1) * 25) { // Check if there are more logs to fetch
      this.pageNumber++;
      this.checkLogs();

      // Enable the Previous button when moving to the next page
      this.previousBtn = false;

      // Check if there are no more pages to disable the Next button
      if (this.logCount <= (this.pageNumber + 1) * 25) {
        this.nextBtn = true;
      }
    } else {
      this.nextBtn = true; // Ensure the Next button is disabled if no more pages
    }
  }

  previous(): void {
    if (this.pageNumber > 0) { // Ensure we don't go below page 0
      this.pageNumber--;
      this.checkLogs();

      // Enable the Next button when moving to the previous page
      this.nextBtn = false;

      // Disable the Previous button if back to the first page
      if (this.pageNumber === 0) {
        this.previousBtn = true;
      }
    }
  }

  ngOnDestroy(): void {
    // Clear the interval when the component is destroyed
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

}
