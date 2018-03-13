import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarGatewaySharedModule } from '../../shared';
import {
    CarService,
    CarPopupService,
    CarComponent,
    CarDetailComponent,
    CarDialogComponent,
    CarPopupComponent,
    CarDeletePopupComponent,
    CarDeleteDialogComponent,
    carRoute,
    carPopupRoute,
} from './';

const ENTITY_STATES = [
    ...carRoute,
    ...carPopupRoute,
];

@NgModule({
    imports: [
        CarGatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CarComponent,
        CarDetailComponent,
        CarDialogComponent,
        CarDeleteDialogComponent,
        CarPopupComponent,
        CarDeletePopupComponent,
    ],
    entryComponents: [
        CarComponent,
        CarDialogComponent,
        CarPopupComponent,
        CarDeleteDialogComponent,
        CarDeletePopupComponent,
    ],
    providers: [
        CarService,
        CarPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarGatewayCarModule {}
