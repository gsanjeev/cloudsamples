import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarGatewaySharedModule } from '../../shared';
import {
    DealerService,
    DealerPopupService,
    DealerComponent,
    DealerDetailComponent,
    DealerDialogComponent,
    DealerPopupComponent,
    DealerDeletePopupComponent,
    DealerDeleteDialogComponent,
    dealerRoute,
    dealerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dealerRoute,
    ...dealerPopupRoute,
];

@NgModule({
    imports: [
        CarGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DealerComponent,
        DealerDetailComponent,
        DealerDialogComponent,
        DealerDeleteDialogComponent,
        DealerPopupComponent,
        DealerDeletePopupComponent,
    ],
    entryComponents: [
        DealerComponent,
        DealerDialogComponent,
        DealerPopupComponent,
        DealerDeleteDialogComponent,
        DealerDeletePopupComponent,
    ],
    providers: [
        DealerService,
        DealerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarGatewayDealerModule {}
