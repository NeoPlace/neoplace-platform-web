import {Routes} from '@angular/router';
import {AdminLayoutComponent} from './shared/components/layouts/admin-layout/admin-layout.component';

export const rootRouterConfig: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'home',
        loadChildren: './views/home/home.module#HomeModule',
        data: { title: 'Home', breadcrumb: 'HOME' }
      },
      {
        path: 'dashboard',
        loadChildren: './views/dashboard/dashboard.module#DashboardModule',
        data: { title: 'Dashboard', breadcrumb: 'DASHBOARD'}
      },
    ]
  },
  {
    path: '**',
    redirectTo: 'sessions/404'
  }
];

