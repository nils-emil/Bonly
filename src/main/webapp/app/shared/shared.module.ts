import { NgModule } from '@angular/core';
import { BonlySharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { BubbleBackendComponent } from 'app/client/bubble-backend/bubble-backend/bubble-backend.component';

@NgModule({
  imports: [BonlySharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, AlertComponent, AlertErrorComponent, BubbleBackendComponent, HasAnyAuthorityDirective],
  entryComponents: [LoginModalComponent],
  exports: [
    BonlySharedLibsModule,
    FindLanguageFromKeyPipe,
    BubbleBackendComponent,
    AlertComponent,
    AlertErrorComponent,
    HasAnyAuthorityDirective,
  ],
})
export class BonlySharedModule {}
