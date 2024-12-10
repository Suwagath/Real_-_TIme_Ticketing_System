import { Routes } from '@angular/router';
import { SystemconfigComponent } from './systemconfig/systemconfig.component';
import { SaleslogComponent } from './saleslog/saleslog.component';


export const routes: Routes = [
    { path: 'systemconfig', component: SystemconfigComponent },
    { path: 'saleslog', component: SaleslogComponent },
    { path: '', redirectTo: 'systemconfig', pathMatch: 'full' }, // Redirect to systemconfig by default
    { path: '**', redirectTo: 'systemconfig' } // Wildcard route
];
