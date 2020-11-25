import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { LoginService } from '../../core/login/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  // TODO extract these
  ages = ['0 - 9', '10 - 13', '14 - 17', '17 - 21', '21 - 25', '25 - 35', '36 - 50', '50 - 69', '69 +'];
  showMoreInpts = false;

  // TODO find out if we will use first and lastname
  settingsForm = this.fb.group({
    // firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    // lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    login: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    age: [],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined],
  });

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router,
    private languageService: JhiLanguageService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          login: account.login,
          // firstName: account.firstName,
          // lastName: account.lastName,
          age: account.age,
          email: account.email,
          langKey: account.langKey,
        });

        this.account = account;
      }
    });
  }

  save(): void {
    this.success = false;
    this.account.login = this.settingsForm.get('login')!.value;
    // this.account.firstName = this.settingsForm.get('firstName')!.value;
    // this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.age = this.settingsForm.get('age')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      if (this.account.langKey !== this.languageService.getCurrentLanguage()) {
        this.languageService.changeLanguage(this.account.langKey);
      }
    });
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['home']);
  }

  selectGender(gender: string): void {
    if (this.account) {
      this.account.gender = gender;
    }
  }
}
