import { Component, OnInit } from '@angular/core';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-more-account-questions',
  templateUrl: './more-account-questions.component.html',
  styleUrls: ['./more-account-questions.component.scss'],
})
export class MoreAccountQuestionsComponent implements OnInit {
  account!: Account;
  success = false;

  ages = ['0 - 9', '10 - 13', '14 - 17', '17 - 21', '21 - 25', '25 - 35', '36 - 50', '50 - 69', '69 +'];

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
      }
    });
  }

  save(): void {
    this.accountService.save(this.account).subscribe(() => {
      this.success = true;
      this.accountService.authenticate(this.account);
    });
  }

  selectGender(gender: string): void {
    this.account.gender = gender;
    this.accountService.save(this.account).subscribe(() => {
      if (this.account.gender && this.account.age) {
        this.router.navigate(['']);
      }
      this.accountService.authenticate(this.account);
    });
  }

  selectAge(age: string): void {
    this.account.age = age;
    this.accountService.save(this.account).subscribe(() => {
      if (this.account.gender && this.account.age) {
        this.router.navigate(['']);
      }
      this.accountService.authenticate(this.account);
    });
  }
}
