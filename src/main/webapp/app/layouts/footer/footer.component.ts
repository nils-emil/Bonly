import { Component } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/core/login/login.service';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
})
export class FooterComponent {
  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private router: Router,
    private accountService: AccountService
  ) {}

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  navigateToPrizes(): void {
    this.router.navigate(['/prizes']);
  }

  navigateToAds(): void {
    this.router.navigate(['/']);
  }

  navigateToContactInfo(): void {
    this.router.navigate(['/contact']);
  }

  navigateToAccountSettings(): void {
    this.router.navigate(['/account/settings']);
  }

  isAdmin(): boolean {
    return this.accountService.isAdmin();
  }

  login(): void {
    this.router.navigate(['/login']);
  }
}
