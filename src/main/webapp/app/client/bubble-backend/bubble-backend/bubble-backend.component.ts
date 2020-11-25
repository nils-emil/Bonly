import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-bubble-backend',
  templateUrl: './bubble-backend.component.html',
  styleUrls: ['./bubble-backend.component.scss'],
})
export class BubbleBackendComponent implements OnInit {
  public numbers: number[];

  constructor() {
    this.numbers = Array(50)
      .fill(0)
      .map((x, i) => i);
  }

  ngOnInit(): void {}
}
