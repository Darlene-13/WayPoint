import { Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { CompaniesComponent } from './features/companies/companies.component';
import { RemindersComponent } from './features/reminders/reminders.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent }
  , { path: 'companies', component: CompaniesComponent }
  , { path: 'reminders', component: RemindersComponent }
];
