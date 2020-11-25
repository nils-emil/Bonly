import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Prize } from 'app/shared/model/prize.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { PrizeRegistrationService } from 'app/entities/prize-registration/prize-registration.service';

@Component({
  selector: 'jhi-prize-registration-modal',
  templateUrl: './prize-registration-modal.component.html',
  styleUrls: ['./prize-registration-modal.component.scss'],
})
export class PrizeRegistrationModalComponent implements OnInit {
  public account: Account | null = null;
  public prize: Prize | null = null;
  public error = '';

  constructor(
    private accountSercice: AccountService,
    public activeModal: NgbActiveModal,
    public prizeRegistrationService: PrizeRegistrationService,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    this.accountSercice.getAccount().subscribe(e => {
      this.account = e;
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {}

  register(): void {
    if (this.prize) {
      this.prizeRegistrationService.create({ prizeId: this.prize.id }).subscribe(
        () => {
          this.eventManager.broadcast('prizeRegistrationCompleted');
          this.activeModal.dismiss();
        },
        () => {
          this.error = 'Unable to register';
        }
      );
    }
  }
}
