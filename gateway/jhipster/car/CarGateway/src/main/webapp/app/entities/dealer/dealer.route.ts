import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DealerComponent } from './dealer.component';
import { DealerDetailComponent } from './dealer-detail.component';
import { DealerPopupComponent } from './dealer-dialog.component';
import { DealerDeletePopupComponent } from './dealer-delete-dialog.component';

export const dealerRoute: Routes = [
    {
        path: 'dealer',
        component: DealerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dealers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dealer/:id',
        component: DealerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dealers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dealerPopupRoute: Routes = [
    {
        path: 'dealer-new',
        component: DealerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dealers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dealer/:id/edit',
        component: DealerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dealers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dealer/:id/delete',
        component: DealerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Dealers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
