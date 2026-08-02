import { Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { CompaniesComponent } from './features/companies/companies.component';
import { RemindersComponent } from './features/reminders/reminders.component';
import { ResumesComponent } from './features/resumes/resumes.component';
import { ApplicationsComponent } from './features/applications/applications.component';
import { ApplicationDetailComponent } from './features/applications/application-detail.component';
import { ContactsComponent } from './features/contacts/contacts.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent }
  , { path: 'companies', component: CompaniesComponent }
  , { path: 'reminders', component: RemindersComponent }
  , { path: 'resumes', component: ResumesComponent }
  , { path: 'applications', component: ApplicationsComponent }
  , { path: 'applications/:id', component: ApplicationDetailComponent }
  , { path: 'contacts', component: ContactsComponent }
];
