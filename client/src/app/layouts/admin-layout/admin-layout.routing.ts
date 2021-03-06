import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';

import { TableListComponent } from '../../table-list/table-list.component';


import { MapsComponent } from '../../maps/maps.component';


export const AdminLayoutRoutes: Routes = [
    { path: 'table-list',     component: TableListComponent },
    { path: 'maps',           component: MapsComponent },
    { path: 'statistics',      component: DashboardComponent },
];
